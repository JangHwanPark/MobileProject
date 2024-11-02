package com.example.androidapplecation.view;

import com.example.androidapplecation.model.User;

public interface UserAccountView {
    void showUserInfo(User user);
    void showError(String message);
    String getToken();
}