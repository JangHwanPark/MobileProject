package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiCallTemplate;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.UserAccountView;
import com.example.androidapplecation.wrapper.ResWrapper;

import retrofit2.Call;
import retrofit2.Response;

public class UserAccountPresenter {
    private final UserAccountView view;
    private final ApiService apiService;

    public UserAccountPresenter(UserAccountView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchUserInfo() {
        String token = view.getToken();
        Call<ResWrapper<User>> call = apiService.getUserInfo(token);

        new ApiCallTemplate<ResWrapper<User>>() {
            @Override
            protected void onSuccess(Response<ResWrapper<User>> response) {
                User user = response.body().getData();
                view.showUserInfo(user);
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to fetch user info: " + errorMessage);
            }
        }.execute(call);
    }
}
