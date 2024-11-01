package com.example.androidapplecation.view;

public interface LoginView {
    void showLoginSuccess(String token);
    void showLoginFailure(String message);
    void showNetworkError(String errorMessage);
    void navigateToDashboard();
    void navigateToRegister();
}
