package com.example.erez0_000.weddingapp.todos_section;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildItemSample implements Serializable{
    @SerializedName("checked")
    private boolean checked;
    @SerializedName("name")
    private String name;

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ChildItemSample(){
        checked = false;
        name = "";
    }
    public ChildItemSample(String name){
        checked = false;
        this.name = name;
    }
}