package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.LoginResponse;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private final LoginView view;
    private final ApiService apiService;

    public LoginPresenter(LoginView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void loginUser(String email, String password) {
        // 이메일과 비밀번호로 Map 생성
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        Call<LoginResponse> call = apiService.loginUser(credentials);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 로그인 성공
                    String token = response.body().getData();
                    view.showLoginSuccess(token);  // 성공 메시지 전달
                } else {
                    view.showLoginFailure("이메일 또는 비밀번호가 일치하지 않습니다.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.showNetworkError("서버와의 통신에 실패했습니다.");
            }
        });
    }
}
