package com.example.newsapp.retrofit;

import com.example.newsapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIList {

    @GET("top-headlines")
    Call<ResponseModel> getTrendingNews(@Query("country") String country, @Query("apiKey") String apiKey);
}
