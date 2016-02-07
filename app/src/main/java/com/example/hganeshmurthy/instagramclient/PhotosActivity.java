package com.example.hganeshmurthy.instagramclient;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class PhotosActivity extends AppCompatActivity  {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    @Bind ((R.id.swipeContainer)) SwipeRefreshLayout swipeContainer;
    private InstagramPhotoAdapter photoAdapter;
    AsyncHttpClient client;
    @Bind (R.id.lvPhotos)ListView lvPhotos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        photos = new ArrayList<>();
       // lvPhotos = (ListView) findViewById(R.id.lvPhotos);

         fetchPopulatPhotos();
         photoAdapter = new InstagramPhotoAdapter(this,photos);
         lvPhotos.setAdapter(photoAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                photoAdapter.clear();
                fetchPopulatPhotos();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }



    public void viewMoreComments(View v) {

        View parentRow = (View) v.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);

        FragmentManager fm = getFragmentManager();
        CommentsFragment commentsDialog = CommentsFragment.newInstance(photos.get(position).getComments());
        commentsDialog.show(fm, "Comments");


    }

    public void fetchPopulatPhotos()
    {
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray instagramPhotos = null;
                try {
                    instagramPhotos = response.getJSONArray("data");

                    for (int i = 0; i < instagramPhotos.length(); i++) {
                        JSONObject photoJson = instagramPhotos.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        if (photoJson.optJSONObject("user") != null) {
                            photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                            photo.setUserPhoto(photoJson.getJSONObject("user").getString("profile_picture"));
                        }
                        if (photoJson.optJSONObject("location") != null) {
                            photo.setLocation(photoJson.optJSONObject("location").getString("name"));
                        }
                        if (photoJson.optJSONObject("caption") != null) {
                            photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                            photo.setCaptionCreatedTime(Long.parseLong(photoJson.getJSONObject("caption").getString("created_time")));
                        }
                        if (photoJson.optJSONObject("images") != null) {
                            photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("height"));
                            photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        }
                        else if (photoJson.optJSONObject("videos") != null) {
                            photo.setImageHeight(photoJson.getJSONObject("videos").getJSONObject("standard_resolution").getString("height"));
                            photo.setImageUrl(photoJson.getJSONObject("videos").getJSONObject("standard_resolution").getString("url"));

                        }
                        if (photoJson.optJSONObject("likes") != null)
                            photo.setLikesCount(photoJson.getJSONObject("likes").getString("count"));
                        if (photoJson.optJSONObject("comments") != null) {
                            JSONArray comments = photoJson.getJSONObject("comments").getJSONArray("data");
                            ArrayList<PhotoComment> photocomments = new ArrayList<PhotoComment>();
                            for (int j = 0; j < comments.length(); j++) {
                                JSONObject commentJson = comments.getJSONObject(j);
                                String username = commentJson.getJSONObject("from").getString("username");
                                PhotoComment singleComment = new PhotoComment();
                                singleComment.setComment(commentJson.getString("text"));
                                singleComment.setCreated_time(commentJson.getLong("created_time"));
                                singleComment.setUsername(username);
                                photocomments.add(singleComment);
                            }

                            photo.setComments(photocomments);
                        }

                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }
}
