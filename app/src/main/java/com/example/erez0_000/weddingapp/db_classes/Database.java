package com.example.erez0_000.weddingapp.db_classes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Database {
    private static final String BASE_URL = "https://api.mlab.com/api/1/databases/wedding_app/collections/";
    private static final String API_KEY = "22IUrrMtRgv9WsRc8Nm9Ov72z9orvB1c";
    private static Database db;

    private MlabService service;

    public static Database getInstance() {
        if (db != null) return db;
        return db = new Database();
    }

    private Database() {
        service = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MlabService.class);
    }

    private static String serializeCredentials(final String username, final String password) {
        return new JSONObject(new HashMap<String, String>(){{
            put("username", username);
            if (password != null) put("password", password);
        }}).toString();
    }

    public void signin(String username, String password, Callback<User> callback) {
        service.getUserByCredentials(serializeCredentials(username, password), API_KEY).enqueue(callback);
    }

    // The User object here should only have the username & password fields assigned
    public void signup(final User user, final Callback<User> callback) {
        // We can't sign up if username already exists
        service.getUserByCredentials(serializeCredentials(user.getUsername(), null), API_KEY).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
                    service.addUser(user, API_KEY).enqueue(callback);
                } else {
                    callback.onFailure(call, new Error("Cannot sign-up - username already exists"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    //Use the callback here just to know when the operation's done
    public void updateUser(User user, Callback<Void> callback) {
        service.updateUser(serializeCredentials(user.getUsername(), user.getPassword()), user, API_KEY).enqueue(callback);
    }

    // Pass empty map if you want to fetch all businesses
    public void getBusinesses(Map<String, String> filters, Callback<List<Businesses>> callback) {
        service.getBusinesses(new JSONObject(filters).toString(), API_KEY).enqueue(callback);
    }

    public interface MlabService {
        @GET("Users")
        Call<User> getUserByCredentials(@Query("q") String credentials, @Query("apiKey") String apiKey);

        @POST("Users")
        Call<User> addUser(@Body User user, @Query("apiKey") String apiKey);

        @PUT("Users")
        Call<Void> updateUser(@Query("q") String credentials, @Body User user, @Query("apiKey") String apiKey);

        @GET("Businesses")
        Call<List<Businesses>> getBusinesses(@Query("q") String filters, @Query("apiKey") String apiKey);
    }
}
