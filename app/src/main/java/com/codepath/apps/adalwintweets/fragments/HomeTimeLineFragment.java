package com.codepath.apps.adalwintweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by aramar1 on 6/8/16.
 */
public class HomeTimeLineFragment extends Fragment implements OnRefreshListener {

    TwitterClient twitterClient;
    private SwipeRefreshLayout swipeContainer;
    private Tweets tweetsModel;
    ListView lvTimeline;
    private TweetArrayAdapter tweetArrayAdapter ;
    private ArrayList<Tweet> tweets;
    private long max_id_page = 0;
    private int page;

    //inflation logic and

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_tweets_list,parent,false);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        lvTimeline= (ListView)view.findViewById(R.id.lvTweets);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(this);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        tweetArrayAdapter = new TweetArrayAdapter(getActivity(),tweets);
        lvTimeline.setAdapter(tweetArrayAdapter);
        lvTimeline.setOnScrollListener(new EndlessRecylcerScrollerListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                System.out.println("739633557509918721 ON load more "+page +":::max_id_page"+max_id_page);
                populateTimeline(max_id_page);
                return true;
            }
            @Override
            public int getFooterViewType() {
                return 0;
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(max_id_page);
            }
        });


        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //this is where we do our lookups
        //and initialization


    }
    //creation life cycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        twitterClient = TwitterApplication.getRestClient();
        populateTimeline(0);
    }

    @Override
    public void onRefresh() {
        populateTimeline(max_id_page);
    }

    //make an async request and list view
    private void populateTimeline(final long page) {
        tweetsModel = new Tweets();
        twitterClient.getTimeLine(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                final ArrayList<Tweet> tweets = tweetsModel.getModelsFromTweets(response);
                if(max_id_page == 0){
                    populateTimeline(0);
                    tweetArrayAdapter.clear();
                }
                max_id_page=tweetsModel.getMax_id();
                tweetArrayAdapter.addAll(tweets);
                tweetArrayAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
            }
        });

    }


}
