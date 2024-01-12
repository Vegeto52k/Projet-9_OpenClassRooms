package fr.vegeto52.realestatemanager.repository;

import static junit.framework.TestCase.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.LiveDataTestUtils;
import fr.vegeto52.realestatemanager.database.repository.PhotoRoomRepository;
import fr.vegeto52.realestatemanager.database.room.dao.PhotoDao;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * Created by Vegeto52-PC on 10/01/2024.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoRoomRepositoryTest {

    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private PhotoDao mPhotoDaoMock;

    @Mock
    private Observer<List<Photo>> mObserverMock;

    @Captor
    private ArgumentCaptor<Photo> photoCaptor;

    private final MutableLiveData<List<Photo>> mListPhotoMutableLiveData = new MutableLiveData<>();

    PhotoRoomRepository SUT;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetListPhoto(){
        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo());
        photoList.add(new Photo());

        doReturn(mListPhotoMutableLiveData)
                .when(mPhotoDaoMock)
                .getListPhoto();

        mListPhotoMutableLiveData.setValue(photoList);

        SUT = new PhotoRoomRepository(mPhotoDaoMock);

        assertEquals(LiveDataTestUtils.getValueForTesting(mListPhotoMutableLiveData), LiveDataTestUtils.getValueForTesting(SUT.getListPhoto()));
    }

    @Test
    public void testInsertPhoto() throws InterruptedException {
        Photo photo = new Photo();

        SUT = new PhotoRoomRepository(mPhotoDaoMock);
        SUT.insertPhoto(photo);

    //    verify(mPhotoDao).insert(photo);
        Thread.sleep(1000);

        verify(mPhotoDaoMock).insert(photoCaptor.capture());
        assertEquals(photo, photoCaptor.getValue());
    }

    @Test
    public void testGetListPhotoToRealEstate(){
        long realEstateId = 123;
        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo());
        photoList.add(new Photo());

        MutableLiveData<List<Photo>> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(photoList);

        SUT = new PhotoRoomRepository(mPhotoDaoMock);

        when(mPhotoDaoMock.getListPhotoToRealEstate(realEstateId)).thenReturn(fakeLiveData);

        LiveData<List<Photo>> observedLiveData = SUT.getListPhotoToRealEstate(realEstateId);
        observedLiveData.observeForever(mObserverMock);

        verify(mPhotoDaoMock).getListPhotoToRealEstate(realEstateId);
        verify(mObserverMock).onChanged(photoList);

        observedLiveData.removeObserver(mObserverMock);
    }

    @Test
    public void testDeleteAllPhotos() throws InterruptedException {
        PhotoRoomRepository photoRoomRepository = new PhotoRoomRepository(mPhotoDaoMock);
        long realEstateId = 123;

        doNothing().when(mPhotoDaoMock).deleteAllPhotos(realEstateId);

        photoRoomRepository.deleteAllPhotos(realEstateId);

        Thread.sleep(1000);

        verify(mPhotoDaoMock).deleteAllPhotos(realEstateId);
    }
}
