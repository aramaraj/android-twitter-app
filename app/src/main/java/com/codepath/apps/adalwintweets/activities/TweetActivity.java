package com.codepath.apps.adalwintweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.models.PostRequestParams;
import com.codepath.apps.adalwintweets.models.Tweet;
import com.codepath.apps.adalwintweets.models.User;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TweetActivity extends AppCompatActivity {
    private TwitterClient twitterClient;
    //EditText etAddress;
    EditText etBody;
    Button btnSave;
    Button btnCancel;
    PostRequestParams  postReqParams;
    boolean validateFields = false;
    private  Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        User loggedInUser = TwitterApplication.getLoggedInUser();
        if (loggedInUser != null) {
            try{
                getSupportActionBar().setTitle(loggedInUser.getName()); // set the top title
                //InputStream URLcontent = (InputStream) new URL(loggedInUser.getProfileImage()).getContent();
                //Drawable image = Drawable.createFromStream(URLcontent,);
                //getSupportActionBar().setLogo(image);
                //getSupportActionBar().setLogo(image);
                getSupportActionBar().setLogo(R.mipmap.ic_launcher_twitter);
            }catch(Exception e){
            }
        }else{
            getSupportActionBar().setTitle(loggedInUser.getName()); // set the top title
            getSupportActionBar().setLogo(R.mipmap.ic_launcher_twitter);
        }



        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        twitterClient = TwitterApplication.getRestClient();
        //etAddress=(EditText)findViewById(R.id.etTweetAddress);
        etBody = (EditText)findViewById(R.id.etBody);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()){
                    onTweet(v);
                }else{
                    Toast.makeText(TweetActivity.this, "Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                onCancel();
            }
        });




    }
    public void onTweet(View v){

        postReqParams=new PostRequestParams(String.valueOf(etBody.getText()));
        twitterClient.postTweet(postReqParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response ) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("Response from POST is  "+response.toString());
                tweet = Tweet.fromJSON(response);
                System.out.println("text"+tweet.getBody()+"::tweet.id::"+tweet.getId()+"User"+tweet.getUser().getName());
                Intent intent = new Intent();
                intent.putExtra("tweetObject",tweet);
                setResult(RESULT_OK, intent);
                finish();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
                System.out.println(response.toString());
            }
        });


    }
    public void onCancel(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public boolean validateFields(){
        String body=String.valueOf(etBody.getText());

        if(!body.isEmpty()){
            validateFields=true;
        }
        return validateFields;
    }


}
