package fr.vegeto52.realestatemanager.ui.mainActivity.editFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 28/11/2023.
 */
public class EditFragmentViewModel extends ViewModel {

    private RealEstateRoomRepository mRealEstateRoomRepository;


    public EditFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
    }

    public LiveData<RealEstate> getRealEstateLiveData(long realEstateId){
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
