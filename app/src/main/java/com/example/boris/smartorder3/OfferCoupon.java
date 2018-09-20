package com.example.boris.smartorder3;

import java.io.Serializable;

public class OfferCoupon implements Serializable {
    int id_coupon_content;
    String date_start;
    String date_end;
    String title;
    String text;


    public int getId_coupon_content() {
        return id_coupon_content;
    }

    public void setId_coupon_content(int id_coupon_content) {
        this.id_coupon_content = id_coupon_content;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OfferCoupon(int id_coupon_content,String date_start,String date_end,String title, String text) {
        this.id_coupon_content = id_coupon_content;
        this.date_start = date_start;
        this.date_end = date_end;
        this.title = title;
        this.text = text;

    }


}
