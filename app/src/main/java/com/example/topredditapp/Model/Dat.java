package com.example.topredditapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dat {
    @SerializedName("data")
    @Expose
    private Reddit dat;

    public Reddit getDat() {
        return dat;
    }
}
