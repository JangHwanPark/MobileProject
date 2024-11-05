package com.example.androidapplecation.ui.presenter;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;
import com.example.androidapplecation.ui.view.CreatePostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostPresenter {
    private final CreatePostView view;
    private final ApiService apiService;
    private final SharedPreferences sharedPreferences;

    public CreatePostPresenter(CreatePostView view, SharedPreferences sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void sendQuestionToServer(Question question) {
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            view.showLoginRequiredMessage();
            return;
        }

        Call<Void> call = apiService.saveQuestion(token, question);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("CreatePostPresenter", "서버에 질문이 성공적으로 저장되었습니다.");
                    view.showSuccessMessage("질문이 서버에 저장되었습니다.");
                } else {
                    Log.e("CreatePostPresenter", "서버에 질문 저장 실패: " + response.errorBody());
                    view.showErrorMessage("질문 저장에 실패했습니다.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                view.showErrorMessage("서버와의 통신에 실패했습니다.");
            }
        });
    }
}
