package com.example.androidapplecation.ui.presenter;

import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;
import com.example.androidapplecation.ui.view.LikeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikePresenter {
    private final LikeView view;
    private final ApiService apiService;

    public LikePresenter(LikeView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void likeQuestion(int qid) {
        Call<Integer> call = apiService.greatQuestion(qid);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    view.onLikeSuccess(response.body());
                } else {
                    view.onLikeFailure("Failed to like the question.");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                view.onLikeFailure("Error: " + t.getMessage());
            }
        });
    }
}
