package fr.vegeto52.realestatemanager.database.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.ui.mainActivity.detailsFragment.DetailsFragmentViewModel;
import fr.vegeto52.realestatemanager.ui.mainActivity.listViewFragment.ListViewViewModel;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RealEstateRoomRepository mRealEstateRoomRepository;

    private static volatile ViewModelFactory sFactory;

    public static ViewModelFactory getInstance(Context context){
        if (sFactory == null){
            synchronized (ViewModelFactory.class){
                if (sFactory == null){
                    sFactory = new ViewModelFactory(context);
                }
            }
        }
        return sFactory;
    }

    private ViewModelFactory(Context context){
        RealEstateDatabase realEstateDatabase = RealEstateDatabase.getInstance(context);
        this.mRealEstateRoomRepository = new RealEstateRoomRepository(realEstateDatabase.mRealEstateDao());
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewViewModel.class)){
            return (T) new ListViewViewModel(mRealEstateRoomRepository);
        }
        if (modelClass.isAssignableFrom(DetailsFragmentViewModel.class)){
            return (T) new DetailsFragmentViewModel(mRealEstateRoomRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
