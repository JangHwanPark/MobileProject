package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.util.FormValidation;
import com.example.androidapplecation.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName, editTextBirth;
    private Button btnSubmit;
    private FormValidation formValidation;

    private static final String TAG = "RegisterActivity";  // 로그 태그

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupInterestSpinner();
        setupUndoButton();
        initViews();
        initFormValidation();
        setupSubmitButton();
    }

    // Spinner 초기화
    private void setupInterestSpinner() {
        Spinner interestSpinner = findViewById(R.id.interest_spinner);
        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_interest, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(interestAdapter);
    }

    // 뷰 초기화
    private void initViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        editTextBirth = findViewById(R.id.editText_birth);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    // FormValidation 초기화
    private void initFormValidation() {
        formValidation = new FormValidation(
                this, editTextEmail, editTextPassword, editTextName, editTextBirth);
    }

    // 버튼 클릭 리스너 설정
    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(v -> {
            if (formValidation.validateForm()) {
                registerUser();
            }
        });
    }

    // 유저 등록 처리 메서드
    private void registerUser() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        User newUser = createUser();
        Call<Void> callUser = apiService.registerUser(newUser);

        callUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    handleRegistrationSuccess(newUser);
                } else {
                    handleRegistrationFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                handleNetworkFailure(t.getMessage());
            }
        });
    }

    private User createUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String birth = editTextBirth.getText().toString();
        return new User(email, password, name, birth);
    }

    private void handleRegistrationSuccess(User newUser) {
        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "User registered: " + newUser.getEmail() + ", " + newUser.getName());
        navigateToLogin();
    }

    private void handleRegistrationFailure(String message) {
        Toast.makeText(this, "회원가입 실패: " + message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "회원가입 실패: " + message);
    }

    private void handleNetworkFailure(String errorMessage) {
        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "서버 통신 오류: " + errorMessage);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
