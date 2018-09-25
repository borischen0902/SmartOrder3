package com.example.boris.smartorder3;

import android.widget.Button;
import android.widget.RadioGroup;

import java.io.Serializable;

class MemberAccount implements Serializable {
    private String phone;
    private String password;
    private String name;
    private String birth;
    private int permission = 1;
    int sex;

    public MemberAccount(String phone, String password, int permission, String name, int sex, String birth) {
        super();
        this.phone = phone;
        this.password = password;
        this.permission = permission;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
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

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
