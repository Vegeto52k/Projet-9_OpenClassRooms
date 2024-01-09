package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import fr.vegeto52.realestatemanager.database.repository.GeocodingRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;

/**
 * Created by Vegeto52-PC on 11/12/2023.
 */
public class AddFragmentViewModel extends ViewModel {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final GeocodingRepository mGeocodingRepository;
    private final MediatorLiveData<ResultsGeocodingApi> mResultsGeocodingApiMediatorLiveData = new MediatorLiveData<>();


    public AddFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository, GeocodingRepository geocodingRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
        mGeocodingRepository = geocodingRepository;
    }

    public void insertRealEstate(RealEstate realEstate) {
        mRealEstateRoomRepository.insertRealEstate(realEstate);
    }

    public long insertRealEstateAndGetId(RealEstate realEstate) {
        return mRealEstateRoomRepository.insertRealEstateAndGetId(realEstate);
    }

    public void insertPhoto(Photo photo) {
        mPhotoRoomRepository.insertPhoto(photo);
    }

    public void getGeocoding(String address, GeocodingCallback callback) {
        mGeocodingRepository.getGeocoding(address, resultsGeocodingApi -> {
            if (resultsGeocodingApi != null && !resultsGeocodingApi.getResults().isEmpty()) {
                ResultsGeocodingApi.Results.Geometry.Location location = resultsGeocodingApi.getResults().get(0).getGeometry().getLocation();
                if (location != null) {
                    Double latitude = location.getLat();
                    Double longitude = location.getLng();
                    callback.onGeocodingComplete(latitude, longitude);
                }
            }
        });
    }

    public interface GeocodingCallback {
        void onGeocodingComplete(Double latitude, Double longitude);
    }

    public LiveData<ResultsGeocodingApi> getGeocodingLiveData() {
        return mResultsGeocodingApiMediatorLiveData;
    }

}
