package com.example.hganeshmurthy.instagramclient;

import java.util.ArrayList;

/**
 * Created by hganeshmurthy on 2/2/16.
 */
public class InstagramPhoto {


    private String userName;
    private String imageUrl;
    private String caption;
    private String likesCount;
    private String imageHeight;
    private ArrayList<PhotoComment> comments;
    private String userPhoto;
    private String location;
    private Long captionCreatedTime;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(String videoHeight) {
        this.videoHeight = videoHeight;
    }

    private String videoUrl;
    private String videoHeight;


    public Long getCaptionCreatedTime() {
        return captionCreatedTime;
    }

    public void setCaptionCreatedTime(Long captionCreatedTime) {
        this.captionCreatedTime = captionCreatedTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public ArrayList<PhotoComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<PhotoComment> comments) {
        this.comments = comments;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }



}
