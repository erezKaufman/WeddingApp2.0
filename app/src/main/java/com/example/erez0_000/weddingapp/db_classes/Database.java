package com.example.erez0_000.weddingapp.db_classes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    public interface MlabService {
        @GET("Users?q={\"username\": {u}, \"password\": {p}}&apiKey=" + API_KEY)
        Call<User> getUser(@Path("u") String username, @Path("p") String password);

        @POST("Users?apiKey=" + API_KEY)
        Call<User> addUser(@Field("username") String username, @Field("password") String password);

        // filter
    }
}
