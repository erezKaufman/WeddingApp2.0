package com.example.erez0_000.weddingapp.Login_pages;

import com.example.erez0_000.weddingapp.db_classes.Businesses;

public class BusinessesInChart {
    private Businesses curBusiness;
    private int minPrice, maxPrice;

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
