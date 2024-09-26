package com.example.androidapplecation.activity;

import android.os.Bundle;

import com.example.androidapplecation.R;

public class UserAccountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();
    }
}
