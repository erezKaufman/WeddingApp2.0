package com.example.erez0_000.weddingapp.db_classes;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Businesses {
    @SerializedName("Name")
    private  String Name;
    @SerializedName("Image")
    private String Image;
    @SerializedName("Address")
    private String Address;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Mail")
    private String Mail;
    @SerializedName("Region")
    private String Region;
    @SerializedName("Kosher")
    private Map<String,Boolean> Kosher;
    @SerializedName("Handikaped")
    private String Handikaped;
    @SerializedName("Bid")
    private Integer Bid;
    @SerializedName("business_type")
    private Integer business_type;
    @SerializedName("Phone")
    private long[] Phone;
    @SerializedName("winter_price")
    private Integer winter_price;
    @SerializedName("summer_price")
    private Integer  summer_price;
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public Integer getBid() {
        return Bid;
    }

    public void setBid(Integer Bid) {
        this.Bid = Bid;
    }

    public Integer getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(Integer business_type) {
        this.business_type = business_type;
    }

    public long[] getPhone() {
        return Phone;
    }

    public void setPhone(long[] Phone) {
        this.Phone= Phone;
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

    public String isHandikaped() {
        return Handikaped;
    }

    public void setHandikaped(String Handikaped) {
        this.Handikaped = Handikaped;
    }

    public Boolean isKosher(String type) {
        return Kosher.get(type);
    }

    public void setKosher(Map<String,Boolean> Kosher) {
        this.Kosher = Kosher;
    }




    public Businesses(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }



    public Businesses(String Name, String Image, String Address, String Description,
                      String Mail, String Region, Integer Bid, Integer business_type,
                      long[] Phone, Integer winter_price, Integer summer_price,
                      String Handikaped, Map<String,Boolean> Kosher) {
        this.Name = Name;
        this.Image = Image;
        this.Address = Address;
        this.Description = Description;
        this.Mail = Mail;
        this.Region = Region;
        this.Bid = Bid;
        this.business_type = business_type;
        this.Phone = Phone;
        this.winter_price = winter_price;
        this.summer_price = summer_price;
        this.Handikaped = Handikaped;
        this.Kosher = Kosher;
    }

}
