package com.example.androidapplecation.data.model;

import java.util.Date;

public class Question {
    private Integer qid;
    private int uid;
    private String interest;
    private String title;
    private String content;
    private String category;  // 관련 질문 ID
    private Date createdAt;
    private Date updatedAt;
    private int great;
    private User author;

    // Constructor
    public Question(
            Integer qid,
            int uid,
            String interest,
            String title,
            String content,
            String category,
            Date createdAt,
            Date updatedAt,
            int great
    ) {
        this.qid = qid;
        this.uid = uid;
        this.interest=interest;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.great = great;
    }

    public Question(
            Integer qid,
            String interest,
            String title,
            String content,
            String category,
            Date updatedAt
    ) {
        this.qid = qid;
        this.interest = interest;
        this.category = category;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return qid;
    }

    public void setId(Integer qid) {
        this.qid = qid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getGreat() {
        return great;
    }

    public void setGreat(int great) {
        this.great = great;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
