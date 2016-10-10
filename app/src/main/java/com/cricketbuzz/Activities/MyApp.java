package com.cricketbuzz.Activities;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.cricketbuzz.Utils.TypefaceUtil;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "SERIF.TTF");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}