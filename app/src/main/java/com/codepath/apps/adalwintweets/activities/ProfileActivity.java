package com.codepath.apps.adalwintweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.adalwintweets.R;
import com.codepath.apps.adalwintweets.app.TwitterApplication;
import com.codepath.apps.adalwintweets.fragments.UserTimeLineFragment;
import com.codepath.apps.adalwintweets.models.User;
import com.codepath.apps.adalwintweets.net.TwitterClient;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient twitterClient;
    ImageView imageView;
    TextView tvTitle;
    TextView tvScreenName;
    TextView tvFollowers;
    TextView tvFollowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        twitterClient=TwitterApplication.getRestClient();
        tvTitle = (TextView)findViewById(R.id.tvProfileTitle);
        tvScreenName = (TextView)findViewById(R.id.tvProfileScreen);
        imageView = (ImageView)findViewById(R.id.ivProfileImage);
        tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        tvFollowing = (TextView)findViewById(R.id.tvFollowing);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        User userObject =(User)getIntent().getSerializableExtra("userObject");
        //Get the Screen Name form the Logged in user
        if (userObject != null) {
            try {
                getSupportActionBar().setTitle("@" + userObject.getName()); // set the top title
                //InputStream URLcontent = (InputStream) new URL(loggedInUser.getProfileImage()).getContent();
                //Drawable image = Drawable.createFromStream(URLcontent,);
                //getSupportActionBar().setLogo(image);
                //getSupportActionBar().setLogo(image);
                getSupportActionBar().setIcon(R.mipmap.ic_launcher_twitter);
            } catch (Exception e) {
            }
        } else {
            getSupportActionBar().setTitle(userObject.getName()); // set the top title
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_twitter);
        }
        populateUserHeaderUi(userObject);
        if(savedInstanceState == null){
            //Create a UserTimeLineActivity
            UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(userObject.getName());
            //Display the User Fragment Dynamically
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer,userTimeLineFragment);
            fragmentTransaction.commit();
        }
    }

    public void populateUserHeaderUi(User user){

        tvTitle.setText(user.getName());
        tvScreenName.setText(user.getScreenName());
        Picasso.with(this).load(user.getProfileImage()).transform(new RoundedCornersTransformation(5,5)).into(imageView);
        tvFollowers.setText(user.getFollowersCount()+" Followers");
        tvFollowing.setText(user.getFriendsCount()+" Following");
    }

}
