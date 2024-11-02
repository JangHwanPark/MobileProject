package com.example.androidapplecation.activity;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.presenter.UserAccountPresenter;
import com.example.androidapplecation.view.UserAccountView;

public class UserAccountActivity extends BaseActivity implements UserAccountView {
    private TextView userEmail;
    private UserAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // 뒤로 가기 버튼 초기화
        setupUndoButton();

        // Presenter 초기화
        presenter = new UserAccountPresenter(this);

        // TextView 초기화
        userEmail = findViewById(R.id.user_email);

        // 사용자 정보 요청
        presenter.fetchUserInfo();
    }

    // UserAccountView 인터페이스 구현
    @Override
    public void showUserInfo(User user) {
        userEmail.setText(user.getEmail());
        Toast.makeText(this, "Hello, " + user.getEmail(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "사용자 이메일: " + user.getEmail());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getToken() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return prefs.getString("token", null);
    }
}