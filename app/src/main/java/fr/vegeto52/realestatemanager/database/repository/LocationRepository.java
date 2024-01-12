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
 * Created by Vegeto52-PC on 23/12/2023.
 */
public class LocationRepository {

    public MutableLiveData<Location> mLocationMutableLiveData = new MutableLiveData<>();

    public FusedLocationProviderClient mFusedLocationProviderClient;

    public LocationRepository() {
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        Context context = MainApplication.getApplication();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        Task<Location> task = mFusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(mLocationMutableLiveData::setValue);
    }

    public LiveData<Location> getLocationLiveData(){
        return mLocationMutableLiveData;
    }

}
