package com.codepath.apps.adalwintweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.models.User;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;


//login activity where the users would sign in
public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		getLoggedInUserDetails();
		Intent i = new Intent(this,TimelineActivity.class);
		startActivity(i);
		Toast.makeText(this, "Login Success	", Toast.LENGTH_SHORT).show();
	}
	private void getLoggedInUserDetails() {
		TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
				super.onSuccess(statusCode,headers,response);
				User user = new User();
				user=user.fromJSON(response);
				TwitterApplication.setLoggedInUser(user);
			}

			@Override
			public void onFailure(int statusCode,cz.msebera.android.httpclient.Header[] headers , Throwable error, JSONObject response) {
				super.onFailure(statusCode,headers , error, response);
			}
		});
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
