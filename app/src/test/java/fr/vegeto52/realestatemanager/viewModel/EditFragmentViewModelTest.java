package fr.vegeto52.realestatemanager.viewModel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.vegeto52.realestatemanager.database.repository.GeocodingRepository;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import fr.vegeto52.realestatemanager.ui.mainActivity.editFragment.EditFragmentViewModel;

/**
 * Created by Vegeto52-PC on 12/01/2024.
 */
public class EditFragmentViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RealEstateRoomRepository mockRealEstateRoomRepository;

    @Mock
    private PhotoRoomRepository mockPhotoRoomRepository;

    @Mock
    private GeocodingRepository mockGeocodingRepository;

    @Mock
    private EditFragmentViewModel.GeocodingCallback mockGeocodingCallback;

    @Mock
    private Observer<RealEstate> mockRealEstateObserver;

    @Mock
    private Observer<List<Photo>> mockPhotoListObserver;

    private EditFragmentViewModel editFragmentViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        editFragmentViewModel = new EditFragmentViewModel(mockRealEstateRoomRepository, mockPhotoRoomRepository, mockGeocodingRepository);
    }

    @Test
    public void testGetRealEstateLiveData() throws InterruptedException {
        long realEstateId = 123;
        RealEstate mockRealEstate = new RealEstate(/* pass required parameters */);
        MutableLiveData<RealEstate> mockLiveData = new MutableLiveData<>();
        mockLiveData.setValue(mockRealEstate);

        when(mockRealEstateRoomRepository.getRealEstate(anyLong())).thenReturn(mockLiveData);

        LiveData<RealEstate> resultLiveData = editFragmentViewModel.getRealEstateLiveData(realEstateId);

        resultLiveData.observeForever(mockRealEstateObserver);

        // Wait for LiveData to be updated
        CountDownLatch latch = new CountDownLatch(1);
        resultLiveData.observeForever(value -> latch.countDown());
        latch.await(2, TimeUnit.SECONDS);

        verify(mockRealEstateRoomRepository).getRealEstate(realEstateId);
        verify(mockRealEstateObserver).onChanged(mockRealEstate);
    }

    @Test
    public void testUpdateRealEstate() {
        RealEstate mockRealEstate = new RealEstate(/* pass required parameters */);
        editFragmentViewModel.updateRealEstate(mockRealEstate);

        verify(mockRealEstateRoomRepository).updateRealEstate(mockRealEstate);
    }

    @Test
    public void testGetListPhotoToRealEstate() throws InterruptedException {
        long realEstateId = 123;
        List<Photo> mockPhotoList = new ArrayList<>(); // Populate with mock data
        MutableLiveData<List<Photo>> mockLiveData = new MutableLiveData<>();
        mockLiveData.setValue(mockPhotoList);

        when(mockPhotoRoomRepository.getListPhotoToRealEstate(anyLong())).thenReturn(mockLiveData);

        LiveData<List<Photo>> resultLiveData = editFragmentViewModel.getListPhotoToRealEstate(realEstateId);

        resultLiveData.observeForever(mockPhotoListObserver);

        // Wait for LiveData to be updated
        CountDownLatch latch = new CountDownLatch(1);
        resultLiveData.observeForever(value -> latch.countDown());
        latch.await(2, TimeUnit.SECONDS);

        verify(mockPhotoRoomRepository).getListPhotoToRealEstate(realEstateId);
        verify(mockPhotoListObserver).onChanged(mockPhotoList);
    }

    @Test
    public void testInsertPhoto() {
        Photo mockPhoto = new Photo(/* pass required parameters */);
        editFragmentViewModel.insertPhoto(mockPhoto);

        verify(mockPhotoRoomRepository).insertPhoto(mockPhoto);
    }

    @Test
    public void testDeleteAllPhotos() {
        long realEstateId = 123;
        editFragmentViewModel.deleteAllPhotos(realEstateId);

        verify(mockPhotoRoomRepository).deleteAllPhotos(realEstateId);
    }
}
