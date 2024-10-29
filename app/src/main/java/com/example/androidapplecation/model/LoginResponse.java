package com.example.androidapplecation.model;

public class LoginResponse {
    private boolean success;
    private String data; // JWT 토큰 등

    // Getter and Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
