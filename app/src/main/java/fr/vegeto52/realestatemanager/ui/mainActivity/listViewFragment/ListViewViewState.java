package fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment;

import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ListViewViewState {

    private final List<RealEstate> mRealEstateList;

    public ListViewViewState(List<RealEstate> realEstateList){
        mRealEstateList = realEstateList;
    }

    public List<RealEstate> getRealEstateList() {
        return mRealEstateList;
    }
}
