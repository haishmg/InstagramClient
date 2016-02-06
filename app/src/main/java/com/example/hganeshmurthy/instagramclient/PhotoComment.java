package com.example.hganeshmurthy.instagramclient;

/**
 * Created by hganeshmurthy on 2/2/16.
 */
public class PhotoComment {
    long created_time ;
    String comment;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
