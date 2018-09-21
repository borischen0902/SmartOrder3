package com.example.boris.smartorder3;

import java.io.Serializable;

public class OfferCoupon implements Serializable {
    int id_coupon_content;
    String startdate;
    String  enddate;
    String title;
    String text;

    public int getId_coupon_content() {
        return id_coupon_content;
    }

    public void setId_coupon_content(int id_coupon_content) {
        this.id_coupon_content = id_coupon_content;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public OfferCoupon(int id_coupon_content, String startdate, String enddate, String title, String text) {
        this.id_coupon_content = id_coupon_content;
        this.startdate = startdate;
        this. enddate =  enddate;

        this.title = title;
        this.text = text;

    }


}
