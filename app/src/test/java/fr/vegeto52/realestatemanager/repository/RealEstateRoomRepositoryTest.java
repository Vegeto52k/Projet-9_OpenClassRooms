package fr.vegeto52.realestatemanager.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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

import fr.vegeto52.realestatemanager.database.repository.RealEstateRoomRepository;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 09/01/2024.
 */

@RunWith(MockitoJUnitRunner.class)
public class RealEstateRoomRepositoryTest {

    @Mock
    private RealEstateDao mockRealEstateDao;

    @Mock
    private Observer<List<RealEstate>> mockObserver;

    @Captor
    private ArgumentCaptor<RealEstate> mRealEstateArgumentCaptor;

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetListRealEstate() {
        List<RealEstate> fakeRealEstateList = new ArrayList<>();
        fakeRealEstateList.add(new RealEstate());
        fakeRealEstateList.add(new RealEstate());

        MutableLiveData<List<RealEstate>> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(fakeRealEstateList);

        when(mockRealEstateDao.getListRealEstate()).thenReturn(fakeLiveData);

        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);

        realEstateRepository.getListRealEstate().observeForever(mockObserver);

        verify(mockObserver).onChanged(fakeRealEstateList);
    }

    @Test
    public void testGetRealEstate() {
        long fakeRealEstateId = 123;

        RealEstate fakeRealEstate1 = new RealEstate();

        MutableLiveData<RealEstate> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(fakeRealEstate1);

        when(mockRealEstateDao.getRealEstate(fakeRealEstateId)).thenReturn(fakeLiveData);

        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);

        Observer<RealEstate> mockObserver = mock(Observer.class);
        realEstateRepository.getRealEstate(fakeRealEstateId).observeForever(mockObserver);

        verify(mockObserver).onChanged(fakeRealEstate1);
    }

    @Test
    public void testInsertRealEstate() throws Exception {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        RealEstate fakeRealEstate2 = new RealEstate();

        realEstateRepository.insertRealEstate(fakeRealEstate2);

        Thread.sleep(1000);

        verify(mockRealEstateDao).insert(fakeRealEstate2);
    }

    @Test
    public void testInsertRealEstateAndGetId() {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        RealEstate fakeRealEstate = new RealEstate();

        long fakeId = 456;
        when(mockRealEstateDao.insertAndGetId(fakeRealEstate)).thenReturn(fakeId);

        long insertedId = realEstateRepository.insertRealEstateAndGetId(fakeRealEstate);
        assertEquals(fakeId, insertedId);

        verify(mockRealEstateDao).insertAndGetId(fakeRealEstate);
    }

    @Test
    public void testUpdateRealEstate() throws InterruptedException {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        RealEstate fakeRealEstate3 = new RealEstate();

        realEstateRepository.updateRealEstate(fakeRealEstate3);

        mRealEstateArgumentCaptor = ArgumentCaptor.forClass(RealEstate.class);

        Thread.sleep(1000);

        verify(mockRealEstateDao).update(mRealEstateArgumentCaptor.capture());
        assertEquals(fakeRealEstate3, mRealEstateArgumentCaptor.getValue());
    }
}
