package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

import androidx.lifecycle.ViewModel;

import fr.vegeto52.realestatemanager.database.repository.GeocodingRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;

/**
 * The AddFragmentViewModel class extends ViewModel and provides data-related functionality for the AddFragment.
 * It interacts with the RealEstateRoomRepository, PhotoRoomRepository, and GeocodingRepository to perform database operations.
 */
public class AddFragmentViewModel extends ViewModel {

    // Repositories for handling database operations
    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final GeocodingRepository mGeocodingRepository;

    /**
     * Constructor for AddFragmentViewModel.
     *
     * @param realEstateRoomRepository Repository for RealEstate entities.
     * @param photoRoomRepository      Repository for Photo entities.
     * @param geocodingRepository      Repository for geocoding operations.
     */
    public AddFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository, GeocodingRepository geocodingRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
        mGeocodingRepository = geocodingRepository;
    }

    /**
     * Inserts a RealEstate entity into the database.
     *
     * @param realEstate The RealEstate object to be inserted.
     */
    public void insertRealEstate(RealEstate realEstate) {
        mRealEstateRoomRepository.insertRealEstate(realEstate);
    }

    /**
     * Inserts a RealEstate entity into the database and returns its ID.
     *
     * @param realEstate The RealEstate object to be inserted.
     * @return The ID of the inserted RealEstate entity.
     */
    public long insertRealEstateAndGetId(RealEstate realEstate) {
        return mRealEstateRoomRepository.insertRealEstateAndGetId(realEstate);
    }

    /**
     * Inserts a Photo entity into the database.
     *
     * @param photo The Photo object to be inserted.
     */
    public void insertPhoto(Photo photo) {
        mPhotoRoomRepository.insertPhoto(photo);
    }

    /**
     * Performs geocoding based on the provided address and calls the callback with the resulting latitude and longitude.
     *
     * @param address  The address for geocoding.
     * @param callback Callback to handle geocoding results.
     */
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

    /**
     * Callback interface to handle geocoding results.
     */
    public interface GeocodingCallback {
        void onGeocodingComplete(Double latitude, Double longitude);
    }
}
