package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.GeocodingRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;

/**
 * Created by Vegeto52-PC on 28/11/2023.
 */
public class EditFragmentViewModel extends ViewModel {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final GeocodingRepository mGeocodingRepository;

    public EditFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository, GeocodingRepository geocodingRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
        mGeocodingRepository = geocodingRepository;
    }

    public LiveData<RealEstate> getRealEstateLiveData(long realEstateId) {
        return mRealEstateRoomRepository.getRealEstate(realEstateId);
    }

    public void updateRealEstate(RealEstate realEstate) {
        mRealEstateRoomRepository.updateRealEstate(realEstate);
    }

    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId) {
        return mPhotoRoomRepository.getListPhotoToRealEstate(realEstateId);
    }

    public void insertPhoto(Photo photo) {
        mPhotoRoomRepository.insertPhoto(photo);
    }

    public void deleteAllPhotos(long realEstateId) {
        mPhotoRoomRepository.deleteAllPhotos(realEstateId);
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
}
