package com.ian.codonib;

import android.app.Application;

import com.ian.codonib.network.ServiceFactory;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceFactory.init();
    }
}
