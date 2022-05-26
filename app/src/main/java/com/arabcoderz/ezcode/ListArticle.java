package com.arabcoderz.ezcode;

public class ListArticle {
    private int id;
    private String title;
    private String content;
    private String writer;
    private String article_date;

    public ListArticle(int id, String title, String content, String writer, String article_date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.article_date = article_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return article_date;
    }

    public void setDate(String article_date) {
        this.article_date = article_date;
    }
}
