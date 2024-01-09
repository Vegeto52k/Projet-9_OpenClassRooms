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
 * Created by Vegeto52-PC on 23/12/2023.
 */
public class LocationViewModel extends ViewModel {

    private final MediatorLiveData<LocationViewState> mLocationViewStateMediatorLiveData = new MediatorLiveData<>();

    public LocationViewModel(LocationRepository locationRepository, RealEstateRoomRepository realEstateRoomRepository) {

        LiveData<Location> location = locationRepository.getLocationLiveData();
        LiveData<List<RealEstate>> listRealEstate = realEstateRoomRepository.getListRealEstate();

        mLocationViewStateMediatorLiveData.addSource(location, location1 -> combine(location1, listRealEstate.getValue()));
        mLocationViewStateMediatorLiveData.addSource(listRealEstate, listRealEstate1 -> combine(location.getValue(), listRealEstate1));
    }

    private void combine(Location location, List<RealEstate> realEstateList) {
        if (location != null && realEstateList != null) {
            mLocationViewStateMediatorLiveData.setValue(new LocationViewState(location, realEstateList));
        }
    }

    public LiveData<LocationViewState> getLocationViewState() {
        return mLocationViewStateMediatorLiveData;
    }
}
