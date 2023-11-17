package fr.vegeto52.realestatemanager.database;

import android.app.Application;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class MainApplication extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }
}
