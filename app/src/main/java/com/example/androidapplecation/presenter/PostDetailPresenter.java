package com.example.androidapplecation.presenter;
import static android.content.ContentValues.TAG;

import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.PostDetailView;

import android.content.SharedPreferences;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class PostDetailPresenter {
    private final PostDetailView view;
    private final ApiService apiService;
    private final SharedPreferences sharedPreferences;

    public PostDetailPresenter(PostDetailView view, SharedPreferences sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void loadComments() {
        int qid = view.getQid();
        Call<List<Comment>> call = apiService.getComments(qid);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> comments = response.body();
                    if (comments.isEmpty()) {
                        view.showNoCommentsMessage();
                    } else {
                        view.hideNoCommentsMessage();
                        view.showComments(comments);
                    }
                } else {
                    view.showError("Failed to load comments");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                view.showError("Error loading comments: " + t.getMessage());
            }
        });
    }

    public void postComment(Comment comment) {
        String token = sharedPreferences.getString("token", null);
        Call<Void> call = apiService.createComment(token, comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showCommentPosted();
                    loadComments(); // 댓글 목록 갱신
                } else {
                    view.showError("Failed to post comment");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Error posting comment: " + t.getMessage());
            }
        });
    }

    public void updateComment(int commentId, Comment updatedComment) {
        Call<Void> call = apiService.updateComment(commentId, updatedComment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showCommentUpdated();
                    loadComments(); // 댓글 목록 갱신
                } else {
                    view.showError("Failed to update comment");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Error updating comment: " + t.getMessage());
            }
        });
    }

    public void deleteComment(int commentId) {
        Call<Void> call = apiService.deleteComment(commentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showCommentDeleted();
                    loadComments(); // 댓글 목록 갱신
                } else {
                    view.showError("Failed to delete comment");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Error deleting comment: " + t.getMessage());
            }
        });
    }
}
