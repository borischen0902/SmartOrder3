package com.example.boris.smartorder3;

import java.io.Serializable;

public class OfferCoupon implements Serializable {
    private int picture;
    private int qty;
    private String title;
    private String info;

    public OfferCoupon(int picture, int qty, String title, String info) {
        this.picture = picture;
        this.qty = qty;
        this.title = title;
        this.info = info;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
