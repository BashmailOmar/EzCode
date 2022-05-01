package com.arabcoderz.ezcode;

public class ListPlaces {


    public String place;
    public String username;
    public String point;
    public String img;

    public ListPlaces(String place, String username, String point, String img) {
        this.place = place;
        this.username = username;
        this.point = point;
        this.img = img;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
