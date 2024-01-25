package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The ListViewViewModel class extends ViewModel and serves as the ViewModel for the ListViewFragment.
 * It observes changes in the list of RealEstate and Photo items and provides a combined ViewState.
 */
public class ListViewViewModel extends ViewModel {

    // MediatorLiveData for combining RealEstate and Photo LiveData
    private final MediatorLiveData<ListViewViewState> mMediatorLiveData = new MediatorLiveData<>();

    /**
     * Constructor for the ListViewViewModel class.
     *
     * @param realEstateRoomRepository The repository for RealEstate data.
     * @param photoRoomRepository      The repository for Photo data.
     */
    public ListViewViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository) {

        // LiveData for RealEstate and Photo items
        LiveData<List<RealEstate>> realEstate = realEstateRoomRepository.getListRealEstate();
        LiveData<List<Photo>> photo = photoRoomRepository.getListPhoto();

        // Add sources to the MediatorLiveData and combine the data
        mMediatorLiveData.addSource(realEstate, realEstateList -> combine(realEstateList, photo.getValue()));
        mMediatorLiveData.addSource(photo, photoList -> combine(realEstate.getValue(), photoList));
    }

    /**
     * Combines RealEstate and Photo data into a ViewState object.
     *
     * @param realEstateList List of RealEstate items.
     * @param photoList      List of Photo items.
     */
    private void combine(List<RealEstate> realEstateList, List<Photo> photoList) {
        if (realEstateList != null && photoList != null) {
            // Set the combined ViewState in the MediatorLiveData
            mMediatorLiveData.setValue(new ListViewViewState(realEstateList, photoList));
        }
    }

    /**
     * Returns the MediatorLiveData containing the combined ViewState.
     *
     * @return MediatorLiveData with ListViewViewState.
     */
    public LiveData<ListViewViewState> getListViewMutableLiveData() {
        return mMediatorLiveData;
    }
}
