package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;
import com.example.androidapplecation.repository.UserRepository;
import com.example.androidapplecation.model.User;

public class LoginActivity extends BaseActivity {

    private Button btnSubmit;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // EditText 초기화
        editTextEmail = findViewById(R.id.editTextEmail); // 이메일 입력 필드와 연결
        editTextPassword = findViewById(R.id.editTextPassword); // 비밀번호 입력 필드와 연결

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // 버튼 초기화
        btnSubmit = findViewById(R.id.loginbutton);

        // 버튼 클릭 리스너 설정
        btnSubmit.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // 이메일 또는 비밀번호가 비어 있는지 확인
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "아이디와 비밀번호를 입력해주세요.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // UserRepository에서 이메일로 유저를 검색
            User user = UserRepository.getInstance().findUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                // 로그인 성공
                Toast.makeText(
                        LoginActivity.this,
                        "로그인 성공!",
                        Toast.LENGTH_SHORT
                ).show();

                // DashboardActivity로 이동
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);

                // 로그인 액티비티 종료
                finish();
            } else {
                // 로그인 실패
                Toast.makeText(
                        LoginActivity.this,
                        "이메일 또는 비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // 회원가입 TextView 클릭 리스너 설정
        TextView signUpText = findViewById(R.id.signin);
        signUpText.setOnClickListener(v -> {
            // Intent를 사용해 회원가입 액티비티로 이동
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class);
            startActivity(intent);
        });
    }
}
