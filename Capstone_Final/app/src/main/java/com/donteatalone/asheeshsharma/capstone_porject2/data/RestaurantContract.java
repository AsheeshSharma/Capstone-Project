package com.donteatalone.asheeshsharma.capstone_porject2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Asheesh.Sharma on 07-10-2016.
 */
public class RestaurantContract {
    public static final String CONTENT_AUTHORITY = "com.donteatalone.asheeshsharma.capstone_porject2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RESTAURANT = "restaurant";

    public static final class RestaurantEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANT).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;

        public static final String TABLE_NAME = "restaurant";

        public static final String COLUMN_RESID = "res_id";
        public static final String COLUMN_NAME= "name";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_AVG_COST= "avg_cost";
        public static final String COLUMN_THUMB_URL = "thumb_url";
        public static final String COLUMN_FEATURED_IMAGE_URL = "featured_url";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_RATING_TEXT = "rating_text";
        public static final String COLUMN_CONTACT_NUMBER = "cntct_number";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_TOTAL_REVIEWS_COUNT = "total_rev_count";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
