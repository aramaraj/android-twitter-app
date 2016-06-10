package com.codepath.apps.adalwintweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.fragments.TweetsListFragment;
import com.codepath.apps.adalwintweets.models.Tweet;
import com.codepath.apps.adalwintweets.models.Tweets;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient twitterClient;
    private final int TIMELINE_REQUEST_CODE = 1001;
    private Tweets tweetsModel;

    private TweetsListFragment tweetsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Tweets"); // set the top title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_twitter);

        twitterClient = TwitterApplication.getRestClient();
        if(savedInstanceState == null) {
            tweetsListFragment = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
        tweetsListFragment.addTweetsToTimeline();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_tweet) {
            Intent intent = new Intent(this, TweetActivity.class);
            startActivityForResult(intent, TIMELINE_REQUEST_CODE);
            return true;
        }
        if (item.getItemId() == R.id.menu_item_logout) {
            twitterClient.clearAccessToken();
            TimelineActivity.this.finish();
            return true;
        } else {
            // if a the new item is clicked show "Toast" message.
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIMELINE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("DEBUG -", "DoneCalling the on ACtivity Success populate timeline");
                Tweet tweet1 = (Tweet) data.getSerializableExtra("tweetObject");
                //tweets.add(0,tweet1);
                //tweetArrayAdapter.addAll(tweets);
                //tweetArrayAdapter.insert(tweet1,0);
                //this.tweetArrayAdapter.notifyDataSetChanged();
                //populateTimeline(0);
                Log.d("DEBUG -", "Done");
            }
        }
    }
    private void populateUserInfo() {
        twitterClient.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Response User info", response.toString());
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
            }
        });
    }



}

