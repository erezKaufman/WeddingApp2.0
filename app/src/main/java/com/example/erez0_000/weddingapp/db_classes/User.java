package com.example.erez0_000.weddingapp.db_classes;

import com.example.erez0_000.weddingapp.todos_section.TodoList;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email, username, password, age, number, type, cost, area, season;
    private ArrayList<TodoList> todoArray ;

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

    public ArrayList<TodoList> getTodoArray() {
        return todoArray;
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
}

