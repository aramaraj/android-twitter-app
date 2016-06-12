package com.codepath.apps.adalwintweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aramar1 on 6/3/16.
 *"id":71201743,
 "id_str":"71201743",
 "name":"Virat Kohli",
 "screen_name":"imVkohli",
 "location":"",
 "description":"The Official twitter account of Virat Kohli, Indian cricketer,gamer,car lover,loves soccer and an enthusiast",
 "url":"https:\/\/t.co\/OhSih7EL7w",
 *
 *
 *
 */
public class User implements Serializable{
    private String name;
    private String screenName;
    private long uid;
    private String profileImage;
    private String followersCount;
    private String friendsCount;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getScreenName() {
        return screenName;
    }
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getFollowersCount() {
        return followersCount;
    }
    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }
    public String getFriendsCount() {
        return friendsCount;
    }
    public void setFriendsCount(String friendsCount) {
        this.friendsCount = friendsCount;
    }
    public static User fromJSON(JSONObject jsonObject){
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.profileImage = jsonObject.getString("profile_image_url");
            user.screenName = jsonObject.getString("screen_name");
            user.uid = jsonObject.getLong("id");
            user.followersCount = jsonObject.getString("followers_count");
            user.friendsCount = jsonObject.getString("friends_count");
        }catch(JSONException json){
            json.printStackTrace();
        }
        return user;

    }
    /*
    [{"created_at":"Sat Jun 11 18:14:13 +0000 2016",
    "id":741695045024153600,
    "id_str":"741695045024153600",
    "text":"@AshokRamaraj testing mentions",
    "truncated":false,"entities":{"hashtags":[],"symbols":[],"user_mentions":[{"screen_name":"AshokRamaraj","name":"ashok ramaraj","id":1023133970,"id_str":"1023133970","indices":[0,13]}],"urls":[]},"source":"<a href=\"http:\/\/twitter.com\" rel=\"nofollow\">Twitter Web Client<\/a>","in_reply_to_status_id":null,"in_reply_to_status_id_str":null,"in_reply_to_user_id":1023133970,"in_reply_to_user_id_str":"1023133970","in_reply_to_screen_name":"AshokRamaraj","user":{"id":741694242888650755,"id_str":"741694242888650755","name":"ramarajashok","screen_name":"aramar0118","location":"","description":"","url":null,"entities":{"description":{"urls":[]}},"protected":false,"followers_count":1,"friends_count":22,"listed_count":0,"created_at":"Sat Jun 11 18:11:02 +0000 2016","favourites_count":0,"utc_offset":null,"time_zone":null,"geo_enabled":false,"verified":false,"statuses_count":1,"lang":"en","contributors_enabled":false,"is_translator":false,"is_translation_enabled":false,"profile_background_color":"F5F8FA","profile_background_image_url":null,"profile_background_image_url_https":null,"profile_background_tile":false,"profile_image_url":"http:\/\/abs.twimg.com\/sticky\/default_profile_images\/default_profile_4_normal.png","profile_image_url_https":"https:\/\/abs.twimg.com\/sticky\/default_profile_images\/default_profile_4_normal.png","profile_link_color":"2B7BB9","profile_sidebar_border_color":"C0DEED","profile_sidebar_fill_color":"DDEEF6","profile_text_color":"333333","profile_use_background_image":true,"has_extended_profile":false,"default_profile":true,"default_profile_image":true,"following":false,"follow_request_sent":false,"notifications":false},"geo":null,"coordinates":null,"place":null,"contributors":null,"is_quote_status":false,"retweet_count":0,"favorite_count":0,"favorited":false,"retweeted":false,"lang":"en"}]
     */


}
