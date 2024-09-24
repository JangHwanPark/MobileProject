package com.example.androidapplecation.model;

public class User {
    private String email;
    private String password;
    private String name;
    private String year;
    private String month;
    private String day;

    public User(
            String email,
            String password,
            String name,
            String year,
            String month,
            String day) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
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

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
}
