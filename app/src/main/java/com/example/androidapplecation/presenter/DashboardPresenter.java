package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiCallTemplate;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.DashboardView;

import java.util.List;

import retrofit2.Call;
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

        new ApiCallTemplate<List<Question>>() {
            @Override
            protected void onSuccess(Response<List<Question>> response) {
                view.showQuestions(response.body());
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("질문 데이터를 가져오지 못했습니다.");
            }
        }.execute(call);
    }

    public void loadFreeBoard() {
        Call<List<Question>> call = apiService.getCategoryFreeBoard();

        new ApiCallTemplate<List<Question>>() {
            @Override
            protected void onSuccess(Response<List<Question>> response) {
                view.showFreeBoard(response.body());
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("자유게시판 데이터를 가져오지 못했습니다.");
            }
        }.execute(call);
    }

    public void loadUsers() {
        Call<List<User>> call = apiService.getAllUsers();

        new ApiCallTemplate<List<User>>() {
            @Override
            protected void onSuccess(Response<List<User>> response) {
                view.showUsers(response.body());
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("사용자 데이터를 가져오지 못했습니다.");
            }
        }.execute(call);
    }
}
