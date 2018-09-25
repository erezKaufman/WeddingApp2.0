package com.example.erez0_000.weddingapp.db_classes;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class Database {
    private static final String BASE_URL = "https://api.mlab.com/api/1/databases/wedding_app/collections";
    private static final String API_KEY = "22IUrrMtRgv9WsRc8Nm9Ov72z9orvB1c";

    private static Database db;

    private MlabService service;

    public static Database getInstance() {
        if (db != null) return db;
        return db = new Database();
    }

    private Database() {
        service = new Retrofit.Builder().baseUrl(BASE_URL).build().create(MlabService.class);
    }

    public void signin(String username, String password, Callback<User> callback) {
        service.getUser(username, password).enqueue(callback);
    }

    public void signup(String username, String password, Callback<User> callback) {
        service.addUser(username, password).enqueue(callback);
    }

    //Use the callback here just to know when the operation's done
    public void updateUser(User user, Callback<String> callback) {
        service.updateUser(user.getUsername(), user.getPassword(), user).enqueue(callback);
    }

    // Pass empty map if you want to fetch all businesses
    public void getBusinesses(Map<String, String> filters, Callback<Businesses []> callback) {
        service.getBusinesses(filters).enqueue(callback);
    }

    public interface MlabService {
        @GET("Users?q={\"username\": {u}, \"password\": {p}}&apiKey=" + API_KEY)
        Call<User> getUser(@Path("u") String username, @Path("p") String password);

        @POST("Users?apiKey=" + API_KEY)
        Call<User> addUser(@Field("username") String username, @Field("password") String password);

        @PUT("Users?q={\"username\": {u}, \"password\": {p}}&apiKey=" + API_KEY)
        Call<String> updateUser(@Path("u") String username, @Path("p") String password, @Body User user);

        @GET("Businesses?apiKey=" + API_KEY)
        Call<Businesses []> getBusinesses(@QueryMap Map<String, String> filters);
    }
}
