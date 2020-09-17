package com.demo.show3dmaster;

import android.app.Application;

import org.andresoviedo.util.android.AndroidURLStreamHandlerFactory;

import java.net.URL;



public class BaseApplication extends Application {

    private static String TAG = "BaseAppliacation";

    // Custom handler: org/andresoviedo/util/android/assets/Handler.class
    static {
        System.setProperty("java.protocol.handler.pkgs", "org.andresoviedo.util.android");
        URL.setURLStreamHandlerFactory(new AndroidURLStreamHandlerFactory());
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


}
