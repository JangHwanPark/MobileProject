package com.example.androidapplecation.activity;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.wrapper.ResWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountActivity extends BaseActivity {
    private TextView userName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // 뒤로 가기 버튼 초기화
        setupUndoButton();
        Log.d(TAG, "사용자 정보 액티비티");
        
        // TextView 초기화
        //userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);

        // 저장된 토큰을 가져와서 로그로 출력
        String token = getTokenFromPreferences();
        Log.d(TAG, "저장된 토큰: " + token); // 토큰 출력
        fetchUserInfo(token);
    }

    private void fetchUserInfo(String token) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        // Call<User> call = apiService.getUserInfo("Bearer " + token);
        Call<ResWrapper<User>> call = apiService.getUserInfo(token);

        call.enqueue(new Callback<ResWrapper<User>>() {
            @Override
            public void onResponse(
                    @NonNull Call<ResWrapper<User>> call,
                    @NonNull Response<ResWrapper<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getData();
                    // 사용자 정보를 TextView에 표시
                    //userName.setText(user.getEmail()); // user_name에 이메일 설정
                    userEmail.setText(user.getEmail());
                    Log.d(TAG, "사용자 이메일: " + user.getEmail());
                    Toast.makeText(UserAccountActivity.this, "Hello, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserAccountActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<ResWrapper<User>> call,
                    @NonNull Throwable t) {
                Toast.makeText(UserAccountActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTokenFromPreferences() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return prefs.getString("token", null);
    }
}
