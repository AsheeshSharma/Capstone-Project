package com.donteatalone.asheeshsharma.capstone_porject2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.donteatalone.asheeshsharma.capstone_porject2.Models.RestaurantOverview;
import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asheesh.Sharma on 07-10-2016.
 */
public class RecycleViewRestaurantAdapter extends ArrayAdapter<RestaurantOverview> {
    private Context context;
    private int res;
    private ArrayList<RestaurantOverview> myList;
    public RecycleViewRestaurantAdapter(Context context, int resource, ArrayList<RestaurantOverview> objects) {
        super(context,resource,objects);
        this.context=context;
        res=resource;
        myList=objects;
    }

    public void setData(List<RestaurantOverview> data) {
        clear();
        for (RestaurantOverview movie : data) {
            add(movie);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null)
        {
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(res,parent,false);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.title_textview = (TextView) convertView.findViewById(R.id.textView3);
            holder.price_textview = (TextView) convertView.findViewById(R.id.textView4);
            holder.rating_textview = (TextView) convertView.findViewById(R.id.textView6);
            holder.rate_text_textview = (TextView) convertView.findViewById(R.id.textView5);
            convertView.setTag(holder);
        }
        else
        {
            holder=(Holder)convertView.getTag();
        }
        if(myList.get(position).getThumb_url()!=null && !myList.get(position).getThumb_url().isEmpty() ){
            Picasso.with(context)
                    .load(myList.get(position).getThumb_url())
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img2)
                    .into(holder.imageView);
        }else{
            Picasso.with(context)
                    .load(R.drawable.sample_img2)
                    .fit().centerCrop()
                    .placeholder(R.drawable.sample_img2)
                    .into(holder.imageView);
        }

        holder.title_textview.setText(myList.get(position).getName());
        holder.price_textview.setText(myList.get(position).getAvg_cost());
        holder.rating_textview.setText(myList.get(position).getUser_rating());
        holder.rate_text_textview.setText(myList.get(position).getRating_text());
        return convertView;
    }
    static class Holder
    {
        ImageView imageView;
        TextView title_textview;
        TextView price_textview;
        TextView rating_textview;
        TextView rate_text_textview;
    }
}
