package com.example.boris.smartorder3;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    String flavor;

    List<Integer> DrinkAndDesert;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public List<Integer> getDrinkAndDesert() {
        return DrinkAndDesert;
    }

    public void setDrinkAndDesert(List<Integer> drinkAndDesert) {
        DrinkAndDesert = drinkAndDesert;
    }

    public Result(String flavor, List<Integer> drinkAndDesert) {
        this.flavor = flavor;
        DrinkAndDesert = drinkAndDesert;
    }
}
