package com.example.androidapplecation.ui.presenter;

import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.data.network.ApiCallTemplate;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;
import com.example.androidapplecation.ui.view.UserAccountView;
import com.example.androidapplecation.data.network.ResWrapper;

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
