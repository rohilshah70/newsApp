package com.example.newsapp.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsapp.Constants.NEWS_BASE_URL;

/**
 *
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static OkHttpClient client = new OkHttpClient.Builder().build();

    //Returns retrofit object that can be used throughout the application
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
}
