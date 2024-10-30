package com.example.androidapplecation.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountActivity extends BaseActivity {
    private TextView userName, userEmail;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // 뒤로 가기 버튼 초기화
        setupUndoButton();

        // TextView 초기화
        //userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);

        // 토큰 가져오기
        String token = getTokenFromPreferences();
        fetchUserInfo(token);
    }

    private void fetchUserInfo(String token) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<User> call = apiService.getUserInfo("Bearer " + token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    // 사용자 정보를 TextView에 표시
                    //userName.setText(user.getEmail()); // user_name에 이메일 설정
                    userEmail.setText(user.getEmail());
                    Toast.makeText(UserAccountActivity.this, "Hello, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserAccountActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserAccountActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTokenFromPreferences() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return prefs.getString("token", null);
    }
}
