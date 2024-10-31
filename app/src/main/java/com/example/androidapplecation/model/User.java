package com.example.androidapplecation.model;

public class User {
    private int uid;
    private int rid;
    private String email;
    private String password;
    private String name;
    private String birth;
    private String interest;
    private String role;
    private String company;

    public User(
            int uid,
            int rid,
            String email,
            String password,
            String name,
            String birth,
            String interest,
            String role,
            String company
    ) {
        this.uid = uid;
        this.rid = rid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.interest = interest;
        this.role = role;
        this.company = company;
    }

    public User(String email, String password, String name, String birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }

    // Getters and Setters
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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
}
