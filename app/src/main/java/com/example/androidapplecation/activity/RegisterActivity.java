package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.util.FormValidation;
import com.example.androidapplecation.R;

import retrofit2.Call;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName, editTextBirth;
    private Button btnSubmit;
    private FormValidation formValidation;

    private static final String TAG = "RegisterActivity";  // 로그 태그

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 뒤로가기 버튼 설정
        setupUndoButton();

        // 뷰 초기화
        initViews();

        // FormValidation 초기화
        formValidation = new FormValidation(
                this,
                editTextEmail,
                editTextPassword,
                editTextName,
                editTextBirth
        );

        // 버튼 클릭 리스너 설정
        btnSubmit.setOnClickListener(v -> {
            if (formValidation.validateForm()) {
                registerUser();
            }
        });
    }

    // 뷰 초기화 메서드
    private void initViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        editTextBirth = findViewById(R.id.editText_birth);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    // 유저 등록 처리 메서드
    private void registerUser() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 유효성 검사가 성공했을 때 처리 로직
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String birth = editTextBirth.getText().toString();

        // 새로운 User 객체 생성 및 UserRepository에 저장
        User newUser = new User(email, password, name, birth);
        Call<Void> callUser = apiService.registerUser(newUser);

        // 네트워크 요청 비동기 처리
        callUser.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(
                    @NonNull Call<Void> call,
                    @NonNull retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버 응답이 성공적일 때 처리
                    Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();

                    // 로그 출력
                    Log.d(TAG, "User registered: " + newUser.getEmail() + ", " + newUser.getName());

                    // 로그인 페이지로 이동
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();  // 회원가입 후 현재 액티비티를 종료
                } else {
                    // 서버 응답이 실패일 때 처리
                    Toast.makeText(RegisterActivity.this, "회원가입 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "회원가입 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                // 네트워크 오류 또는 서버와의 통신 실패
                Toast.makeText(RegisterActivity.this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 오류: " + t.getMessage());
            }
        });

        // 회원가입 성공 메시지
        Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();

        // 로그 출력
        Log.d(TAG, "User registered: " + newUser.getEmail() + ", " + newUser.getName());

        Intent intent = new Intent(
                RegisterActivity.this,
                LoginActivity.class);
        startActivity(intent);
    }
}
