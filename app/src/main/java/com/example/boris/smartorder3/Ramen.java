package com.example.boris.smartorder3;


import java.io.Serializable;

public class Ramen implements Serializable {

    private int dashi, richness, garlic, spicy, texture;

    public int getDashi() {
        return dashi;
    }

    public void setDashi(int dashi) {
        this.dashi = dashi;
    }

    public int getRichness() {
        return richness;
    }

    public void setRichness(int richness) {
        this.richness = richness;
    }

    public int getGarlic() {
        return garlic;
    }

    public void setGarlic(int garlic) {
        this.garlic = garlic;
    }

    public int getSpicy() {
        return spicy;
    }

    public void setSpicy(int spicy) {
        this.spicy = spicy;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }



    public Ramen(int dashi, int richness, int garlic, int spicy, int texture) {
        this.dashi = dashi;
        this.richness = richness;
        this.garlic = garlic;
        this.spicy = spicy;
        this.texture = texture;

    }
}