package fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment;

import android.location.Location;

import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The LocationViewState class represents the combined state of the user's location and a list of real estates.
 * It is used to provide a unified view of these data for the LocationFragment.
 */
public class LocationViewState {

    // The user's location
    private final Location mLocation;
    // The list of real estates
    private final List<RealEstate> mRealEstateList;

    /**
     * Constructor for LocationViewState.
     *
     * @param location        The user's location.
     * @param realEstateList  The list of real estates.
     */
    public LocationViewState(Location location, List<RealEstate> realEstateList) {
        mLocation = location;
        mRealEstateList = realEstateList;
    }

    /**
     * Get the user's location.
     *
     * @return Location
     */
    public Location getLocation() {
        return mLocation;
    }

    /**
     * Get the list of real estates.
     *
     * @return List<RealEstate>
     */
    public List<RealEstate> getRealEstateList() {
        return mRealEstateList;
    }
}
