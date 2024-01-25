package fr.vegeto52.realestatemanager.database.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import fr.vegeto52.realestatemanager.database.MainApplication;

/**
 * The LocationRepository class serves as a repository for obtaining device location information.
 * It uses the FusedLocationProviderClient from the Google Play Services Location API.
 */
public class LocationRepository {

    public MutableLiveData<Location> mLocationMutableLiveData = new MutableLiveData<>();

    public FusedLocationProviderClient mFusedLocationProviderClient;

    /**
     * Default constructor for the LocationRepository class.
     * Initiates the process of obtaining the device location.
     */
    public LocationRepository() {
        getLocation();
    }

    /**
     * Request the device's last known location using the FusedLocationProviderClient.
     * The result is stored in the LiveData for observation.
     */
    @SuppressLint("MissingPermission")
    public void getLocation() {
        Context context = MainApplication.getApplication();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        Task<Location> task = mFusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(mLocationMutableLiveData::setValue);
    }

    /**
     * Get the LiveData object for observing device location changes.
     *
     * @return The LiveData object containing the device location.
     */
    public LiveData<Location> getLocationLiveData() {
        return mLocationMutableLiveData;
    }
}
