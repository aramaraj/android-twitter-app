package com.codepath.apps.adalwintweets.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.fragments.HomeTimeLineFragment;
import com.codepath.apps.adalwintweets.fragments.MentionsTimeLineFragment;
import com.codepath.apps.adalwintweets.models.Tweet;
import com.codepath.apps.adalwintweets.models.Tweets;
import com.codepath.apps.adalwintweets.models.User;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient twitterClient;
    private final int TIMELINE_REQUEST_CODE = 1001;
    private Tweets tweetsModel;
    private HomeTimeLineFragment homeTimeLineFragment;
    private MentionsTimeLineFragment mentionsTimeLineFragment;
    User loggedInUser;
    User userParcel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        twitterClient = TwitterApplication.getRestClient();
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        loggedInUser = TwitterApplication.getLoggedInUser();
        if (loggedInUser != null) {
            try{
                actionBar.setTitle("@"+loggedInUser.getName()); // set the top title
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setLogo(R.mipmap.ic_launcher_twitter);
            }catch(Exception e){
            }
        }else{
            actionBar.setTitle("Timeline"); // set the top title
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setLogo(R.mipmap.ic_launcher_twitter);
        }
        //get the viewPager
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        //set the view pager adapter for the pager
        vpPager.setAdapter(new TwitterPagerAdapter(getSupportFragmentManager()));
        //find the pager sliding tabstrip
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        //Attach the tabstrip  to the viewpager
        pagerSlidingTabStrip.setViewPager(vpPager);
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
        }
        if (item.getItemId() == R.id.menu_item_profile) {
            startProfileActivity(loggedInUser);
            return true;
        }
        else {
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


    private void startProfileActivity(User userParcel){

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("userObject",userParcel);
        startActivityForResult(intent, TIMELINE_REQUEST_CODE);

    }
    private User populateUserHeaderModel(String screenName) {

        twitterClient.getUserInfo(screenName,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                userParcel=User.fromJSON(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);

            }
        });
        return userParcel;

    }

    public class TwitterPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        private String tabTitles[] = {"Home","Mentions"};
        private int tabIcons[] = {R.mipmap.twitter_home, R.mipmap.twitter_mention};

        //Adapter gets the manager from the Activity
        public TwitterPagerAdapter(FragmentManager fm){
            super(fm);
        }

        //order and creation of the fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTimeLineFragment();
            }else if (position == 1){
                return new MentionsTimeLineFragment();
            }else{
                return null;
            }
        }

        @Override
        public int getPageIconResId(int position) {
            return tabIcons[position];
        }

        @Override
        public int getCount() {
            return tabIcons.length;
        }
    }

}