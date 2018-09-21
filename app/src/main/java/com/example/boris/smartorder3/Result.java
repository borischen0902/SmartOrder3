package com.example.boris.smartorder3;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    String phone;

    String flavor;

    List<Integer> DrinkAndDesert;

    int table;

    public Result(String phone, String flavor, List<Integer> drinkAndDesert, int table) {
        this.phone = phone;
        this.flavor = flavor;
        DrinkAndDesert = drinkAndDesert;
        this.table = table;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }






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

}
