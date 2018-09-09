package com.example.boris.smartorder3;

import java.io.Serializable;

public class CDesert implements Serializable {
    private int image;
    private String name;
    private int price;
    private boolean button;

    public CDesert() {
        super();
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public boolean getButton() {
        return button;
    }

    public void setButton(boolean button) {
        this.button = button;
    }

    public CDesert(int image, String name, int price, boolean button) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.button = button;
    }


}
