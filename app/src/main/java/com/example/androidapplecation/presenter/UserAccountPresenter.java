package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.UserAccountView;
import com.example.androidapplecation.wrapper.ResWrapper;

import retrofit2.Call;
import retrofit2.Callback;
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

        call.enqueue(new Callback<ResWrapper<User>>() {
            @Override
            public void onResponse(Call<ResWrapper<User>> call, Response<ResWrapper<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getData();
                    view.showUserInfo(user);
                } else {
                    view.showError("Failed to fetch user info");
                }
            }

            @Override
            public void onFailure(Call<ResWrapper<User>> call, Throwable t) {
                view.showError("Error fetching user info: " + t.getMessage());
            }
        });
    }
}
