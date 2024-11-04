package com.example.androidapplecation.presenter;

import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.network.ApiCallTemplate;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.view.PostDetailView;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

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

    // 게시글 수정 메서드
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

    // 게시글 삭제 메서드
    public void deletePost(int postId) {
        String token = getToken();
        Call<Void> call = apiService.deleteQuestion(token, postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showCommentDeleted();
                } else {
                    view.showError("Failed to delete post");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Error deleting post: " + t.getMessage());
            }
        });
    }

    public void loadComments() {
        int qid = view.getQid();
        Call<List<Comment>> call = apiService.getComments(qid);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Comment>> call,
                    @NonNull Response<List<Comment>> response) {
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
            public void onFailure(
                    @NonNull Call<List<Comment>> call,
                    @NonNull Throwable t) {
                view.showError("Error loading comments: " + t.getMessage());
            }
        });
    }

    public void postComment(Comment comment) {
        String token = sharedPreferences.getString("token", null);
        Call<Void> call = apiService.createComment(token, comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(
                    @NonNull Call<Void> call,
                    @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showCommentPosted();
                    loadComments(); // 댓글 목록 갱신
                } else {
                    view.showError("Failed to post comment");
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<Void> call,
                    @NonNull Throwable t) {
                view.showError("Error posting comment: " + t.getMessage());
            }
        });
    }

    // 게시글 수정
    public void editComment(Comment updatedComment) {
        Call<Void> call = apiService.updateComment(updatedComment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(
                    @NonNull Call<Void> call,
                    @NonNull Response<Void> response) {
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

    private String getToken() {
        return sharedPreferences.getString("token", null);
    }
}
