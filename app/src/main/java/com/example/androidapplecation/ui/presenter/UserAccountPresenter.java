package com.example.androidapplecation.ui.presenter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.data.network.ApiCallTemplate;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;
import com.example.androidapplecation.ui.view.UserAccountView;
import com.example.androidapplecation.data.network.ResWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class UserAccountPresenter {
    private final UserAccountView view;
    private final ApiService apiService;
    private final Context context;

    public UserAccountPresenter(UserAccountView view, Context context) {
        this.view = view;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        this.context = context;
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

    // 로그인한 사용자가 작성한 게시글 목록
    public void fetchUserPostList() {
        int uid = 61;
        Call<List<Question>> call = apiService.getMyPostData(uid);

        new ApiCallTemplate<List<Question>>() {
            @Override
            protected void onSuccess(Response<List<Question>> response) {
                List<Question> questions = response.body();
                if (questions != null && !questions.isEmpty()) {
                    // View에 데이터를 전달하여 RecyclerView 업데이트
                    view.showUserPosts(questions);
                } else {
                    view.showError("작성한 게시글이 없습니다.");
                }
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("게시글 목록을 불러오지 못했습니다: " + errorMessage);
            }
        }.execute(call);
    }
}
