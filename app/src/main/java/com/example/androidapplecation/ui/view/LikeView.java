package com.example.androidapplecation.ui.view;

public interface LikeView {
    void onLikeSuccess(int newLikeCount, int position);
    void onLikeFailure(String errorMessage);
}
