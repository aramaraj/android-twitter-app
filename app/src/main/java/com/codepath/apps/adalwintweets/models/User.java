package com.codepath.apps.adalwintweets.models;

import org.json.JSONException;
import org.json.JSONObject;

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
public class User {
    private String name;
    private String screenName;
    private long uid;
    private String profileImage;

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

    public static User fromJSON(JSONObject jsonObject){
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.profileImage = jsonObject.getString("profile_image_url");
            user.screenName = jsonObject.getString("screen_name");
            user.uid = jsonObject.getLong("id");

        }catch(JSONException json){
            json.printStackTrace();
        }
        return user;

    }


}
