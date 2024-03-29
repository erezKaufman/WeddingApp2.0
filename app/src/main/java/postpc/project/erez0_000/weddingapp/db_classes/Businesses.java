package postpc.project.erez0_000.weddingapp.db_classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class Businesses implements Serializable {
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
    @SerializedName("MeatKosher")
    private boolean MeatKosher;
    @SerializedName("MilkKosher")
    private boolean MilkKosher;
    @SerializedName("Handikaped")
    private String Handikaped;
    @SerializedName("Bid")
    private Integer Bid;
    @SerializedName("Business_Type")
    private String business_type;
    @SerializedName("Phones")
    private String[] Phones;
    @SerializedName("Winter")
    private Map<String,Integer> Winter;
    @SerializedName("Summer")
    private Map<String,Integer> Summer;
    private long[] OccupiedDates;

    public void setOccupiedDates(long[] occupiedDates) {
        OccupiedDates = occupiedDates;
    }

    public long[] getOccupiedDates() {
        return OccupiedDates;
    }

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

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String[] getPhone() {
        return Phones;
    }

    public void setPhone(String[] Phone) {
        this.Phones= Phone;
    }

    public Map<String,Integer>  getWinter_price() {
        return Winter;
    }

//    public void setWinter_price(Integer winter_price) {
//        this.Winter= winter_price;
//    }

    public Map<String,Integer> getSummer_price() {
        return Summer;
    }

//    public void setSummer_price(Integer summer_price) {
//        this.summer_price = summer_price;
//    }

    public String isHandikaped() {
        return Handikaped;
    }

    public void setHandikaped(String Handikaped) {
        this.Handikaped = Handikaped;
    }

    public Boolean isMeatKosher() {
        return MeatKosher;
    }

    public void setMeatKosher(boolean Kosher) {
        this.MeatKosher = Kosher;
    }

    public Boolean isMilkKosher() {
        return MilkKosher;
    }

    public void setMilkKosher(boolean Kosher) {
        this.MilkKosher= Kosher;
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
                      String Mail, String Region, Integer Bid, String business_type,
                      String[] Phone, Map<String,Integer>  winter_price, Map<String,Integer>  summer_price,
                      String Handikaped, boolean MeatKoshe, boolean MilkKosher) {
        this.Name = Name;
        this.Image = Image;
        this.Address = Address;
        this.Description = Description;
        this.Mail = Mail;
        this.Region = Region;
        this.Bid = Bid;
        this.business_type = business_type;
        this.Phones = Phone;
        this.Winter = winter_price;
        this.Summer = summer_price;
        this.Handikaped = Handikaped;
        this.MeatKosher= MeatKoshe;
        this.MilkKosher= MilkKosher;
    }

}
