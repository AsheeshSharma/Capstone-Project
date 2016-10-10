package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.Models.RestaurantOverview;
import com.donteatalone.asheeshsharma.capstone_porject2.R;

import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.SharedPrefUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by Asheesh.Sharma on 08-10-2016.
 */
public class RestaurantDetailActivity extends AppCompatActivity {
    ImageView backImageView;
    ImageView posterView;
    TextView rateTextView;
    TextView avgRateTextView;
    TextView totalVotesTextView;
    TextView costAverageTextView;
    TextView nameTextView;
    TextView mapsTextView;
    TextView locationTextView;
    private RestaurantOverview restaurantOverview;
    private AdView mAdView;
    Button going_btn;
    Button not_going_btn;
    String placeid=null, name=null, latitude=null, longitude=null;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("B70FD138362BCAC68D99C74208104B59")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("Ad","Ad is loaded!");
            }

            @Override
            public void onAdClosed() {
                Log.d("Ad","Ad is closed!");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("Ad","Ad failed to load! error code: " + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("Ad","Ad left application!");
            }

            @Override
            public void onAdOpened() {
                Log.d("Ad","Ad is opened!");
            }
        });
        restaurantOverview = new RestaurantOverview("","","","","","","","","","","","");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int sdk;
        sdk = new Integer(Build.VERSION.SDK).intValue();
        if (sdk >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        backImageView = (ImageView)findViewById(R.id.imageView3);
        posterView = (ImageView) findViewById(R.id.imageView4);
        nameTextView = (TextView)findViewById(R.id.textView7);
        rateTextView = (TextView) findViewById(R.id.textView8);
        avgRateTextView = (TextView) findViewById(R.id.textView9);
        totalVotesTextView = (TextView) findViewById(R.id.textView10);
        locationTextView = (TextView) findViewById(R.id.textView11);
        mapsTextView = (TextView) findViewById(R.id.textView12);
        going_btn = (Button)findViewById(R.id.button);
        not_going_btn = (Button)findViewById(R.id.button2);
        going_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefUtils.getOneTimeChatIntro(getApplicationContext())!=0){
                    Intent intent = new Intent(RestaurantDetailActivity.this, LoginChatActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(RestaurantDetailActivity.this, ChatIntroScreens.class);
                    startActivity(intent);
                }
            }
        });
        not_going_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailActivity.this, RestaurantListActivity.class);
                if(latitude!=null && longitude!=null){
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                }else if(placeid!=null && name!=null){
                    intent.putExtra("placeid", placeid);
                    intent.putExtra("name", name);
                }
                startActivity(intent);
            }
        });
        setData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if(id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            restaurantOverview=bundle.getParcelable("rest_info");
            placeid = bundle.containsKey("placeid")?bundle.getString("placeid"):null;
            name = bundle.containsKey("name")?bundle.getString("name"):null;
            latitude = bundle.containsKey("latitude")?bundle.getString("latitude"):null;
            longitude = bundle.containsKey("longitude")?bundle.getString("longitude"):null;
        }
        getSupportActionBar().setTitle(restaurantOverview.getName());
        if(restaurantOverview.getFeatured_image_url()!=null && !restaurantOverview.getFeatured_image_url().isEmpty() ) {
            Picasso.with(this)
                    .load(restaurantOverview.getFeatured_image_url())
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img)
                    .into(backImageView);
        }else{
            Picasso.with(this)
                    .load(R.drawable.sample_img)
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img)
                    .into(backImageView);
        }
        if(restaurantOverview.getThumb_url()!=null && !restaurantOverview.getThumb_url().isEmpty() ) {
            Picasso.with(this)
                    .load(restaurantOverview.getThumb_url())
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img)
                    .into(posterView);
        }else{
            Picasso.with(this)
                    .load(R.drawable.sample_img)
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img)
                    .into(backImageView);
        }
        nameTextView.setText(restaurantOverview.getName());
        rateTextView.setText(restaurantOverview.getUser_rating() + " / 5");
        avgRateTextView.setText(restaurantOverview.getRating_text());
        totalVotesTextView.setText(restaurantOverview.getTotal_reviews_count() + " Votes");
        locationTextView.setText(restaurantOverview.getLocation());
        mapsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Lat", restaurantOverview.getLatitude() + "");
                Log.e("Long", restaurantOverview.getLongitude() + "");
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.parseFloat(restaurantOverview.getLatitude()), Float.parseFloat(restaurantOverview.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri + "?q=<" + restaurantOverview.getLatitude()  + ">,<" + restaurantOverview.getLongitude() + ">(" + restaurantOverview.getName() + ")"));

                startActivity(intent);
            }
        });
    }
}
