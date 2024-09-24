package com.example.androidapplecation.model;

import java.util.Date;

public class Board {
    private String name;
    private String title;
    private String article;
    private Date date;

    public Board(
            String name,
            String title,
            String article,
            Date date
    ) {
        this.name = name;
        this.title = title;
        this.article = article;
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArticle() { return article; }
    public void setArticle(String article) { this.article = article; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

}
