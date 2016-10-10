package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

/**
 * Created by Asheesh.Sharma on 04-10-2016.
 */
import android.content.Intent;

import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.SharedPrefUtils;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by avluis on 08/08/2016.
 * Shared methods between classes
 */
public class BaseIntro extends AppIntro {

    void loadMainActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    void loadChatActivity(){
        SharedPrefUtils.setoneTimeChatIntro(getApplicationContext());
        Intent intent = new Intent(this, LoginChatActivity.class);
        startActivity(intent);
    }
}