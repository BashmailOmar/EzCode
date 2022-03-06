package com.arabcoderz.ezcode;

public class List_Item {
    private int id;
    private String title; //articles value
    private String link;
    private String img_link; //articles value
    private String date;
    private String userName; // articles value



    public List_Item(int id, String title, String link, String img_link, String date) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.img_link = img_link;
        this.date = date;
    }

    public void articles(String title,String img_link,String userName){
        this.title = title;
        this.img_link = img_link;
        this.userName = userName;
        this.date = date;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

