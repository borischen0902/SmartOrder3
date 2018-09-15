package com.example.boris.smartorder3;


public class Ramen {

    private int dashi, richness, garlic, spicy, texture;
    private boolean seaweed, egg, rice;

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

    public boolean isSeaweed() {
        return seaweed;
    }

    public void setSeaweed(boolean seaweed) {
        this.seaweed = seaweed;
    }

    public boolean isEgg() {
        return egg;
    }

    public void setEgg(boolean egg) {
        this.egg = egg;
    }

    public boolean isRice() {
        return rice;
    }

    public void setRice(boolean rice) {
        this.rice = rice;
    }

    public Ramen(int dashi, int richness, int garlic, int spicy, int texture, boolean seaweed, boolean egg, boolean rice) {
        this.dashi = dashi;
        this.richness = richness;
        this.garlic = garlic;
        this.spicy = spicy;
        this.texture = texture;
        this.seaweed = seaweed;
        this.egg = egg;
        this.rice = rice;
    }
}