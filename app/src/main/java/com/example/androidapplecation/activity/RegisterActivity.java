package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.repository.UserRepository;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.util.FormValidation;
import com.example.androidapplecation.R;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName, editTextBirth;
    private Button btnSubmit;
    private FormValidation formValidation;

    private static final String TAG = "RegisterActivity";  // 로그 태그

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 뒤로가기 버튼 설정
        setupUndoButton();

        // 뷰 초기화
        initViews();

        // FormValidation 초기화
        formValidation = new FormValidation(
                this,
                editTextEmail,
                editTextPassword,
                editTextName,
                editTextBirth
        );

        // 버튼 클릭 리스너 설정
        btnSubmit.setOnClickListener(v -> {
            if (formValidation.validateForm()) {
                registerUser();
            }
        });
    }

    // 뷰 초기화 메서드
    private void initViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        editTextBirth = findViewById(R.id.editText_birth);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    // 유저 등록 처리 메서드
    private void registerUser() {
        // 유효성 검사가 성공했을 때 처리 로직
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String birth = editTextBirth.getText().toString();

        // 새로운 User 객체 생성 및 UserRepository에 저장
        User newUser = new User(email, password, name, birth);
        UserRepository.getInstance().addUser(newUser);  // 싱글톤에 저장

        // 회원가입 성공 메시지
        Toast.makeText(
                RegisterActivity.this,
                "회원가입 성공!",
                Toast.LENGTH_SHORT
        ).show();

        // 로그 출력
        Log.d(TAG, "User registered: " + newUser.getEmail() + ", " + newUser.getName());

        Intent intent = new Intent(
                RegisterActivity.this,
                LoginActivity.class
        );
        startActivity(intent);
    }
}
