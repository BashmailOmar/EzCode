package com.arabcoderz.ezcode;

public class List_Item {
    private int id;
    private String title; //articles value nad // news value
    private String link;  // news value
    private String img_link;  // news value and //articles value


    public List_Item(int id, String title, String link, String img_link) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.img_link = img_link;
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
}

