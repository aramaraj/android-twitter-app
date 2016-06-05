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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Compose New Tweet"); // set the top title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
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
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                super.onFailure(statusCode, headers, error, response);
                System.out.println(response.toString());
            }
        });
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }
    public void onCancel(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public boolean validateFields(){
        //String address=String.valueOf(etAddress.getText());
        String body=String.valueOf(etBody.getText());
        //if(!address.isEmpty()){
          //  validateFields=true;
        //}
        if(!body.isEmpty()){
            validateFields=true;
        }
        return validateFields;
    }
}
