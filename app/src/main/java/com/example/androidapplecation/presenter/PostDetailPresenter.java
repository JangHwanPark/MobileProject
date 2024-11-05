package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.network.ApiCallTemplate;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.PostDetailView;

import android.content.SharedPreferences;
import retrofit2.Call;
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

    public void editQuestion(int postId, String newContent) {
        String token = getToken();
        Call<Void> call = apiService.editQuestion(token, postId, newContent);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showCommentUpdated();
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to update post: " + errorMessage);
            }
        }.execute(call);
    }

    public void deletePost(int postId) {
        String token = getToken();
        Call<Void> call = apiService.deleteQuestion(token, postId);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showCommentDeleted();
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to delete post: " + errorMessage);
            }
        }.execute(call);
    }

    public void loadComments() {
        int qid = view.getQid();
        Call<List<Comment>> call = apiService.getComments(qid);

        new ApiCallTemplate<List<Comment>>() {
            @Override
            protected void onSuccess(Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                if (comments != null && !comments.isEmpty()) {
                    view.showComments(comments);
                    view.hideNoCommentsMessage();
                } else {
                    view.showNoCommentsMessage();
                }
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to load comments: " + errorMessage);
            }
        }.execute(call);
    }

    public void postComment(Comment comment) {
        String token = getToken();
        Call<Void> call = apiService.createComment(token, comment);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showCommentPosted();
                loadComments();
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to post comment: " + errorMessage);
            }
        }.execute(call);
    }

    public void editComment(Comment updatedComment) {
        Call<Void> call = apiService.updateComment(updatedComment);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showCommentUpdated();
                loadComments();
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to update comment: " + errorMessage);
            }
        }.execute(call);
    }

    public void deleteComment(int commentId) {
        Call<Void> call = apiService.deleteComment(commentId);

        new ApiCallTemplate<Void>() {
            @Override
            protected void onSuccess(Response<Void> response) {
                view.showCommentDeleted();
                loadComments();
            }

            @Override
            protected void onFailure(String errorMessage) {
                view.showError("Failed to delete comment: " + errorMessage);
            }
        }.execute(call);
    }

    private String getToken() {
        return sharedPreferences.getString("token", null);
    }
}
