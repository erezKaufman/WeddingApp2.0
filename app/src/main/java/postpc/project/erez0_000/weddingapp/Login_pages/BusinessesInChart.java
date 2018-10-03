package postpc.project.erez0_000.weddingapp.Login_pages;

import postpc.project.erez0_000.weddingapp.db_classes.Businesses;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import postpc.project.erez0_000.weddingapp.db_classes.Businesses;

public class BusinessesInChart implements Serializable {
    @SerializedName("curBusiness")
    private Businesses curBusiness;
    @SerializedName("minPrice")
    private int minPrice;
    @SerializedName("maxPrice")
    private int maxPrice;

    public BusinessesInChart(Businesses business, int minPrice, int maxPrice){
        this.curBusiness = business;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public Businesses getCurBusiness() {
        return curBusiness;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }
}
