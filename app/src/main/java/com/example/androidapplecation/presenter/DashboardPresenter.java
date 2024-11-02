package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.DashboardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter {
    private final DashboardView view;
    private final ApiService apiService;

    public DashboardPresenter(DashboardView view) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void loadQuestions() {
        Call<List<Question>> call = apiService.getCategoryQuestion();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showQuestions(response.body());
                } else {
                    view.showError("질문 데이터를 가져오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                view.showError("서버 통신 오류: " + t.getMessage());
            }
        });
    }

    public void loadFreeBoard() {
        Call<List<Question>> call = apiService.getCategoryFreeBoard();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showFreeBoard(response.body());
                } else {
                    view.showError("자유게시판 데이터를 가져오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                view.showError("서버 통신 오류: " + t.getMessage());
            }
        });
    }

    public void loadUsers() {
        Call<List<User>> call = apiService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showUsers(response.body());
                } else {
                    view.showError("사용자 데이터를 가져오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.showError("서버 통신 오류: " + t.getMessage());
            }
        });
    }
}