package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.SharedPrefUtils;
import com.sendbird.android.SendBird;

public class SplashActivity extends AppCompatActivity {
    TextView appTitle, shortDesc;
    private static final String appId = "AEDC49F1-F121-4834-9C3D-EC4D4427A754"; /* Sample SendBird Application */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SendBird.init(appId, this);

        appTitle = (TextView)findViewById(R.id.textView);
        shortDesc = (TextView)findViewById(R.id.textView2);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/quartzo.ttf");
        appTitle.setTypeface(typeface);
        shortDesc.setTypeface(typeface);

        Log.e("VALUE",SharedPrefUtils.getloginSuccessfull(getApplicationContext()) + "");
        Log.e("VALUE",SharedPrefUtils.getSplashShown(getApplicationContext()) + "");
        if(SharedPrefUtils.getSplashShown(getApplicationContext())!=0 && SharedPrefUtils.getloginSuccessfull(getApplicationContext())==0){
            Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        else if(SharedPrefUtils.getSplashShown(getApplicationContext())!=0 && SharedPrefUtils.getloginSuccessfull(getApplicationContext())!=0){
            Intent intent = new Intent(SplashActivity.this, NearbyCitiesActivity.class);
            startActivity(intent);
        }else{
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3800);
                        SharedPrefUtils.setSplashShown(getApplicationContext());
                    } catch (InterruptedException e) {
                        e.setStackTrace(getStackTrace());
                    } finally {
                        Intent i = new Intent(SplashActivity.this, AppIntroScreens.class);
                        startActivity(i);
                        finish();
                    }

                }
            };
            timer.start();
        }


    }
}
