package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

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
        Button btnSubmit = findViewById(R.id.loginbutton);

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

            // 서버로 로그인 요청 보내기
            loginUser(email, password);
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

    // 로그인 요청 처리 메서드
    private void loginUser(String email, String password) {
        // ApiService 생성
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 새로운 User 객체 생성
        User user = new User(email, password);

        // 로그인 요청
        Call<User> call = apiService.loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
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
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // 네트워크 오류 처리
                Toast.makeText(
                        LoginActivity.this,
                        "서버와의 통신에 실패했습니다.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
