package com.example.topredditapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("children")
    @Expose
    private List<Dat> Data;

    public List<Dat> getData() {
        return Data;
    }
}
