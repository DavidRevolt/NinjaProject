package com.example.ninja;

import android.app.Application;
import android.content.Context;


//CLASS USE FOR GET CONTEXT EVERYWHERE AND INITIATED IN MANIFEST
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}