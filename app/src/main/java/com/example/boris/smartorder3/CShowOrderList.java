package com.example.boris.smartorder3;

import java.io.Serializable;

public class CShowOrderList implements Serializable {
    private int id_order_detail;
    private int id_account;
    private int id_table;
    private int id_coupon;
    private String item;
    private String flavor;
    private int status;

    public CShowOrderList(int id_order_detail, int id_account, int id_table, int id_coupon, String item, String flavor, int status) {
        this.id_order_detail = id_order_detail;
        this.id_account = id_account;
        this.id_table = id_table;
        this.id_coupon = id_coupon;
        this.item = item;
        this.flavor = flavor;
        this.status = status;
    }

    public int getId_order_detail() {
        return id_order_detail;
    }

    public void setId_order_detail(int id_order_detail) {
        this.id_order_detail = id_order_detail;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public int getId_coupon() {
        return id_coupon;
    }

    public void setId_coupon(int id_coupon) {
        this.id_coupon = id_coupon;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
