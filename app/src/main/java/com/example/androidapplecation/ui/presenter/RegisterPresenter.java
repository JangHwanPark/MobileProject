package com.example.androidapplecation.ui.presenter;

import com.example.androidapplecation.ui.view.RegisterView;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.data.network.ApiCallTemplate;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterPresenter {
    private final RegisterView view;
    private final ApiService apiService;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void registerUser(User newUser) {
        Call<Void> call = apiService.registerUser(newUser);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showRegistrationSuccess(newUser);
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showRegistrationFailure(errorMessage);
            }
        }.execute(call);
    }
}
