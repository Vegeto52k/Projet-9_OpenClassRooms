package fr.vegeto52.realestatemanager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewViewModel extends ViewModel {

    private RealEstateRoomRepository mRealEstateRoomRepository;
    private final MediatorLiveData<ListViewViewState> mMediatorLiveData = new MediatorLiveData<>();

    public ListViewViewModel(RealEstateRoomRepository realEstateRoomRepository){
        LiveData<List<RealEstate>> realEstate = realEstateRoomRepository.getListRealEstate();

        mMediatorLiveData.addSource(realEstate, realEstateList -> combine(realEstateList));
    }

    private void combine(List<RealEstate> realEstateList){
        if (realEstateList != null){
            mMediatorLiveData.setValue(new ListViewViewState(realEstateList));
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
