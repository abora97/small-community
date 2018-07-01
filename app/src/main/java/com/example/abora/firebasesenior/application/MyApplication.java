package com.example.abora.firebasesenior.application;

import android.app.Application;

import com.appizona.yehiahd.fastsave.FastSave;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FastSave.init(getApplicationContext());
    }
}
