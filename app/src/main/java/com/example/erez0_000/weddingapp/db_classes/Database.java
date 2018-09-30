package com.example.erez0_000.weddingapp.db_classes;

import org.json.JSONException;
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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
        service.getUserByCredentials(serializeCredentials(username, password)).enqueue(callback);
    }

    // The User object here should only have the username & password fields assigned
    public void signup(final User user, final Callback<User> callback) {
        // We can't sign up if username already exists
        service.getUserByCredentials(serializeCredentials(user.getUsername(), null)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
                    service.addUser(user).enqueue(callback);
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
        service.updateUser(serializeCredentials(user.getUsername(), user.getPassword()), user).enqueue(callback);
    }

    // Pass empty map if you want to fetch all businesses
    public void getBusinesses(Map<String, String> filters, Callback<List<Businesses>> callback) {
        service.getBusinesses(new JSONObject(filters).toString()).enqueue(callback);
    }

    public void getBusinessesByName(String nameSubstring, Callback<List<Businesses>> callback) {
        try {
            service.getBusinessesByName(
                    new JSONObject().put("Name", new JSONObject().put("$regex", nameSubstring)).toString())
                    .enqueue(callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface MlabService {
        @GET("Users?fo=true&apiKey=" + API_KEY)
        Call<User> getUserByCredentials(@Query("q") String credentials);

        @POST("Users?apiKey=" + API_KEY)
        Call<User> addUser(@Body User user);

        @PUT("Users?apiKey=" + API_KEY)
        Call<Void> updateUser(@Query("q") String credentials, @Body User user);

        @GET("Businesses?apiKey=" + API_KEY)
        Call<List<Businesses>> getBusinesses(@Query("q") String filters);

        @GET("Businesses?apiKey=" + API_KEY)
        Call<List<Businesses>> getBusinessesByName(@Query("q") String name);
    }
}
