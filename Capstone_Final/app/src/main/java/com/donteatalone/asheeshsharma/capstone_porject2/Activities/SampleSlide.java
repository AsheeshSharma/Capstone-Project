package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

/**
 * Created by Asheesh.Sharma on 04-10-2016.
 */
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donteatalone.asheeshsharma.capstone_porject2.R;

public class SampleSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static SampleSlide newInstance(int layoutResId) {
        SampleSlide sampleSlide = new SampleSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layoutResId, container, false);
        TextView title = (TextView) v.findViewById(R.id.intro_title);
        TextView desc = (TextView) v.findViewById(R.id.app_desc);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/quartzo.ttf");
        title.setTypeface(font);
        desc.setTypeface(font);
        return v;
    }
}