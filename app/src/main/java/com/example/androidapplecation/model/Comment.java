package com.example.androidapplecation.model;

import java.util.Date;

public class Comment {
    private int cid;
    private int uid;
    private int qid;
    private String content;
    private Date createAt;
    private Date updateAt;
    private User user; // User 정보 포함

    // 모든 필드를 사용하는 생성자
    public Comment(
            int cid,
            int uid,
            int qid,
            String content,
            Date createAt,
            Date updateAt,
            User user
    ) {
        this.cid = cid;
        this.uid = uid;
        this.qid = qid;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.user = user;
    }

    // 기본 생성자
    public Comment() {}

    // Getters
    public int getCid() {
        return cid;
    }

    public int getUid() {
        return uid;
    }

    public int getQid() {
        return qid;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public void setUser(User user) { this.user = user; }

    // toString 메서드 오버라이드
    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", qid=" + qid +
                ", content='" + content + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
