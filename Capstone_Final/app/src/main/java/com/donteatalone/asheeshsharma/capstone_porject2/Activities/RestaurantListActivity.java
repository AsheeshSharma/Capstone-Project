package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import com.donteatalone.asheeshsharma.capstone_porject2.Adapters.RecycleViewRestaurantAdapter;
import com.donteatalone.asheeshsharma.capstone_porject2.Models.RestaurantOverview;
import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.NetworkUtility;
import com.donteatalone.asheeshsharma.capstone_porject2.data.RestaurantContract;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Asheesh.Sharma on 07-10-2016.
 */
public class RestaurantListActivity extends AppCompatActivity  {
    OkHttpClient client = new OkHttpClient();
    int collection_id;
    int entity_id;
    public static final String ENTITY_TYPEX ="city";
    private ListView recyclerView;
    public RecycleViewRestaurantAdapter recycleViewRestaurantAdapter;
    String query;
    int city_id;
    private static final String[] RESTAURANT_COLUMNS = {
            RestaurantContract.RestaurantEntry.COLUMN_RESID,
            RestaurantContract.RestaurantEntry.COLUMN_NAME,
            RestaurantContract.RestaurantEntry.COLUMN_LOCATION,
            RestaurantContract.RestaurantEntry.COLUMN_AVG_COST,
            RestaurantContract.RestaurantEntry.COLUMN_THUMB_URL,
            RestaurantContract.RestaurantEntry.COLUMN_FEATURED_IMAGE_URL,
            RestaurantContract.RestaurantEntry.COLUMN_USER_RATING,
            RestaurantContract.RestaurantEntry.COLUMN_RATING_TEXT,
            RestaurantContract.RestaurantEntry.COLUMN_CONTACT_NUMBER,
            RestaurantContract.RestaurantEntry.COLUMN_LATITUDE,
            RestaurantContract.RestaurantEntry.COLUMN_LONGITUDE,
            RestaurantContract.RestaurantEntry.COLUMN_TOTAL_REVIEWS_COUNT
    };

    public static final int COL_RESID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_LOCATION = 2;
    public static final int COL_AVG_COST = 3;
    public static final int COL_THUMB_URL = 4;
    public static final int COL_FEATURED_IMAGE_URL = 5;
    public static final int COL_USER_RATING = 6;
    public static final int COL_RATING_TEXT= 7;
    public static final int COL_CONTACT_NUMBER=8;
    public static final int COL_LATITUDE=9;
    public static final int COL_LONGITUDE=10;
    public static final int COL_TOTAL_REVIEWS_COUNT= 11;

