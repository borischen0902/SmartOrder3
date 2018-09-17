package com.example.boris.smartorder3;

import java.io.Serializable;

public class CAccount implements Serializable {
private String phone;
private String password;
private String name;
private String birth;
private int sex;
private int permission= 1;

    public CAccount(String phone, String password, String name, String birth, int sex) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getPermission() {
        return permission;
    }


}
