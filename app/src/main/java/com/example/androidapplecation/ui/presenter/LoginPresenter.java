package com.example.androidapplecation.ui.presenter;

import com.example.androidapplecation.data.model.LoginResponse;
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
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        Call<LoginResponse> call = apiService.loginUser(credentials);

        new ApiCallTemplate<LoginResponse>() {
            @Override
            protected void onSuccess(Response<LoginResponse> response) {
                String token = response.body().getData();
                view.showLoginSuccess(token);
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showLoginFailure("이메일 또는 비밀번호가 일치하지 않습니다.");
            }
        }.execute(call);
    }
}
