package com.example.topredditapp.API;

import com.example.topredditapp.Model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API_reddit {

    @GET("top.json")
    Call<Example> getRedditJSON();
}
