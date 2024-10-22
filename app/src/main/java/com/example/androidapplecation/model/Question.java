package com.example.androidapplecation.model;

import java.util.Date;

public class Question {
    private Integer qid;
    private int uid;
    private String title;
    private String content;
    // private String author;
    private String questionId;  // 관련 질문 ID
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Question(
            Integer qid,
            int uid,
            String title,
            String content,
            // String author,
            String questionId,
            Date createdAt,
            Date updatedAt
    ) {
        this.qid = qid;
        this.uid = uid;
        this.title = title;
        this.content = content;
        // this.author = author;
        this.questionId = questionId;
        this.createdAt = createdAt;
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
