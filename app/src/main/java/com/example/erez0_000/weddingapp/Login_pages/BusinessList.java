package com.example.erez0_000.weddingapp.Login_pages;

import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessList {
    @SerializedName("employeeList")
    private List<Businesses> businesses;

    public List<Businesses> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Businesses> businesses) {
        this.businesses = businesses;
    }
}
