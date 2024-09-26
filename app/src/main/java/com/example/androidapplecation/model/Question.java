package com.example.androidapplecation.model;

import java.util.Date;

public class Question {
    private String id;
    private String title;
    private String content;
    private String author;
    private String questionId;  // 관련 질문 ID
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Question(
            String id,
            String title,
            String content,
            String author,
            String questionId,
            Date createdAt,
            Date updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.questionId = questionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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
}
