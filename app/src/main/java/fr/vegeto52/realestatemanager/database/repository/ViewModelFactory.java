package fr.vegeto52.realestatemanager.database.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.ui.mainActivity.addFragment.AddFragmentViewModel;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragmentViewModel;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragmentViewModel;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewViewModel;
import fr.vegeto52.realestatemanager.ui.mainActivity.locationFragment.LocationViewModel;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final LocationRepository mLocationRepository;
    private final GeocodingRepository mGeocodingRepository;

    private static volatile ViewModelFactory sFactory;

    public static ViewModelFactory getInstance(Context context) {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(context);
                }
            }
        }
        return sFactory;
    }

    private ViewModelFactory(Context context) {
        RealEstateDatabase realEstateDatabase = RealEstateDatabase.getInstance(context);
        this.mRealEstateRoomRepository = new RealEstateRoomRepository(realEstateDatabase.mRealEstateDao());
        this.mPhotoRoomRepository = new PhotoRoomRepository(realEstateDatabase.mPhotoDao());
        this.mLocationRepository = new LocationRepository();
        this.mGeocodingRepository = new GeocodingRepository();
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewViewModel.class)) {
            return (T) new ListViewViewModel(mRealEstateRoomRepository, mPhotoRoomRepository);
        }
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel.class)) {
            return (T) new DetailsFragmentViewModel(mRealEstateRoomRepository, mPhotoRoomRepository);
        }
        if (modelClass.isAssignableFrom(EditFragmentViewModel.class)) {
            return (T) new EditFragmentViewModel(mRealEstateRoomRepository, mPhotoRoomRepository, mGeocodingRepository);
        }
        if (modelClass.isAssignableFrom(AddFragmentViewModel.class)) {
            return (T) new AddFragmentViewModel(mRealEstateRoomRepository, mPhotoRoomRepository, mGeocodingRepository);
        }
        if (modelClass.isAssignableFrom(LocationViewModel.class)) {
            return (T) new LocationViewModel(mLocationRepository, mRealEstateRoomRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
