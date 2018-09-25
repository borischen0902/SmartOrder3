package com.example.boris.smartorder3;

import java.io.Serializable;

class OOder implements Serializable {


    private int id_item;
    private String name;
    private int price;
    private String datetime;

    public OOder( int id_item,String name,int price,String datetime) {

        this.id_item=id_item;
        this.name = name;
        this.price = price;
        this.datetime = datetime;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
