package com.codepath.apps.TacoTweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/*

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
    }

 */
@Table(name = "Users")
public class User extends Model{

    //list attributes
    @Column(name = "Name")
    private String name;
    @Column(name = "uid")
    private long uid;
    @Column(name = "screenName")
    private String screenName;
    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    private String tagline;

    private int followersCount;

    private int followingCount;


    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingCount;
    }

    //Deserialize the user JSON => User
    public static User fromJson(JSONObject jsonObject) {
        User u = new User();
        //Extract and fill the values
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagline = jsonObject.getString("description");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
            u.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Return a user
        return u;
    }


}
