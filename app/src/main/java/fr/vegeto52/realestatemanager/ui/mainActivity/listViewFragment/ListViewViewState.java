package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import java.util.List;

import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The ListViewViewState class represents the combined view state of RealEstate and Photo data.
 * It holds lists of RealEstate and Photo items.
 */
public class ListViewViewState {

    // List of RealEstate items
    private final List<RealEstate> mRealEstateList;

    // List of Photo items
    private final List<Photo> mPhotoList;

    /**
     * Constructor for the ListViewViewState class.
     *
     * @param realEstateList List of RealEstate items.
     * @param photoList      List of Photo items.
     */
    public ListViewViewState(List<RealEstate> realEstateList, List<Photo> photoList) {
        mRealEstateList = realEstateList;
        mPhotoList = photoList;
    }

    /**
     * Gets the list of RealEstate items.
     *
     * @return List of RealEstate items.
     */
    public List<RealEstate> getRealEstateList() {
        return mRealEstateList;
    }

    /**
     * Gets the list of Photo items.
     *
     * @return List of Photo items.
     */
    public List<Photo> getPhotoList() {
        return mPhotoList;
    }
}
