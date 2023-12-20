package fr.vegeto52.realestatemanager.ui.mainActivity.addFragment;

import androidx.lifecycle.ViewModel;

import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 11/12/2023.
 */
public class AddFragmentViewModel extends ViewModel {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;


    public AddFragmentViewModel(RealEstateRoomRepository realEstateRoomRepository, PhotoRoomRepository photoRoomRepository) {
        mRealEstateRoomRepository = realEstateRoomRepository;
        mPhotoRoomRepository = photoRoomRepository;
    }

    public void insertRealEstate(RealEstate realEstate){
        mRealEstateRoomRepository.insertRealEstate(realEstate);
    }

    public long insertRealEstateAndGetId(RealEstate realEstate){
        return mRealEstateRoomRepository.insertRealEstateAndGetId(realEstate);
    }

    public void insertPhoto(Photo photo){
        mPhotoRoomRepository.insertPhoto(photo);
    }

}
