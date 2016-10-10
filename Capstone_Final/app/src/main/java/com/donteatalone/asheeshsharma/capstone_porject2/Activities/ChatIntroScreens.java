package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.R;

/**
 * Created by Asheesh.Sharma on 09-10-2016.
 */
public class ChatIntroScreens extends BaseIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.app_intro_chat_1));
        addSlide(SampleSlide.newInstance(R.layout.app_intro_chat_2));
        addSlide(SampleSlide.newInstance(R.layout.app_intro_chat_3));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadChatActivity();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadChatActivity();
        /*Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();*/
    }

    public void getStarted(View v) {
        loadChatActivity();

    }
}
