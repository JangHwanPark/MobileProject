package com.example.androidapplecation.view;

import android.view.View;

import com.example.androidapplecation.model.Comment;

import java.util.List;

public interface PostDetailView {
    void showComments(List<Comment> comments);
    void showNoCommentsMessage();
    void hideNoCommentsMessage();
    void showCommentPosted();
    void showCommentUpdated();
    void showCommentDeleted();
    void showError(String message);
    void showCommentPopupMenu(View view, int commentId);
    void showPopupMenu(View view);
    int getQid();
}