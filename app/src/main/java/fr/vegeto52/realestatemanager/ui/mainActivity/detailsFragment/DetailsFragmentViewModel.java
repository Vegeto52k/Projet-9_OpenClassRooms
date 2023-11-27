package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewViewState;

/**
 * Created by Vegeto52-PC on 21/11/2023.
 */
public class DetailsFragmentViewModel extends ViewModel {

    private RealEstateRoomRepository mRealEstateRoomRepository;

    public DetailsFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
    }

    public LiveData<RealEstate> getRealEstateLiveData(long realRestateId){
        return mRealEstateRoomRepository.getRealEstate(realRestateId);
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
