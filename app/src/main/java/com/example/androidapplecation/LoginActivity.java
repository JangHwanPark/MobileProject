package com.example.androidapplecation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    }
}
