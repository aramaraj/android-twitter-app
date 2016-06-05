package com.codepath.apps.adalwintweets.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by aramar1 on 6/3/16.
 */
//Parse the Json + Store the Data +
public class Tweet {
    private String body;
    private long id;//unique id for  the Tweet.
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public com.codepath.apps.adalwintweets.models.User getUser() {
        return user;
    }

    public void setUser(com.codepath.apps.adalwintweets.models.User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    //Deserialize the JSON
    //Tweet.fromJSON
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        }catch (JSONException jsone){
            jsone.printStackTrace();
        }


        return tweet;
    }
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }



}

/*  "created_at":"Fri Jun 03 10:36:12 +0000 2016",
      "id":738680678926143488,
      "id_str":"738680678926143488",
      "text":"A million reasons to smile today; hosting a charity dinner to support the next gen of India #SmileWithVKF https:\/\/t.co\/3vC0rPmrUy",
      "truncated":false,
      "entities":{
         "hashtags":[
            {
               "text":"SmileWithVKF",
               "indices":[
                  92,
                  105
               ]
            }
         ],
         "symbols":[

         ],
         "user_mentions":[

         ],
         "urls":[

         ],
         "media":[
            {
               "id":738680641185800192,
               "id_str":"738680641185800192",
               "indices":[
                  106,
                  129
               ],
               "media_url":"http:\/\/pbs.twimg.com\/media\/CkBSISRWYAAv-Ip.jpg",
               "media_url_https":"https:\/\/pbs.twimg.com\/media\/CkBSISRWYAAv-Ip.jpg",
               "url":"https:\/\/t.co\/3vC0rPmrUy",
               "display_url":"pic.twitter.com\/3vC0rPmrUy",
               "expanded_url":"http:\/\/twitter.com\/imVkohli\/status\/738680678926143488\/photo\/1",
               "type":"photo",
*
* */
