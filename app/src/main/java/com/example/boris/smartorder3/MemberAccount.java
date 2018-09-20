package com.example.boris.smartorder3;

import android.widget.Button;
import android.widget.RadioGroup;

import java.io.Serializable;

class MemberAccount implements Serializable {
    private String phone;
    private String password, repasswprd;
    private String name;
    private String birth;
    private int permission= 1;
    int sex;

    public MemberAccount( String password, String name, String bir, int sexcheck) {


        this.password = password;
        this.name = name;
        this.birth = bir;
        this.sex = sexcheck;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRepasswprd() {
        return repasswprd;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public int getSex() {
        return sex;
    }

    public int getPermission() {
        return permission;
    }
}