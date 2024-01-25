package fr.vegeto52.realestatemanager.database;

import android.app.Application;

/**
 * The MainApplication class represents the main application instance.
 * It extends the Application class and serves as the entry point for the application.
 */
public class MainApplication extends Application {

    private static Application mApplication;


    /**
     * Called when the application is starting. This is where you should
     * initialize any global settings or resources for the application.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Set the application instance when it is created
        mApplication = this;
    }

    /**
     * Get the singleton instance of the application.
     *
     * @return The application instance.
     */
    public static Application getApplication() {
        return mApplication;
    }
}
