package com.example.boris.smartorder3;

import java.io.Serializable;

class OOder implements Serializable {


    private String title;
    private String info;

    public OOder( String title, String info) {

        this.title = title;
        this.info = info;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
