package com.example.topredditapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }
}
