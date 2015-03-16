package com.codepath.apps.TacoTweets.models;


/*

    [

        {
    "coordinates": null,
    "truncated": false,
    "created_at": "Tue Aug 28 21:16:23 +0000 2012",
    "favorited": false,
    "id_str": "240558470661799936",
    "in_reply_to_user_id_str": null,
    "text": "just another test",
    "contributors": null,
    "id": 240558470661799936,
    "retweet_count": 0,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "<a href="//realitytechnicians.com\"" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
    "user": {
      "name": "OAuth Dancer",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "created_at": "Wed Mar 03 19:37:35 +0000 2010",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "119476949",
      "is_translator": false,
      "profile_link_color": "0084B4"
    },
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null
  },

  {
    ...
  }

    ]

*/

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Parse the JSON + store the data + encapsulate state logic and display logic
@Table(name = "Tweets")
public class Tweet extends Model {
    //list out the attributes
    @Column(name = "body")
    private String body;
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid; // unique id for the tweet
    @Column(name = "user")
    private User user; // store embedded user object
    @Column(name = "createdAt")
    private String createdAt;

    public Tweet() {
        super();
    }
    public Tweet(String body, long uid, User user, String createdAt){
        super();
        this.body = body;
        this.uid = uid;
        this.user = user;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    //Tweet.fromJSONArray([{...}, {...}, {...}]) => List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Return the json array and create tweets
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject tweetJSON = jsonArray.getJSONObject(i);
                    Tweet tweet = Tweet.fromJSON(tweetJSON);
                    if (tweet != null) {
                        tweets.add(tweet);
                        tweet.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        //Return the finish list
        return tweets;
    }

    public static List<Tweet> fromSQLite() {
        // This is how you execute a query
        return new Select()
                .from(Tweet.class).orderBy("uid DESC")
                .execute();
    }


    // Deserialize the JSON and build Tweet objects
    // Tweet.fromJSON{"{...}"} => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        //Extract values from the JSON, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return the tweet object
        return tweet;
    }

}
