package com.example.erez0_000.weddingapp.db_classes;

import java.io.Serializable;

public class User implements Serializable {
    public String email, username, password, age, number, type, cost, area, season;

    public User() {}

    public User(String email,String username, String password, String age,String number,String type,String cost,
                String area,String season){
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.number = number;
        this.type = type;
        this.cost = cost;
        this.area = area;
        this.season = season;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public void setCost(String cost) {
        this.cost = cost;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setType(String type) {
        this.type = type;
    }


}

