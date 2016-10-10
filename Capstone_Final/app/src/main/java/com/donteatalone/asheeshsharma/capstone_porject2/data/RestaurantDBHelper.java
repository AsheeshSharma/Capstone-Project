package com.donteatalone.asheeshsharma.capstone_porject2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Asheesh.Sharma on 07-10-2016.
 */
public class RestaurantDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "restaurant.db";

    public RestaurantDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + RestaurantContract.RestaurantEntry.TABLE_NAME + " (" +
                RestaurantContract.RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RestaurantContract.RestaurantEntry.COLUMN_RESID + " INTEGER NOT NULL, " +
                RestaurantContract.RestaurantEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                RestaurantContract.RestaurantEntry.COLUMN_LOCATION + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_AVG_COST + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_THUMB_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_FEATURED_IMAGE_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_USER_RATING + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_RATING_TEXT + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_CONTACT_NUMBER  + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_LATITUDE  + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_LONGITUDE + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_TOTAL_REVIEWS_COUNT + " TEXT);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.RestaurantEntry.TABLE_NAME);
        onCreate(db);
    }
}
