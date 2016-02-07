package com.example.hganeshmurthy.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hganeshmurthy on 2/6/16.
 */
public class CommentsAdapter  extends ArrayAdapter<PhotoComment> {
    public CommentsAdapter(Context context, ArrayList<PhotoComment> comments) {
        super(context, 0, comments);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PhotoComment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_comments, parent, false);
        }
        // Lookup view for data population
        TextView tvCommentUserName = (TextView) convertView.findViewById(R.id.tvCommentUserName);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        // Populate the data into the template view using the data object
        tvCommentUserName.setText(comment.getUsername()+ "  ");
        tvComment.setText(comment.getComment());
        // Return the completed view to render on screen
        return convertView;
    }
}
