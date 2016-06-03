package com.codepath.apps.adalwintweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        }catch (JSONException jsone){
            jsone.printStackTrace();
        }


        return tweet;
    }

    public static ArrayList<Tweet> getModelsFromTweets(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        User user = new User();
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet!=null){
                    tweets.add(tweet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
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
