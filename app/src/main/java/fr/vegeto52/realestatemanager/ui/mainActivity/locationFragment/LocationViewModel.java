package fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.LocationRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The LocationViewModel class extends ViewModel and serves as the ViewModel for the LocationFragment.
 * It observes the LocationRepository and RealEstateRoomRepository to provide a combined LocationViewState.
 */
public class LocationViewModel extends ViewModel {

    // MediatorLiveData to combine Location and List<RealEstate> into LocationViewState
    private final MediatorLiveData<LocationViewState> mLocationViewStateMediatorLiveData = new MediatorLiveData<>();

    /**
     * Constructor for LocationViewModel.
     *
     * @param locationRepository        The repository providing the user's location.
     * @param realEstateRoomRepository  The repository providing the list of real estates.
     */
    public LocationViewModel(LocationRepository locationRepository, RealEstateRoomRepository realEstateRoomRepository) {

        // LiveData for user's location
        LiveData<Location> location = locationRepository.getLocationLiveData();
        // LiveData for the list of real estates
        LiveData<List<RealEstate>> listRealEstate = realEstateRoomRepository.getListRealEstate();

        // Combine location and listRealEstate LiveData using MediatorLiveData
        mLocationViewStateMediatorLiveData.addSource(location, location1 -> combine(location1, listRealEstate.getValue()));
        mLocationViewStateMediatorLiveData.addSource(listRealEstate, listRealEstate1 -> combine(location.getValue(), listRealEstate1));
    }

    /**
     * Combines the user's location and the list of real estates into a LocationViewState.
     *
     * @param location      The user's location.
     * @param realEstateList The list of real estates.
     */
    private void combine(Location location, List<RealEstate> realEstateList) {
        if (location != null && realEstateList != null) {
            // Provide the combined LocationViewState
            mLocationViewStateMediatorLiveData.setValue(new LocationViewState(location, realEstateList));
        }
    }

    /**
     * Get the LiveData for LocationViewState.
     *
     * @return LiveData<LocationViewState>
     */
    public LiveData<LocationViewState> getLocationViewState() {
        return mLocationViewStateMediatorLiveData;
    }
}
