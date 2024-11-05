package com.example.androidapplecation.ui.presenter;

import com.example.androidapplecation.data.network.LoginResponse;
import com.example.androidapplecation.data.network.ApiCallTemplate;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;
import com.example.androidapplecation.ui.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenter {
    private final LoginView view;
    private final ApiService apiService;

    public LoginPresenter(LoginView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void loginUser(String email, String password) {
        // 사용자 자격 증명 데이터 준비
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        // API 호출
        Call<LoginResponse> call = apiService.loginUser(credentials);

        // ApiCallTemplate을 사용하여 API 응답 처리
        new ApiCallTemplate<LoginResponse>() {
            @Override
            protected void onSuccess(Response<LoginResponse> response) {
                if (response.body() != null) {
                    String token = response.body().getData();
                    view.showLoginSuccess(token); // 로그인 성공 시 토큰 전달
                } else {
                    onFailure("로그인 응답에 문제가 발생했습니다.");
                }
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showLoginFailure("이메일 또는 비밀번호가 일치하지 않습니다."); // 로그인 실패 메시지 전달
            }
        }.execute(call); // API 호출 실행
    }
}
