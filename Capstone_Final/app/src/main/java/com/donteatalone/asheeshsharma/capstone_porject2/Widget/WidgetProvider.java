package com.donteatalone.asheeshsharma.capstone_porject2.Widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.donteatalone.asheeshsharma.capstone_porject2.Activities.RestaurantDetailActivity;
import com.donteatalone.asheeshsharma.capstone_porject2.Models.RestaurantOverview;
import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.donteatalone.asheeshsharma.capstone_porject2.data.RestaurantContract;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Asheesh.Sharma on 10-10-2016.
 */
public class WidgetProvider extends AppWidgetProvider {

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
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // To prevent any ANR timeouts, we perform the update in a service
        context.startService(new Intent(context, UpdateService.class));
    }

    public static class UpdateService extends Service {


        @Override
        public void onStart(Intent intent, int startId) {
            // Build the widget update for today
            RemoteViews updateViews = buildUpdate(this);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, WidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }

        public RemoteViews buildUpdate(Context context) {



            // Build an update that holds the updated widget contents
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            ArrayList<RestaurantOverview> reslist = upddateWidgetAsPerDb(context);

            updateViews.setTextViewText(R.id.textView, reslist.get(0).getName());
            ComponentName name = new ComponentName(context, WidgetProvider.class);
            int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
            if(reslist.get(0).getFeatured_image_url()!=null && !reslist.get(0).getFeatured_image_url().isEmpty() ){
                Picasso.with(context)
                        .load(reslist.get(0).getFeatured_image_url())
                        .into(updateViews, R.id.imageView, ids);
            }else{
                Picasso.with(context)
                        .load(R.drawable.sample_img2)
                        .into(updateViews, R.id.imageView, ids);
            }

            Intent intent = new Intent(this, RestaurantDetailActivity.class);
            intent.putExtra("rest_info",reslist.get(0));
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            updateViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);

            return updateViews;

        }
        private ArrayList<RestaurantOverview> returnDataFromDatabase(Cursor cursor){
            ArrayList<RestaurantOverview> resultList = new ArrayList<RestaurantOverview>();
            int cnt = 0;
            if(cursor!=null && cursor.moveToNext()){
                do{
                    if(cnt < 1){
                        RestaurantOverview restaurantOverview = new RestaurantOverview(cursor);
                        resultList.add(restaurantOverview);
                    }
                    cnt++;
                }while (cursor.moveToNext());
            }
            Log.d("Size", resultList.size() + "");
            return resultList;
        }
        public ArrayList<RestaurantOverview> upddateWidgetAsPerDb(Context context){
            Cursor cursor = context.getContentResolver().query(RestaurantContract.RestaurantEntry.CONTENT_URI, RESTAURANT_COLUMNS,null,null,null);
            return returnDataFromDatabase(cursor);
        }
        @Override
        public IBinder onBind(Intent intent) {
            // We don't need to bind to this service
            return null;
        }

    }

}