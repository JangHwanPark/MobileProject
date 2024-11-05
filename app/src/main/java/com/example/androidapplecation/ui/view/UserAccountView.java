package com.example.androidapplecation.ui.view;

import com.example.androidapplecation.data.model.User;

public interface UserAccountView {
    void showUserInfo(User user);
    void showError(String message);
    String getToken();
}