    String c_id=null, name=null, latitude=null, longitude=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            c_id = b.containsKey("placeid")?b.getString("placeid"):null;
            name = b.containsKey("name")?b.getString("name"):null;
            latitude = b.containsKey("latitude")?b.getString("latitude"):null;
            longitude = b.containsKey("longitude")?b.getString("longitude"):null;
        }
        Log.d("cid", c_id + "" + name + "" + latitude + "" + longitude);
        /*setListView(Integer.parseInt(c_id));*/
        setContentView(R.layout.activity_rest_list);
        recyclerView = (ListView) findViewById(R.id.listView);
        recycleViewRestaurantAdapter = new RecycleViewRestaurantAdapter(this,R.layout.item_restau_list,new ArrayList<RestaurantOverview>());
        recyclerView.setAdapter(recycleViewRestaurantAdapter);
        //initProcessing(city_id,query);
        if(NetworkUtility.isNetworkAvailable(RestaurantListActivity.this)){
            if(c_id!=null && name!=null){
                initProcessing(Integer.parseInt(c_id),name);
            }else if(latitude!=null && longitude!=null){
                new FetchRestaurantsFromCollectionLatLong(latitude,longitude).execute();
            }

        }else{
            Toast.makeText(RestaurantListActivity.this,"No Network Detected, Using offline Information",Toast.LENGTH_SHORT).show();
            new FetchOfflineRestaurants(RestaurantListActivity.this).execute();
        }

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendToDetailActivity(position, view);
            }
        });


    }

    private void sendToDetailActivity(int position, View view){
        Intent intent = new Intent(this,RestaurantDetailActivity.class);
        int offset = recyclerView.getFirstVisiblePosition();
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        String transition_name = getString(R.string.transition_poster);
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, transition_name
        );
        if(latitude!=null && longitude!=null){
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
        }else if(c_id!=null && name!=null){
            intent.putExtra("placeid", c_id);
            intent.putExtra("name", name);
        }
        intent.putExtra("rest_info", recycleViewRestaurantAdapter.getItem(position));
        ActivityCompat.startActivity(this, intent, option.toBundle());

    }

    private void initProcessing(int city_id, String query){
        Log.d("Called","Called Init");
        new FetchCollectionId(city_id, query).execute();
    }


    private class FetchCollectionId extends AsyncTask<Void, Void, Integer>{
        ProgressDialog pd;
        int country_id;String query = "";
        public FetchCollectionId(int country_id, String query){
            this.country_id = country_id;
            this.query = query;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            try {
                if ((this.pd != null) && this.pd.isShowing()) {
                    this.pd.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                this.pd = null;
            }
            collection_id = integer;
            new FetchEntityIDforLocation(query, collection_id).execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RestaurantListActivity.this,R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Setting Up");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                /*RequestBody requestBody = new FormEncodingBuilder()
                        .add("city_id",Integer.toString(country_id))
                        .add("count","1")
                        .build();*/
                HttpUrl url = new HttpUrl.Builder()
                        .scheme("https")
                        .host("developers.zomato.com")
                        .addPathSegment("api")
                        .addPathSegment("v2.1")
                        .addPathSegment("collections")
                        .addQueryParameter("city_id", Integer.toString(country_id))
                        .addQueryParameter("count","1")
                        .build();
                Request request = new Request.Builder()
                        .header("user-key","774633d95336b16abe846ff3d114efd7")
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String res_string = response.body().string();
                JSONObject jsonObject = new JSONObject(res_string);
                JSONArray jsonArray = jsonObject.getJSONArray("collections");
                String id="";
                for(int i = 0; i < 1;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("collection"));
                    id = jsonObject2.getString("collection_id");
                }
                return Integer.parseInt(id);
            } catch (Exception e) {
                Log.e("ex",e.getMessage().toString() + "");
            }
            return -1;
        }
    }

    public class FetchEntityIDforLocation extends AsyncTask<Void, Void, Integer>{
        private String ccity_query="";
        private int collection_id = 0;
        ProgressDialog pd;
        public FetchEntityIDforLocation(String text_, int collection_id){
            ccity_query = text_;
            this.collection_id = collection_id;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            if(ccity_query == "" || ccity_query == null){
                return 0;
            }
            try {
                /*RequestBody requestBody = new FormEncodingBuilder()
                        .add("city_id",Integer.toString(country_id))
                        .add("count","1")
                        .build();*/
                HttpUrl url = new HttpUrl.Builder()
                        .scheme("https")
                        .host("developers.zomato.com")
                        .addPathSegment("api")
                        .addPathSegment("v2.1")
                        .addPathSegment("locations")
                        .addQueryParameter("query", ccity_query)
                        .addQueryParameter("count","1")
                        .build();
                Request request = new Request.Builder()
                        .header("user-key","774633d95336b16abe846ff3d114efd7")
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String res_string = response.body().string();
                JSONObject jsonObject = new JSONObject(res_string);
                JSONArray jsonArray = jsonObject.getJSONArray("location_suggestions");
                String entity_id="";
                for(int i = 0; i < 1;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    entity_id = jsonObject1.getString("entity_id");
                }
                Log.d("Entity", entity_id);
                return Integer.parseInt(entity_id);
            } catch (Exception e) {
                Log.e("ex",e.getMessage().toString() + "");
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            try {
                if ((this.pd != null) && this.pd.isShowing()) {
                    this.pd.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                this.pd = null;
            }
            entity_id = integer;
            new FetchRestaurantsFromCollection(collection_id, entity_id,ENTITY_TYPEX).execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RestaurantListActivity.this,R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Cleaning the Tables");
            pd.setCanceledOnTouchOutside(false);

            pd.show();
        }
    }


    public class FetchRestaurantsFromCollection extends AsyncTask<Void,Void, ArrayList<RestaurantOverview>>{
        ProgressDialog pd;

        int collection_id, city_id;
        String entity_type;
        public FetchRestaurantsFromCollection(int collection_id, int city_id, String entity_type){
            this.collection_id = collection_id;
            this.entity_type = entity_type;
            this.city_id = city_id;
        }
        @Override
        protected ArrayList<RestaurantOverview> doInBackground(Void... params) {
            ArrayList<RestaurantOverview> restaurantsList = new ArrayList<RestaurantOverview>();
            try {
                /*RequestBody requestBody = new FormEncodingBuilder()
                        .add("city_id",Integer.toString(country_id))
                        .add("count","1")
                        .build();*/
                HttpUrl url = new HttpUrl.Builder()
                        .scheme("https")
                        .host("developers.zomato.com")
                        .addPathSegment("api")
                        .addPathSegment("v2.1")
                        .addPathSegment("search")
                        .addQueryParameter("entity_id", Integer.toString(city_id))
                        .addQueryParameter("entity_type",entity_type)
                        .addQueryParameter("count","50")
                        .addQueryParameter("collection_id",Integer.toString(collection_id))
                        .addQueryParameter("sort","rating")
                        .addQueryParameter("order","desc")
                        .build();
                Request request = new Request.Builder()
                        .header("user-key","774633d95336b16abe846ff3d114efd7")
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String res_string = response.body().string();
                JSONObject jsonObject = new JSONObject(res_string);
                JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
                getContentResolver().delete(RestaurantContract.RestaurantEntry.CONTENT_URI,null,null);
                for(int i = 0; i < jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("restaurant");
                    String res_id = jsonObject1.getString("id");
                    String thumb_url = jsonObject1.getString("thumb");
                    String avg_cost = jsonObject1.getString("average_cost_for_two");
                    String name = jsonObject1.getString("name");
                    String feat_image = jsonObject1.getString("featured_image");
                    JSONObject ratingObject = jsonObject1.getJSONObject("user_rating");
                    String avg_rating = ratingObject.getString("aggregate_rating");
                    String rate_text = ratingObject.getString("rating_text");
                    String total_votes = ratingObject.getString("votes");
                    JSONObject locationObject = jsonObject1.getJSONObject("location");
                    String address = locationObject.getString("address") + "," + locationObject.getString("locality") + "," + locationObject.getString("city");
                    String latitude = locationObject.getString("latitude");
                    String longitude = locationObject.getString("longitude");
                    RestaurantOverview restaurantOverview = new RestaurantOverview(res_id,name,address,avg_cost,thumb_url,feat_image,avg_rating,rate_text,"",latitude,longitude,total_votes);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_RESID,res_id);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_NAME,name);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LOCATION,address);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_AVG_COST,avg_cost);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_THUMB_URL,thumb_url);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_FEATURED_IMAGE_URL,feat_image);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_USER_RATING,avg_rating);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_RATING_TEXT,rate_text);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_CONTACT_NUMBER,"9999999999");
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LATITUDE,latitude);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LONGITUDE,longitude);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_REVIEWS_COUNT,total_votes);
                    getContentResolver().insert(RestaurantContract.RestaurantEntry.CONTENT_URI, contentValues);
                    restaurantsList.add(restaurantOverview);
                }
                return restaurantsList;
            } catch (Exception e) {
                Log.e("ex",e.getMessage().toString() + "");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RestaurantListActivity.this,R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Loading Filtered Results");
            pd.setCanceledOnTouchOutside(false);

            pd.show();
        }

        @Override
        protected void onPostExecute(ArrayList<RestaurantOverview> result) {
            super.onPostExecute(result);
            if(result != null) {
                try {
                    if ((this.pd != null) && this.pd.isShowing()) {
                        this.pd.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {
                    this.pd = null;
                }
                recycleViewRestaurantAdapter.clear();
                for(RestaurantOverview info : result) {
                    recycleViewRestaurantAdapter.add(info);
                }
            }
        }
    }

    public class FetchOfflineRestaurants extends AsyncTask<Void, Void, ArrayList<RestaurantOverview>>{
        Context mContext;
        public FetchOfflineRestaurants(Context context){
            mContext = context;
        }

        private ArrayList<RestaurantOverview> returnDataFromDatabase(Cursor cursor){
            ArrayList<RestaurantOverview> resultList = new ArrayList<RestaurantOverview>();
            if(cursor!=null && cursor.moveToNext()){
                do{
                    RestaurantOverview restaurantOverview = new RestaurantOverview(cursor);
                    resultList.add(restaurantOverview);
                }while (cursor.moveToNext());
            }
            return resultList;
        }

        @Override
        protected ArrayList<RestaurantOverview> doInBackground(Void... params) {
            try {
                Cursor cursor = mContext.getContentResolver().query(RestaurantContract.RestaurantEntry.CONTENT_URI, RESTAURANT_COLUMNS,null,null,null);
                return returnDataFromDatabase(cursor);
            } catch (Exception e) {
                Log.e("ex",e.getMessage().toString() + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RestaurantOverview> restaurantOverviews) {
            super.onPostExecute(restaurantOverviews);
            if(restaurantOverviews!=null){
                recycleViewRestaurantAdapter.setData(restaurantOverviews);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    //Fetching Restaurants List from Latitude Longitude
    public class FetchRestaurantsFromCollectionLatLong extends AsyncTask<Void,Void, ArrayList<RestaurantOverview>>{
        ProgressDialog pd;
        String latitude, longitude;
        String entity_type;
        public FetchRestaurantsFromCollectionLatLong(String latitude, String longitude){
            this.latitude = latitude;
            this.entity_type = entity_type;
            this.longitude = longitude;
        }
        @Override
        protected ArrayList<RestaurantOverview> doInBackground(Void... params) {
            ArrayList<RestaurantOverview> restaurantsList = new ArrayList<RestaurantOverview>();
            try {
                /*RequestBody requestBody = new FormEncodingBuilder()
                        .add("city_id",Integer.toString(country_id))
                        .add("count","1")
                        .build();*/
                HttpUrl url = new HttpUrl.Builder()
                        .scheme("https")
                        .host("developers.zomato.com")
                        .addPathSegment("api")
                        .addPathSegment("v2.1")
                        .addPathSegment("search")
                        .addQueryParameter("lat", latitude)
                        .addQueryParameter("lon",longitude)
                        .addQueryParameter("count","250")
                        .addQueryParameter("sort","rating")
                        .addQueryParameter("order","desc")
                        .build();
                Request request = new Request.Builder()
                        .header("user-key","774633d95336b16abe846ff3d114efd7")
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String res_string = response.body().string();
                JSONObject jsonObject = new JSONObject(res_string);
                JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
                getContentResolver().delete(RestaurantContract.RestaurantEntry.CONTENT_URI,null,null);
                for(int i = 0; i < jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("restaurant");
                    String res_id = jsonObject1.getString("id");
                    String thumb_url = jsonObject1.getString("thumb");
                    String avg_cost = jsonObject1.getString("average_cost_for_two");
                    String name = jsonObject1.getString("name");
                    String feat_image = jsonObject1.getString("featured_image");
                    JSONObject ratingObject = jsonObject1.getJSONObject("user_rating");
                    String avg_rating = ratingObject.getString("aggregate_rating");
                    String rate_text = ratingObject.getString("rating_text");
                    String total_votes = ratingObject.getString("votes");
                    JSONObject locationObject = jsonObject1.getJSONObject("location");
                    String address = locationObject.getString("address") + "," + locationObject.getString("locality") + "," + locationObject.getString("city");
                    String latitude = locationObject.getString("latitude");
                    String longitude = locationObject.getString("longitude");
                    RestaurantOverview restaurantOverview = new RestaurantOverview(res_id,name,address,avg_cost,thumb_url,feat_image,avg_rating,rate_text,"",latitude,longitude,total_votes);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_RESID,res_id);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_NAME,name);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LOCATION,address);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_AVG_COST,avg_cost);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_THUMB_URL,thumb_url);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_FEATURED_IMAGE_URL,feat_image);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_USER_RATING,avg_rating);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_RATING_TEXT,rate_text);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_CONTACT_NUMBER,"9999999999");
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LATITUDE,latitude);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_LONGITUDE,longitude);
                    contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_TOTAL_REVIEWS_COUNT,total_votes);
                    getContentResolver().insert(RestaurantContract.RestaurantEntry.CONTENT_URI, contentValues);
                    restaurantsList.add(restaurantOverview);
                }
                return restaurantsList;
            } catch (Exception e) {
                Log.e("ex",e.getMessage().toString() + "");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RestaurantListActivity.this,R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Loading Filtered Results");
            pd.setCanceledOnTouchOutside(false);

            pd.show();
        }

        @Override
        protected void onPostExecute(ArrayList<RestaurantOverview> result) {
            super.onPostExecute(result);
            if(result != null) {
                try {
                    if ((this.pd != null) && this.pd.isShowing()) {
                        this.pd.dismiss();
                    }
                } catch (final IllegalArgumentException e) {
                    // Handle or log or ignore
                } catch (final Exception e) {
                    // Handle or log or ignore
                } finally {
                    this.pd = null;
                }
                recycleViewRestaurantAdapter.clear();
                for(RestaurantOverview info : result) {
                    recycleViewRestaurantAdapter.add(info);
                }
            }
        }
    }


}
