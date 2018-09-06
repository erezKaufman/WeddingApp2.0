package com.example.erez0_000.weddingapp;

public class User {
    public String memail, musername,mage,mnumber,mtype,mcost,marea,mseason;

    public User(){

    }


    public User(String email,String username,String age,String number,String type,String cost,String area,String season){
        mage = age;
        memail = email;
        musername = username;
        mnumber = number;
        mtype = type;
        mcost = cost;
        marea = area;
        mseason = season;
    }

    public void sendUserData(){

    }
}
