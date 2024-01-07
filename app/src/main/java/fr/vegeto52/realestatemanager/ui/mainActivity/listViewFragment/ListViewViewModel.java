package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.LocationRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewViewModel extends ViewModel {

    private RealEstateRoomRepository mRealEstateRoomRepository;
    private PhotoRoomRepository mPhotoRoomRepository;

    private final MediatorLiveData<ListViewViewState> mMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<List<RealEstate>> mRealEstateListFilteredLiveDate = new MutableLiveData<>();
    public ListViewViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository){
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;

        LiveData<List<RealEstate>> realEstate = realEstateRoomRepository.getListRealEstate();
        LiveData<List<Photo>> photo = photoRoomRepository.getListPhoto();

        mMediatorLiveData.addSource(realEstate, realEstateList -> combine(realEstateList, photo.getValue()));
        mMediatorLiveData.addSource(photo, photoList -> combine(realEstate.getValue(), photoList));
    }

    private void combine(List<RealEstate> realEstateList, List<Photo> photoList){
        if (realEstateList != null && photoList != null){
            mMediatorLiveData.setValue(new ListViewViewState(realEstateList, photoList));
        }
    }

    public LiveData<ListViewViewState> getListViewMutableLiveData(){
        return mMediatorLiveData;
    }

    public LiveData<RealEstate> getRealEstate(long realEstateId){
        return mRealEstateRoomRepository.getRealEstate(realEstateId);
    }

    public void insertRealEstate(RealEstate realEstate){
        mRealEstateRoomRepository.insertRealEstate(realEstate);
    }

    public void updateRealEstate(RealEstate realEstate){
        mRealEstateRoomRepository.updateRealEstate(realEstate);
    }

    public void deleteRealEstate(RealEstate realEstate){
        mRealEstateRoomRepository.deleteRealEstate(realEstate);
    }
}
