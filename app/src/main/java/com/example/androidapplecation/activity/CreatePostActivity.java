package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidapplecation.R;

public class CreatePostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // 체크 버튼 설정
        Button submitBtn = findViewById(R.id.header_btn_check);
        submitBtn.setVisibility(View.VISIBLE);
    }
}
