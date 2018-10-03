package postpc.project.erez0_000.weddingapp.db_classes;

import postpc.project.erez0_000.weddingapp.Login_pages.BusinessesInChart;
import postpc.project.erez0_000.weddingapp.todos_section.TodoList;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import postpc.project.erez0_000.weddingapp.todos_section.TodoList;

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

    @SerializedName("minCurrentDestinedAmmount")
    private int minCurrentDestinedAmmount;

    @SerializedName("maxCurrentDestinedAmmount")
    private int maxCurrentDestinedAmmount;


    public int getMaxCurrentDestinedAmmount() {
        return maxCurrentDestinedAmmount;
    }

    public void setMaxCurrentDestinedAmmount(int maxCurrentDestinedAmmount) {
        this.maxCurrentDestinedAmmount = maxCurrentDestinedAmmount;
    }

    public int getMinCurrentDestinedAmmount() {
        return minCurrentDestinedAmmount;
    }

    public void setMinCurrentDestinedAmmount(int currentDestinedAmmount) {
        this.minCurrentDestinedAmmount= currentDestinedAmmount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBusinessInChart(ArrayList<BusinessesInChart> businessInChart) {
        this.businessInChart = businessInChart;
    }

    public void setBusinessesFavorites(ArrayList<Businesses> businessesFavorites) {
        this.businessesFavorites = businessesFavorites;
    }

        @SerializedName("todoArray")
    private ArrayList<TodoList> todoArray;

    @SerializedName("businessInChart")
    private ArrayList<BusinessesInChart> businessInChart;

    @SerializedName("businessesFavorites")
    private ArrayList<Businesses> businessesFavorites;

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

    public ArrayList<BusinessesInChart> getBusinessInChart() {
        return businessInChart;
    }

    public void addBusinessToChart(Businesses business,int minPrice, int maxPrice){
        if (businessInChart == null){
            businessInChart = new ArrayList<>();
        }
        businessInChart.add(new BusinessesInChart(business,minPrice,maxPrice));
    }
    public void addBusinessToFavorites(Businesses business){
        businessesFavorites.add(business);
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

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getArea() {
        return area;
    }

    public String getSeason() {
        return season;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public ArrayList<Businesses> getBusinessesFavorites() {
        return businessesFavorites;
    }

    public static User getThisUser() {
        return thisUser;
    }

    public String getCost() {
        return cost;
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

