package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import java.util.List;

import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewViewState {

    private final List<RealEstate> mRealEstateList;
    private final List<Photo> mPhotoList;

    public ListViewViewState(List<RealEstate> realEstateList, List<Photo> photoList) {
        mRealEstateList = realEstateList;
        mPhotoList = photoList;
    }

    public List<RealEstate> getRealEstateList() {
        return mRealEstateList;
    }

    public List<Photo> getPhotoList() {
        return mPhotoList;
    }
}
