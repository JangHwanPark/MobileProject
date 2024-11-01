package com.example.androidapplecation.presenter;

import androidx.annotation.NonNull;

import com.example.androidapplecation.view.RegisterView;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private final RegisterView view;
    private final ApiService apiService;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void registerUser(User newUser) {
        Call<Void> callUser = apiService.registerUser(newUser);
        callUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(
                    @NonNull Call<Void> call,
                    @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showRegistrationSuccess(newUser);
                } else {
                    view.showRegistrationFailure(response.message());
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<Void> call,
                    @NonNull Throwable t) {
                view.showNetworkError(t.getMessage());
            }
        });
    }
}

