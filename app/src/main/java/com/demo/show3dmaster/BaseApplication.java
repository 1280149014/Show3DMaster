package com.demo.show3dmaster;

import android.app.Application;

import org.ok.util.android.AndroidURLStreamHandlerFactory;

import java.net.URL;



public class BaseApplication extends Application {

    private static String TAG = "BaseAppliacation";

    private static Application app;

    public static Application get(){
        return  app;
    }

    // Custom handler: org/andresoviedo/util/android/assets/Handler.class
    static {
        System.setProperty("java.protocol.handler.pkgs", "org.ok.util.android");
        URL.setURLStreamHandlerFactory(new AndroidURLStreamHandlerFactory());
    }


    @Override
    public void onCreate() {
        super.onCreate();

        app = this ;
    }


}
