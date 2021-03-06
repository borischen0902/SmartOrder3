package com.example.boris.smartorder3;

import java.io.Serializable;

public class Drink implements Serializable{
    private int id;
    private String name;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Drink(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
