package com.donteatalone.asheeshsharma.capstone_porject2.Models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.donteatalone.asheeshsharma.capstone_porject2.Activities.RestaurantListActivity;
import com.donteatalone.asheeshsharma.capstone_porject2.data.RestaurantContract;

/**
 * Created by Asheesh.Sharma on 07-10-2016.
 */
public class RestaurantOverview implements Parcelable{
    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(String avg_cost) {
        this.avg_cost = avg_cost;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getFeatured_image_url() {
        return featured_image_url;
    }

    public void setFeatured_image_url(String featured_image_url) {
        this.featured_image_url = featured_image_url;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getTotal_reviews_count() {
        return total_reviews_count;
    }

    public void setTotal_reviews_count(String total_reviews_count) {
        this.total_reviews_count = total_reviews_count;
    }

    private String res_id;
    private String name;
    private String location;
    private String avg_cost;
    private String thumb_url;
    private String featured_image_url;
    private String user_rating;
    private String rating_text;
    private String contact_number;
    private String total_reviews_count;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private String latitude;
    private String longitude;
    @Override
    public int describeContents() {
        return 0;
    }

    public RestaurantOverview(String res_id,String name,String location,String avg_cost,String thumb_url,String featured_image_url,String user_rating,String rating_text,String contact_number,String latitude, String longitude,String total_reviews_count){
        this.res_id = res_id;
        this.name = name;
        this.location = location;
        this.avg_cost = avg_cost;
        this.thumb_url = thumb_url;
        this.featured_image_url = featured_image_url;
        this.user_rating = user_rating;
        this.rating_text = rating_text;
        this.contact_number = contact_number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.total_reviews_count = total_reviews_count;
    }

    public RestaurantOverview(Cursor cursor){
        this.res_id = cursor.getString(RestaurantListActivity.COL_RESID);
        this.name = cursor.getString(RestaurantListActivity.COL_NAME);
        this.location = cursor.getString(RestaurantListActivity.COL_LOCATION);
        this.avg_cost = cursor.getString(RestaurantListActivity.COL_AVG_COST);
        this.thumb_url = cursor.getString(RestaurantListActivity.COL_THUMB_URL);
        this.featured_image_url = cursor.getString(RestaurantListActivity.COL_FEATURED_IMAGE_URL);
        this.user_rating = cursor.getString(RestaurantListActivity.COL_USER_RATING);
        this.rating_text = cursor.getString(RestaurantListActivity.COL_RATING_TEXT);
        this.contact_number = cursor.getString(RestaurantListActivity.COL_CONTACT_NUMBER);
        this.latitude = cursor.getString(RestaurantListActivity.COL_LATITUDE);
        this.longitude = cursor.getString(RestaurantListActivity.COL_LONGITUDE);
        this.total_reviews_count = cursor.getString(RestaurantListActivity.COL_TOTAL_REVIEWS_COUNT);
    }

    public RestaurantOverview(Parcel in){
        String[] strings = new String[12];
        in.readStringArray(strings);
        res_id = strings[0];
        name = strings[1];
        location = strings[2];
        avg_cost = strings[3];
        thumb_url = strings[4];
        featured_image_url = strings[5];
        user_rating = strings[6];
        rating_text = strings[7];
        contact_number = strings[8];
        latitude = strings[9];
        longitude = strings[10];
        total_reviews_count = strings[11];
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                res_id,
                name,
                location,
                avg_cost,
                thumb_url,
                featured_image_url,
                user_rating,
                rating_text,
                contact_number,
                latitude,
                longitude,
                total_reviews_count
        });
    }

    public static final Parcelable.Creator<RestaurantOverview> CREATOR = new Parcelable.Creator<RestaurantOverview>() {
        public RestaurantOverview createFromParcel(Parcel in) {
            return new RestaurantOverview(in);
        }

        public RestaurantOverview[] newArray(int size) {
            return new RestaurantOverview[size];
        }
    };
}
