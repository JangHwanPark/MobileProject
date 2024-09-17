package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;
import com.example.androidapplecation.UserRepository;
import com.example.androidapplecation.model.User;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    private Button btnSubmit;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // 뷰 초기화
        btnSubmit = findViewById(R.id.loginbutton);
        
        // 버튼 클릭 리스너
        btnSubmit.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // UserRepository에서 이메일로 유저를 검색
            User user = UserRepository.getInstance().findUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                // 로그인 성공
                Toast.makeText(
                        LoginActivity.this,
                        "로그인 성공!",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                // 로그인 실패
                Toast.makeText(
                        LoginActivity.this,
                        "이메일 또는 비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        // 회원가입 TextView 찾기
        TextView signUpText = findViewById(R.id.signin);

        // 회원가입 TextView 클릭 리스너 설정
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent를 사용해 회원가입 액티비티로 이동
                Intent intent = new Intent(
                        LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
