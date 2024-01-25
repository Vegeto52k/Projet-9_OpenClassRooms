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
 * The ViewModelFactory class provides instances of ViewModels to be used in the application.
 * It implements the ViewModelProvider.Factory interface for custom ViewModel creation.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // Repositories for providing data to ViewModels
    private final RealEstateRoomRepository mRealEstateRoomRepository;
    private final PhotoRoomRepository mPhotoRoomRepository;
    private final LocationRepository mLocationRepository;
    private final GeocodingRepository mGeocodingRepository;
    private final MapsStaticRepository mMapsStaticRepository;

    private static volatile ViewModelFactory sFactory;

    /**
     * Get the singleton instance of ViewModelFactory.
     *
     * @param context The application context.
     * @return The ViewModelFactory instance.
     */
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

    /**
     * Constructor for the ViewModelFactory class.
     *
     * @param context The application context.
     */
    private ViewModelFactory(Context context) {
        RealEstateDatabase realEstateDatabase = RealEstateDatabase.getInstance(context);
        this.mRealEstateRoomRepository = new RealEstateRoomRepository(realEstateDatabase.mRealEstateDao());
        this.mPhotoRoomRepository = new PhotoRoomRepository(realEstateDatabase.mPhotoDao());
        this.mLocationRepository = new LocationRepository();
        this.mGeocodingRepository = new GeocodingRepository();
        this.mMapsStaticRepository = new MapsStaticRepository();
    }

    /**
     * Create a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @param <T>        The type of the ViewModel.
     * @return The created ViewModel instance.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewViewModel.class)) {
            return (T) new ListViewViewModel(mRealEstateRoomRepository, mPhotoRoomRepository);
        }
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel.class)) {
            return (T) new DetailsFragmentViewModel(mRealEstateRoomRepository, mPhotoRoomRepository, mMapsStaticRepository);
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
