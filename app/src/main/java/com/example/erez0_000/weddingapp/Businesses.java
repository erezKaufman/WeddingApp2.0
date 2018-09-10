package com.amir.ofir.connecttest;

public class Businesses {

    public String name;
    public String image;
    public String address;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(Integer business_type) {
        this.business_type = business_type;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getWinter_price() {
        return winter_price;
    }

    public void setWinter_price(Integer winter_price) {
        this.winter_price = winter_price;
    }

    public Integer getSummer_price() {
        return summer_price;
    }

    public void setSummer_price(Integer summer_price) {
        this.summer_price = summer_price;
    }

    public boolean isHandikaped() {
        return handikaped;
    }

    public void setHandikaped(boolean handikaped) {
        this.handikaped = handikaped;
    }

    public boolean isKosher() {
        return kosher;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public String description;
    public String mail;
    public String region;
    public Integer bid, business_type, phone, winter_price, summer_price;
    public boolean handikaped, kosher;

    public Businesses(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public Businesses(String name, String image, String address) {
//        this.name = name;
//        this.image = image;
//        this.address = address;
//    }


    public Businesses(String name, String image, String address, String description,
                      String mail, String region, Integer bid, Integer business_type,
                      Integer phone, Integer winter_price, Integer summer_price,
                      boolean handikaped, boolean kosher) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.description = description;
        this.mail = mail;
        this.region = region;
        this.bid = bid;
        this.business_type = business_type;
        this.phone = phone;
        this.winter_price = winter_price;
        this.summer_price = summer_price;
        this.handikaped = handikaped;
        this.kosher = kosher;
    }

}
