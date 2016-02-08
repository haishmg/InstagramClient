package com.example.hganeshmurthy.instagramclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hganeshmurthy on 2/2/16.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {



    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvTimeSincePosting = (TextView) convertView.findViewById(R.id.tVTimeSincePosting);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
        ImageView ivPlay = (ImageView) convertView.findViewById(R.id.ivPlay);
        long currentTime = System.currentTimeMillis();

        if (photo.getCaptionCreatedTime() != null) {
            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(photo.getCaptionCreatedTime() * 1000, currentTime, 60000);
            if (relativeTime.toString().contains("minutes"))
                relativeTime = relativeTime.toString().replace("minutes ago", "m");
            else if (relativeTime.toString().contains("minute"))
                relativeTime = relativeTime.toString().replace("minute ago", "m");
            else if (relativeTime.toString().contains("hours"))
                relativeTime = relativeTime.toString().replace("hours ago", "h");
            else if (relativeTime.toString().contains("hour"))
                relativeTime = relativeTime.toString().replace("hour ago", "h");
            else if (relativeTime.toString().contains("weeks"))
                relativeTime = relativeTime.toString().replace("weeks ago", "w");
            else if (relativeTime.toString().contains("week"))
                relativeTime = relativeTime.toString().replace("week ago", "w");
            tvTimeSincePosting.setText(relativeTime);
        }



        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePicture);

        tvCaption.setText(photo.getCaption());
        tvUserName.setText(photo.getUserName());
        tvLikes.setText(photo.getLikesCount() + " Likes");
        tvLocation.setText(photo.getLocation());


        ArrayList<PhotoComment> comments = new ArrayList<PhotoComment>();
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).placeholder(R.drawable.progress_animation)
                .into(ivPhoto);


        Picasso.with(getContext()).load(photo.getUserPhoto()).transform(new CircleTransform()).into(ivProfilePhoto);

        comments = new ArrayList<>();
        comments = photo.getComments();
        Long time;

        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Long> latestTimes = new ArrayList<>();

        ArrayList<PhotoComment> latestComments = new ArrayList<>();

        LinearLayout list = (LinearLayout) convertView.findViewById(R.id.tlComments);
        list.removeAllViews();
        for (PhotoComment comment : comments) {
            times.add(comment.getCreated_time());
        }

        if(photo.getVideoUrl() != null)
        {
            ivPlay.setVisibility(View.VISIBLE);
        }
        else
        {
            ivPlay.setVisibility(View.GONE);
        }
        latestTimes = getLatestTime(times);
        for (PhotoComment comment : comments) {
            if (latestTimes.contains(comment.getCreated_time())) {
                View line = LayoutInflater.from(getContext()).inflate(R.layout.photo_comments, null);
                TextView tvCommentUserName = (TextView) line.findViewById(R.id.tvCommentUserName);
                tvCommentUserName.setText(comment.getUsername() + "  ");
                TextView tvComment = (TextView) line.findViewById(R.id.tvComment);
                tvComment.setText(comment.getComment());
                list.addView(line);
            }
        }
        return convertView;
    }

    public ArrayList getLatestTime(ArrayList<Long> times) {
        ArrayList<Long> latestTimes = new ArrayList<>();
        long max1 = 0;
        long max2 = 0;

        for (long time : times) {
            if (time > max1) {
                max2 = max1;
                max1 = time;
            } else if (time > max2) {
                max2 = time;
            }
        }
        latestTimes.add(max1);
        latestTimes.add(max2);
        return latestTimes;
    }



        public class CircleTransform implements Transformation {
            @Override
            public Bitmap transform(Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());

                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                if (squaredBitmap != source) {
                    source.recycle();
                }

                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader = new BitmapShader(squaredBitmap,
                        BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                paint.setShader(shader);
                paint.setAntiAlias(true);

                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);

                squaredBitmap.recycle();
                return bitmap;
            }

            @Override
            public String key() {
                return "circle";
            }

        }
    }