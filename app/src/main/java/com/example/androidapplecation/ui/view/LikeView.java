package com.example.androidapplecation.ui.view;

public interface LikeView {
    void onLikeSuccess(int newLikeCount);
    void onLikeFailure(String errorMessage);
}
