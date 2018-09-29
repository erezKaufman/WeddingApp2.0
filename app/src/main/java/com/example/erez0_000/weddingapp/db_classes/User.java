package com.example.erez0_000.weddingapp.db_classes;

import com.example.erez0_000.weddingapp.todos_section.TodoList;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public transient static User thisUser;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("age")
    private String age;

    @SerializedName("number")
    private String number;

    @SerializedName("type")
    private String type;

    @SerializedName("cost")
    private String cost;

    @SerializedName("area")
    private String area;

    @SerializedName("season")
    private String season;

//    @SerializedName("todoArray")
    private transient ArrayList<TodoList> todoArray;

//    @SerializedName("businessInChart")
    private transient ArrayList<Businesses> businessInChart;

//    @SerializedName("businessesFavorites")
    private transient ArrayList<Businesses> businessesFavorites;

    public User() {}

    public User(String email,String username, String password, String age,String number,String type,String cost,
                String area,String season){
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.number = number;
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

    public void setTodoArray(ArrayList<TodoList> todoArray) {
        this.todoArray = todoArray;
    }

    public void addTodo(TodoList todoList){
        todoArray.add(todoList);
    }
    public void updateTodo(int pos, TodoList todoList){
        todoArray.set(pos,todoList);
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public void setCost(String cost) {
        this.cost = cost;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setType(String type) {
        this.type = type;
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

