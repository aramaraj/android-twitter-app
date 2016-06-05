package com.codepath.apps.adalwintweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.adapters.TweetArrayAdapter;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.models.Tweet;
import com.codepath.apps.adalwintweets.models.Tweets;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.codepath.apps.adalwintweets.util.EndlessRecylcerScrollerListener;
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
    private Tweets tweetsModel;
    private final int TIMELINE_REQUEST_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTimeline= (ListView)findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        tweetArrayAdapter = new TweetArrayAdapter(this,tweets);
        lvTimeline.setAdapter(tweetArrayAdapter);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Tweets"); // set the top title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        twitterClient = TwitterApplication.getRestClient();
        populateUserInfo();
        populateTimeline(0);

        lvTimeline.setOnScrollListener(new EndlessRecylcerScrollerListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
                return true;
            }
            @Override
            public int getFooterViewType() {
                return 0;
            }
        });



    }
    //make an async request and list view
    private void populateTimeline(final int page) {

        twitterClient.getTimeLine(page,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,JSONArray response ) {
                super.onSuccess(statusCode, headers,response);
                final ArrayList<Tweet> tweets = tweetsModel.getModelsFromTweets(response);
                tweetArrayAdapter.addAll(tweets);
                tweetArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
            }
        });
    }

    private void populateUserInfo(){
        twitterClient.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,JSONArray response ) {
                Log.d("Response User info",response.toString());
                super.onSuccess(statusCode, headers,response);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_tweet){
            Intent intent = new Intent(this, TweetActivity.class);
            startActivityForResult(intent, TIMELINE_REQUEST_CODE);
            return true;
        }
        else{
            // if a the new item is clicked show "Toast" message.
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIMELINE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                System.out.println("Calling the on ACtivity Success populate timeline");
                populateTimeline(0);
                System.out.println("Calling the on ACtivity Success");
                Log.d("DEBUG --> ONACTIVITY","Done");
            }
        }
    }
}
