package com.codepath.apps.adalwintweets.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.adapters.TweetArrayAdapter;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.models.Tweet;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient twitterClient;
    ListView lvTimeline;
    private TweetArrayAdapter tweetArrayAdapter ;
    private ArrayList<Tweet> tweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTimeline= (ListView)findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        tweetArrayAdapter = new TweetArrayAdapter(this,tweets);
        lvTimeline.setAdapter(tweetArrayAdapter);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Timeline"); // set the top title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        twitterClient = TwitterApplication.getRestClient();
        populateTimeline();


    }
    //make an async request and list view
    private void populateTimeline() {
        twitterClient.getTimeLine(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,JSONArray response ) {
                super.onSuccess(statusCode, headers,response);
                final ArrayList<Tweet> tweets = Tweet.getModelsFromTweets(response);
                tweetArrayAdapter.addAll(tweets);
                tweetArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
