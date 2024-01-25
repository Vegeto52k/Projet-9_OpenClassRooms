package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.MapsStaticRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * The DetailsFragmentViewModel class extends ViewModel and provides data to the DetailsFragment.
 * It interacts with the repositories to retrieve information about a specific RealEstate and its photos.
 */
public class DetailsFragmentViewModel extends ViewModel {

    // Repositories for RealEstate, Photo, and Maps Static API
    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final MapsStaticRepository mMapsStaticRepository;

    /**
     * Constructor for the DetailsFragmentViewModel class.
     *
     * @param realEstateRoomRepository Repository for RealEstate data.
     * @param photoRoomRepository      Repository for Photo data.
     * @param mapsStaticRepository     Repository for Maps Static API data.
     */
    public DetailsFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository, MapsStaticRepository mapsStaticRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
        mMapsStaticRepository = mapsStaticRepository;
    }

    /**
     * Retrieves LiveData containing information about a specific RealEstate.
     *
     * @param realEstateId The ID of the RealEstate.
     * @return LiveData<RealEstate> containing information about the specified RealEstate.
     */
    public LiveData<RealEstate> getRealEstateLiveData(long realEstateId) {
        return mRealEstateRoomRepository.getRealEstate(realEstateId);
    }

    /**
     * Retrieves LiveData containing a list of photos associated with a specific RealEstate.
     *
     * @param realEstateId The ID of the RealEstate.
     * @return LiveData<List<Photo>> containing a list of photos associated with the specified RealEstate.
     */
    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId) {
        return mPhotoRoomRepository.getListPhotoToRealEstate(realEstateId);
    }

    /**
     * Retrieves LiveData containing the response from the Maps Static API for a given address.
     *
     * @param address The address for which to retrieve the Maps Static API response.
     * @return LiveData<Response<ResponseBody>> containing the response from the Maps Static API.
     */
    public LiveData<Response<ResponseBody>> getMapsStatic(String address){
        return mMapsStaticRepository.getLiveDataMapsStatic(address);
    }
}
