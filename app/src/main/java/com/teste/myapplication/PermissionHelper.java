package com.teste.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionHelper {

    public static final int MY_PERMISSIONS_REQUEST = 100;

    public static void askPermission(AppCompatActivity activity) {
        // Here, activity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                                              Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                                              new String[]{Manifest.permission.CAMERA},
                                              MY_PERMISSIONS_REQUEST);

        }
    }
}
