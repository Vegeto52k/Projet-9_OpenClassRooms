package fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 21/11/2023.
 */
public class DetailsFragmentViewModel extends ViewModel {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;

    public DetailsFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
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

    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId){
        return mPhotoRoomRepository.getListPhotoToRealEstate(realEstateId);
    }
}
