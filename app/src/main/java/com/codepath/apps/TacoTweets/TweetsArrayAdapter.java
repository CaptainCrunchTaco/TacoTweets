package com.codepath.apps.TacoTweets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.TacoTweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

//Taking the Tweets objects and turning them into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    //Override and setup our own custom template
    //ViewHolder
    private static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUsername;
        TextView tvBody;
        TextView tvTimestamp;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get tweet
        Tweet tweet = getItem(position);
        // 2. Find or inflate the template
        ViewHolder viewHolder; //Implement viewholder to increase performance
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            // 3. Find subviews to fill with data and the template
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 4. Populate data into the subviews
        viewHolder.tvUsername.setText(tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent); //clear out the old image for a recycled view
        viewHolder.tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
        // 5. Return the view to be inserted into the list
        return convertView;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            if (relativeDate.contains(" minutes ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" minutes ago")) + "m";
            } else if(relativeDate.contains(" minute ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" minute ago")) + "m";
            } else if(relativeDate.contains(" hour ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" hour ago")) + "h";
            } else if(relativeDate.contains(" hours ago")) {
                relativeDate = relativeDate.substring(0,relativeDate.indexOf(" hours ago")) + "h";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
