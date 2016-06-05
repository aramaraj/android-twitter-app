package com.codepath.apps.adalwintweets.models;

import java.io.Serializable;

/**
 * Created by aramar1 on 6/4/16.
 */
public class PostRequestParams implements Serializable {

    public String tweetScreenName;
    public String tweetBody;
    public PostRequestParams (String tweetBody){

        this.tweetBody = tweetBody;
    }

    public String getTweetScreenName() {
        return tweetScreenName;
    }

    public void setTweetScreenName(String tweetScreenName) {
        this.tweetScreenName = tweetScreenName;
    }

    public String getTweetBody() {
        return tweetBody;
    }

    public void setTweetBody(String tweetBody) {
        this.tweetBody = tweetBody;
    }



}
