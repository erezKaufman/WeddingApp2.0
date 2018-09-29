package com.example.erez0_000.weddingapp.db_classes;

import com.example.erez0_000.weddingapp.todos_section.TodoList;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    @SerializedName("thisUser")
    public static User thisUser;
    @SerializedName("_id")
    private String _id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("age")
    private String age;
    @SerializedName("numberOfInvited")
    private String numberOfInvited;
    @SerializedName("type")
    private String type;
    @SerializedName("cost")
    private String cost;
    @SerializedName("area")
    private String area;
    @SerializedName("season")
    private String season;
    @SerializedName("todoArray")
    private ArrayList<TodoList> todoArray ;
    @SerializedName("businessInChart")
    private ArrayList<Businesses> businessInChart;
    @SerializedName("businessesFavorites")
    private ArrayList<Businesses> businessesFavorites;

    public User() {}

    public User(String email,String username, String password, String age,String number,String type,String cost,
                String area,String season){
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.numberOfInvited = number;
        this.type = type;
        this.cost = cost;
        this.area = area;
        this.season = season;
        todoArray = new ArrayList<>();


    }

    public ArrayList<Businesses> getBusinessesLiked() {
        return businessesFavorites;
    }

    public ArrayList<Businesses> getBusinessInChart() {
        return businessInChart;
    }

    public void addBusinessToChart(Businesses business){
        businessInChart.add(business);
    }
    public void addBusinessToFavorites(Businesses business){
        businessInChart.add(business);
    }

    public ArrayList<TodoList> getTodoArray() {
        return todoArray;
    }

    public void addTodo(TodoList todoList){
        todoArray.add(todoList);
    }
    public void updateTodo(int pos, TodoList todoList){
        todoArray.set(pos,todoList);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


    public String getNumber() {
        return numberOfInvited;
    }

    public void setNumber(String number) {
        this.numberOfInvited = number;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

