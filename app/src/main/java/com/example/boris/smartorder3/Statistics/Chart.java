package com.example.boris.smartorder3.Statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Chart {
    private int id;
    private String menber;
    private String item;
    private int price;
    private long date;

    public Chart (int id, String menber,String item, int price, long datetime) {
        super();
        this.id = id;
        this.menber = menber;
        this.item = item;
        this.price = price;
        this.date = datetime;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getmenber() {
        return menber;
    }

    public void setmenber(String menber) {
        this.menber = menber;
    }
    public String getFormatedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        return dateFormat.format(new Date(date));
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


}
