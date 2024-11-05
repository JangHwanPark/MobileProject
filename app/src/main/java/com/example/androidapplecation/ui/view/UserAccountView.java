package com.example.androidapplecation.ui.view;

import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;

import java.util.List;

public interface UserAccountView {
    void showUserInfo(User user);
    void showUserPosts(List<Question> questions);
    void showError(String message);
    String getToken();
}