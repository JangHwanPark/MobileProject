package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;

public class LoginActivity extends BaseActivity {

    private Button btnSubmit;

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
            Toast.makeText(
                    LoginActivity.this,
                    "로그인 성공",
                    Toast.LENGTH_SHORT).show();
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
