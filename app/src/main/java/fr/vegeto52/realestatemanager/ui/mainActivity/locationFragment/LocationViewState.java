package fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment;

import android.location.Location;

import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 23/12/2023.
 */
public class LocationViewState {

    private final Location mLocation;
    private final List<RealEstate> mRealEstateList;

    public LocationViewState(Location location, List<RealEstate> realEstateList) {
        mLocation = location;
        mRealEstateList = realEstateList;
    }

    public Location getLocation() {
        return mLocation;
    }

    public List<RealEstate> getRealEstateList() {
        return mRealEstateList;
    }
}
