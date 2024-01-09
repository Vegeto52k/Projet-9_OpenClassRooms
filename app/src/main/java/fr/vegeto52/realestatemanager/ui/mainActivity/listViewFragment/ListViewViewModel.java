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
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewViewModel extends ViewModel {

    private final MediatorLiveData<ListViewViewState> mMediatorLiveData = new MediatorLiveData<>();

    public ListViewViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository) {

        LiveData<List<RealEstate>> realEstate = realEstateRoomRepository.getListRealEstate();
        LiveData<List<Photo>> photo = photoRoomRepository.getListPhoto();

        mMediatorLiveData.addSource(realEstate, realEstateList -> combine(realEstateList, photo.getValue()));
        mMediatorLiveData.addSource(photo, photoList -> combine(realEstate.getValue(), photoList));
    }

    private void combine(List<RealEstate> realEstateList, List<Photo> photoList) {
        if (realEstateList != null && photoList != null) {
            mMediatorLiveData.setValue(new ListViewViewState(realEstateList, photoList));
        }
    }

    public LiveData<ListViewViewState> getListViewMutableLiveData() {
        return mMediatorLiveData;
    }
}
