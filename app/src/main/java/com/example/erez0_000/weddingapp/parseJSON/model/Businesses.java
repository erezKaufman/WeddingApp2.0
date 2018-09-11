package com.demotxt.myapp.parseJSON.model;

//public class Businesses {
//
//    private String name ;
//    private String Description;
//    private String rating;
//    private int nb_episode;
//    private String categorie;
//    private String studio ;
//    private String image_url;
//
//    public Businesses() {
//    }
//
//    public Businesses(String name, String description, String rating, int nb_episode, String categorie, String studio, String image_url) {
//        this.name = name;
//        Description = description;
//        this.rating = rating;
//        this.nb_episode = nb_episode;
//        this.categorie = categorie;
//        this.studio = studio;
//        this.image_url = image_url;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public String getRating() {
//        return rating;
//    }
//
//    public int getNb_episode() {
//        return nb_episode;
//    }
//
//    public String getCategorie() {
//        return categorie;
//    }
//
//    public String getStudio() {
//        return studio;
//    }
//
//    public String getImage_url() {
//        return image_url;
//    }
//
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public void setRating(String rating) {
//        this.rating = rating;
//    }
//
//    public void setNb_episode(int nb_episode) {
//        this.nb_episode = nb_episode;
//    }
//
//    public void setCategorie(String categorie) {
//        this.categorie = categorie;
//    }
//
//    public void setStudio(String studio) {
//        this.studio = studio;
//    }
//
//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
//}
//

//////////////////////////////////////////////////////////////////////////////////

public class Businesses {

    public String name;
    public String image;
    public String address;
    public String description;
    public String mail;
    public String region;
    public Integer bid, business_type, phone, winter_price, summer_price;
    public boolean handikaped, kosher;

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
