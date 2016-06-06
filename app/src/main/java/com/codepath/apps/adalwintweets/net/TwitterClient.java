package com.codepath.apps.adalwintweets.net;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.adalwintweets.models.PostRequestParams;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */

public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "3ui9JxGJNDHxf7qGwgwvJW9lw";       // Change this
	public static final String REST_CONSUMER_SECRET = "9TZdBnFqviiNIRsnj79LOKlTrDI1ENO55GcWqIJaBBhNxuAjcX"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://adalwintweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {

		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
		context.getApplicationInfo();
	}

	//TODO Time line method
	public void getTimeLine(long max_id,JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		Log.d("Response to Srting",params.toString());
		Log.d("Response to Srting 1",String.valueOf(max_id));
		params.put("count",25);

		if(max_id>0){
			params.put("max_id",(max_id-1));
		}
		else{
			params.put("since_id",1);
		}
		getClient().get(apiUrl, params, handler);

	}
	//Composing a tweet
	//https://api.twitter.com/1.1/users/show.json?screen_name=twitterdev
	public void getUserInfo(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("screen_name","twitterdev");
		getClient().get(apiUrl, params, handler);

	}
	//Composin

	//Composing a MESSAGE
	//https://api.twitter.com/1.1/users/show.json?screen_name=twitterdev
	public void postMessage(PostRequestParams postRequestParams,JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("direct_messages/new.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("screen_name",postRequestParams.getTweetScreenName());
		params.put("text",postRequestParams.getTweetBody());
		System.out.println(postRequestParams.getTweetScreenName()+"This is"+postRequestParams.getTweetBody());
		getClient().post(apiUrl, params, handler);

	}

	public void postTweet(PostRequestParams postRequestParams,JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		//params.put("screen_name",postRequestParams.getTweetScreenName());
		params.put("status",postRequestParams.getTweetBody());
		getClient().post(apiUrl, params, handler);

	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}