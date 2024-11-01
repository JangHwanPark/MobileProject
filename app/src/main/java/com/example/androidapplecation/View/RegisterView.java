package com.example.androidapplecation.View;

import com.example.androidapplecation.model.User;

public interface RegisterView {
    void showRegistrationSuccess(User newUser);
    void showRegistrationFailure(String message);
    void showNetworkError(String errorMessage);
    void navigateToLogin();
}
