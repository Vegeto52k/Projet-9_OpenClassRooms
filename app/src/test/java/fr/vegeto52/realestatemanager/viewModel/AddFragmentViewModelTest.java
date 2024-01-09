package fr.vegeto52.realestatemanager.viewModel;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import fr.vegeto52.realestatemanager.database.repository.GeocodingRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import fr.vegeto52.realestatemanager.ui.mainActivity.addFragment.AddFragmentViewModel;

/**
 * Created by Vegeto52-PC on 08/01/2024.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddFragmentViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RealEstateRoomRepository mockRealEstateRepository;

    @Mock
    private PhotoRoomRepository mockPhotoRepository;

    @Mock
    private GeocodingRepository mockGeocodingRepository;

    @Mock
    private AddFragmentViewModel.GeocodingCallback mockGeocodingCallback;

    @Mock
    private Observer<ResultsGeocodingApi> mockGeocodingObserver;

    private AddFragmentViewModel addFragmentViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        addFragmentViewModel = new AddFragmentViewModel(mockRealEstateRepository, mockPhotoRepository, mockGeocodingRepository);
        addFragmentViewModel.getGeocodingLiveData().observeForever(mockGeocodingObserver);
    }

    @Test
    public void insertRealEstate_callsRepositoryInsert() {
        RealEstate mockRealEstate = mock(RealEstate.class);
        addFragmentViewModel.insertRealEstate(mockRealEstate);
        verify(mockRealEstateRepository).insertRealEstate(mockRealEstate);
    }

    @Test
    public void insertRealEstateAndGetId_callsRepositoryInsertAndGetId() {
        RealEstate mockRealEstate = mock(RealEstate.class);
        addFragmentViewModel.insertRealEstateAndGetId(mockRealEstate);
        verify(mockRealEstateRepository).insertRealEstateAndGetId(mockRealEstate);
    }

    @Test
    public void insertPhoto_callsRepositoryInsert() {
        Photo mockPhoto = mock(Photo.class);
        addFragmentViewModel.insertPhoto(mockPhoto);
        verify(mockPhotoRepository).insertPhoto(mockPhoto);
    }

    @Test
    public void getGeocoding_callsRepositoryGetGeocoding() {
        String mockAddress = "Mock Address";
        addFragmentViewModel.getGeocoding(mockAddress, mockGeocodingCallback);
        verify(mockGeocodingRepository).getGeocoding(eq(mockAddress), any());
    }
}
