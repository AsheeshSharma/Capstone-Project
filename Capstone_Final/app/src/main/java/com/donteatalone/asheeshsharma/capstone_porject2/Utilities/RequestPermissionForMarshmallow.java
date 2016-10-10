package com.donteatalone.asheeshsharma.capstone_porject2.Utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.R;

/**
 * Created by Asheesh.Sharma on 09-10-2016.
 */
public class RequestPermissionForMarshmallow implements ActivityCompat.OnRequestPermissionsResultCallback  {
    public static final int MY_PERMISSIONS_REQUEST = 1;
    int mcurrentapiVersion;
    private Context mcontext;
    public RequestPermissionForMarshmallow(int currentapiVersion, Context context) {

        mcurrentapiVersion = currentapiVersion;
        mcontext = context;


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(mcontext, mcontext.getString(R.string.pl_all), Toast.LENGTH_SHORT).show();

                }
                return;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void AskForLocationPermission() {
        if (mcurrentapiVersion >= android.os.Build.VERSION_CODES.M) {

            int hasWriteSMSPermission = mcontext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasWriteSMSPermission != PackageManager.PERMISSION_GRANTED) {
                ((Activity) mcontext).requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void AskForLocationCPermission() {
        if (mcurrentapiVersion >= android.os.Build.VERSION_CODES.M) {

            int hasWriteSMSPermission = mcontext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasWriteSMSPermission != PackageManager.PERMISSION_GRANTED) {
                ((Activity) mcontext).requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST);

            }
        }
    }

    public boolean checkLocationPermission()
    {

        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = mcontext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkLocationCPermission()
    {

        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = mcontext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
