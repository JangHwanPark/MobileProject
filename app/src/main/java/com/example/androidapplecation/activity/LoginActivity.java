package com.example.androidapplecation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;
import com.example.androidapplecation.presenter.LoginPresenter;
import com.example.androidapplecation.view.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {
    private EditText editTextEmail, editTextPassword;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // EditText 초기화
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Presenter 초기화
        presenter = new LoginPresenter(this);

        // 로그인 버튼 초기화 및 클릭 리스너 설정
        Button btnSubmit = findViewById(R.id.loginbutton);
        btnSubmit.setOnClickListener(v -> handleLoginButtonClick()); // 함수로 분리

        // 회원가입 링크 클릭 리스너 설정
        TextView signUpText = findViewById(R.id.signin);
        signUpText.setOnClickListener(v -> navigateToRegister());
    }

    // 로그인 버튼 클릭 처리 메서드
    private void handleLoginButtonClick() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Presenter를 통해 로그인 처리 요청
        presenter.loginUser(email, password);
    }


    @Override
    public void showLoginSuccess(String token) {
        // SharedPreferences에 토큰 저장
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();

        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
        navigateToDashboard();
    }

    @Override
    public void showLoginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
