package com.donteatalone.asheeshsharma.capstone_porject2.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.donteatalone.asheeshsharma.capstone_porject2.R;

/**
 * Created by Asheesh.Sharma on 06-10-2016.
 */
public class SharedPrefUtils {
    public final static String SPLASH_SHOWN = "d";
    public final static String LOGIN_COMPLETED = "l";
    public final static String ONE_TIME_CHAT_INTRO = "ct";
    public  static void setSplashShown(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref_splash), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SPLASH_SHOWN, 1);
        editor.commit();
    }

    public static int getSplashShown(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref_splash), context.MODE_PRIVATE);
        return sharedPref.getInt(SPLASH_SHOWN,0);
    }

    public static void setloginSuccessfull(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref_login), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(LOGIN_COMPLETED, 1);
        editor.commit();
    }
    public static int getloginSuccessfull(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref_login), context.MODE_PRIVATE);
        return sharedPref.getInt(LOGIN_COMPLETED,0);
    }

    public static void setoneTimeChatIntro(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(ONE_TIME_CHAT_INTRO, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ONE_TIME_CHAT_INTRO,1);
        editor.commit();
    }

    public static int getOneTimeChatIntro(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(ONE_TIME_CHAT_INTRO, context.MODE_PRIVATE);
        return sharedPref.getInt(ONE_TIME_CHAT_INTRO,0);
    }
}
