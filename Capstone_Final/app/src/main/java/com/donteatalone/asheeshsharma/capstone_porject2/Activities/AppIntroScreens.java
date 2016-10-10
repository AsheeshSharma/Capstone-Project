package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

/**
 * Created by Asheesh.Sharma on 04-10-2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.R;


public final class AppIntroScreens extends BaseIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.app_intro_1));
        addSlide(SampleSlide.newInstance(R.layout.app_intro_2));
        addSlide(SampleSlide.newInstance(R.layout.app_intro_3));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        /*Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();*/
    }

    public void getStarted(View v) {
        loadMainActivity();

    }
}
