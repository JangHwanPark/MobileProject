package com.example.androidapplecation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;
import com.example.androidapplecation.ui.presenter.RegisterPresenter;
import com.example.androidapplecation.ui.view.RegisterView;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.util.FormValidation;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private EditText editTextEmail, editTextPassword, editTextName, editTextBirth, editTextCompany;
    private Spinner interestSpinner;
    private Button btnSubmit;
    private FormValidation formValidation;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupInterestSpinner();
        setupUndoButton();
        initFormValidation();
        setupSubmitButton();

        presenter = new RegisterPresenter(this);  // Presenter 초기화
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        editTextBirth = findViewById(R.id.editText_birth);
        editTextCompany = findViewById(R.id.editTextCompany);
        interestSpinner = findViewById(R.id.interest_spinner);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void setupInterestSpinner() {
        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_interest, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(interestAdapter);
    }

    private void initFormValidation() {
        formValidation = new FormValidation(
                this, editTextEmail, editTextPassword, editTextName, editTextBirth);
    }

    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(v -> {
            if (formValidation.validateForm()) {
                User newUser = createUser();
                presenter.registerUser(newUser); // Presenter를 통해 등록 처리
            }
        });
    }

    @Override
    public void showRegistrationSuccess(User newUser) {
        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
        navigateToLogin();
    }

    @Override
    public void showRegistrationFailure(String message) {
        Toast.makeText(this, "회원가입 실패: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError(String errorMessage) {
        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // User 객체 생성 메서드
    private User createUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String birth = editTextBirth.getText().toString().trim();
        String company = editTextCompany.getText().toString().trim();
        String interest = interestSpinner.getSelectedItem().toString();
        return new User(email, password, name, birth, interest, company);
    }
}
