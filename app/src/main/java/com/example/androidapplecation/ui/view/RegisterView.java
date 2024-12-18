package com.example.androidapplecation.ui.view;

import com.example.androidapplecation.data.model.User;

public interface RegisterView {
    void showRegistrationSuccess(User newUser);
    void showRegistrationFailure(String message);
    void showNetworkError(String errorMessage);
    void navigateToLogin();
}
