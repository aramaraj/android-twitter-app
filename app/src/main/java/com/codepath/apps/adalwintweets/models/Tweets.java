package com.codepath.apps.adalwintweets.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aramar1 on 6/4/16.
 */
public class Tweets implements Serializable{

    private static ArrayList<Tweet> tweets;
    private static long max_id;

    public ArrayList<Tweet> getTweet() {
        return tweets;
    }

    public void setTweet(ArrayList<Tweet> tweet) {
        this.tweets = tweet;
    }


    public long getMax_id() {
        return max_id;
    }

    public void setMax_id(long since_id) {
        this.max_id = max_id;
    }

    public static ArrayList<Tweet> getModelsFromTweets(JSONArray jsonArray){
        tweets = new ArrayList<Tweet>();


        User user = new User();
        long temp1=0;
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet!=null){
                    tweets.add(tweet);
                    if(temp1==0) {
                        Tweets.max_id = tweet.getId();
                        temp1 = tweet.getId();
                    }
                    else{
                        Tweets.max_id=Math.min(temp1,tweet.getId());
                        temp1 = Tweets.max_id;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }




}
