package com.example.topredditapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reddit {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("num_comments")
    @Expose
    private Long num_comments;
    @SerializedName("created")
    @Expose
    private Long created;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("thumbnail_height")
    @Expose
    private int thumbnail_height;
    @SerializedName("thumbnail_width")
    @Expose
    private int thumbnail_width;

    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }


    public String getSubreddit() {
        return subreddit;
    }


    public Long getNum_comments() {
        return num_comments;
    }


    public Long getCreated() {
        return created;
    }


    public String getThumbnail() {
        return thumbnail;
    }


    public int getThumbnail_height() {
        return thumbnail_height;
    }


    public int getThumbnail_width() {
        return thumbnail_width;
    }

}



