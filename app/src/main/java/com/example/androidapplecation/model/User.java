package com.example.androidapplecation.model;

import androidx.annotation.NonNull;

public class User {
    private int uid;
    private String email;
    private String password;
    private String name;
    private String birth;
    private String interest;
    private String role;
    private String company;

    public User(
            String email,
            String password,
            String name,
            String birth,
            String interest) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.interest = interest;
    }

    // Getters and Setters
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", interest='" + interest + '\'' +
                ", role='" + role + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

}
