package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplecation.R;

public class QuestionDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // XML 레이아웃 설정

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // XML과 연결
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewContent = findViewById(R.id.textViewContent);

        // Intent로부터 전달된 데이터 받기
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // 데이터 설정
        textViewTitle.setText(title != null ? title : "No Title");
        textViewContent.setText(content != null ? content : "No Content");
    }
}
