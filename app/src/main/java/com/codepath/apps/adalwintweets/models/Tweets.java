package com.codepath.apps.adalwintweets.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aramar1 on 6/4/16.
 */
public class Tweets implements Serializable{

    private static ArrayList<Tweet> tweets;
    private static int since_id;

    public ArrayList<Tweet> getTweet() {
        return tweets;
    }

    public void setTweet(ArrayList<Tweet> tweet) {
        this.tweets = tweet;
    }


    public int getSince_id() {
        return since_id;
    }

    public void setSince_id(int since_id) {
        this.since_id = since_id;
    }

    public static ArrayList<Tweet> getModelsFromTweets(JSONArray jsonArray){
        tweets = new ArrayList<Tweet>();
        Tweets.since_id=since_id;
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
                    Log.d("Tweets ::",tweet.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }




}
