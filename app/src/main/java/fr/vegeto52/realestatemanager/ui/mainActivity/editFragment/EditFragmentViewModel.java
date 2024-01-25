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
 * The EditFragmentViewModel class extends ViewModel and serves as the ViewModel for the EditFragment.
 * It interacts with the RealEstateRoomRepository, PhotoRoomRepository, and GeocodingRepository to manage data
 * for editing a real estate property.
 */
public class EditFragmentViewModel extends ViewModel {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final GeocodingRepository mGeocodingRepository;

    /**
     * Constructor for the EditFragmentViewModel class.
     *
     * @param realEstateRoomRepository The repository for real estate data.
     * @param photoRoomRepository      The repository for photo data.
     * @param geocodingRepository      The repository for geocoding data.
     */
    public EditFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository, GeocodingRepository geocodingRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
        mGeocodingRepository = geocodingRepository;
    }

    /**
     * Retrieves LiveData for a specific real estate property based on its ID.
     *
     * @param realEstateId The ID of the real estate property.
     * @return LiveData containing the real estate property information.
     */
    public LiveData<RealEstate> getRealEstateLiveData(long realEstateId) {
        return mRealEstateRoomRepository.getRealEstate(realEstateId);
    }

    /**
     * Updates the information of a real estate property.
     *
     * @param realEstate The real estate property with updated information.
     */
    public void updateRealEstate(RealEstate realEstate) {
        mRealEstateRoomRepository.updateRealEstate(realEstate);
    }

    /**
     * Retrieves LiveData for the list of photos associated with a specific real estate property.
     *
     * @param realEstateId The ID of the real estate property.
     * @return LiveData containing the list of photos.
     */
    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId) {
        return mPhotoRoomRepository.getListPhotoToRealEstate(realEstateId);
    }

    /**
     * Inserts a new photo into the database.
     *
     * @param photo The photo to be inserted.
     */
    public void insertPhoto(Photo photo) {
        mPhotoRoomRepository.insertPhoto(photo);
    }

    /**
     * Deletes all photos associated with a specific real estate property.
     *
     * @param realEstateId The ID of the real estate property.
     */
    public void deleteAllPhotos(long realEstateId) {
        mPhotoRoomRepository.deleteAllPhotos(realEstateId);
    }

    /**
     * Retrieves geocoding information for a given address and invokes a callback upon completion.
     *
     * @param address  The address for geocoding.
     * @param callback The callback to be invoked upon completion.
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
     * Callback interface for geocoding operations.
     */
    public interface GeocodingCallback {
        /**
         * Invoked when geocoding is complete, providing the latitude and longitude.
         *
         * @param latitude  The latitude of the location.
         * @param longitude The longitude of the location.
         */
        void onGeocodingComplete(Double latitude, Double longitude);
    }
}
