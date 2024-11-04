package com.example.androidapplecation.network;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallTemplate<T> {

    public void execute(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(
                    @NonNull Call<T> call,
                    @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    onSuccess(response);
                } else {
                    ApiCallTemplate.this.onFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<T> call,
                    @NonNull Throwable t) {
                // 이 부분에서 Throwable을 직접 받아 처리
                ApiCallTemplate.this.onFailure("API call failed: " + t.getMessage());
            }
        });
    }

    protected abstract void onSuccess(Response<T> response);

    protected abstract void onFailure(String errorMessage);
}
