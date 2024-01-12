package fr.vegeto52.realestatemanager.repository;

import static com.google.android.gms.tasks.Tasks.await;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import fr.vegeto52.realestatemanager.LiveDataTestUtils;
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
    @Captor
    private ArgumentCaptor<RealEstate> mRealEstateArgumentCaptor2;

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

//    private RealEstateRoomRepository realEstateRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialiser la classe à tester en injectant le DAO simulé
    //    realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
    //    reset(mockRealEstateDao);
    //    reset(realEstateRepository);
    }

    @Test
    public void testGetListRealEstate() {
        // Créer une liste de RealEstate simulée
        List<RealEstate> fakeRealEstateList = new ArrayList<>();
        fakeRealEstateList.add(new RealEstate());
        fakeRealEstateList.add(new RealEstate());

        MutableLiveData<List<RealEstate>> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(fakeRealEstateList);

        // Simuler le comportement du DAO pour retourner la liste simulée
        when(mockRealEstateDao.getListRealEstate()).thenReturn(fakeLiveData);

        // Créer une instance de RealEstateRoomRepository avec le DAO simulé
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);

        // Observer pour surveiller les changements
        realEstateRepository.getListRealEstate().observeForever(mockObserver);

        // Vérifier que l'Observer a été notifié avec la liste simulée
        verify(mockObserver).onChanged(fakeRealEstateList);
    }

    //En conflit avec le 5ème test
    @Test
    public void testGetRealEstate() {
        // Identifier un identifiant simulé
        long fakeRealEstateId = 123;

        // Créer un RealEstate simulé
        RealEstate fakeRealEstate1 = new RealEstate();

        MutableLiveData<RealEstate> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(fakeRealEstate1);

        // Simuler le comportement du DAO pour retourner le RealEstate simulé
        when(mockRealEstateDao.getRealEstate(fakeRealEstateId)).thenReturn(fakeLiveData);

        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);

        // Observer pour surveiller les changements
        Observer<RealEstate> mockObserver = mock(Observer.class);
        realEstateRepository.getRealEstate(fakeRealEstateId).observeForever(mockObserver);

        // Vérifier que l'Observer a été notifié avec le RealEstate simulé
        verify(mockObserver).onChanged(fakeRealEstate1);
    }

    @Test
    public void testInsertRealEstate() throws Exception {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        // Créer un RealEstate simulé
        RealEstate fakeRealEstate2 = new RealEstate();

        // Utiliser une Future pour attendre la fin de l'insertion
    //    Future<?> future = Executors.newSingleThreadExecutor().submit(() -> {
            realEstateRepository.insertRealEstate(fakeRealEstate2);

            Thread.sleep(1000);
    //    });

        // Attendre la fin de l'insertion
    //    future.get();

        // Vérifier que la méthode insert du DAO a été appelée avec le RealEstate simulé
    //    verify(mockRealEstateDao).insert(fakeRealEstate2);

        mRealEstateArgumentCaptor2 = ArgumentCaptor.forClass(RealEstate.class);

        verify(mockRealEstateDao).insert(fakeRealEstate2);
    //    assertEquals(fakeRealEstate2, mRealEstateArgumentCaptor2.getValue());
    }

    @Test
    public void testInsertRealEstateAndGetId() throws Exception {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        // Créer un RealEstate simulé
        RealEstate fakeRealEstate = new RealEstate();

        // Simuler l'ID retourné par le DAO
        long fakeId = 456;
        when(mockRealEstateDao.insertAndGetId(fakeRealEstate)).thenReturn(fakeId);

        // Utiliser une Future pour obtenir l'ID de l'insertion
        Future<Long> future = (Future<Long>) Executors.newSingleThreadExecutor().submit(() -> {
            long insertedId = realEstateRepository.insertRealEstateAndGetId(fakeRealEstate);
            // Vérifier que l'ID retourné correspond à l'ID simulé
            assertEquals(fakeId, insertedId);
        });

        // Attendre la fin de l'insertion
        future.get();

        // Vérifier que la méthode insertAndGetId du DAO a été appelée avec le RealEstate simulé
        verify(mockRealEstateDao).insertAndGetId(fakeRealEstate);
    }

    //En conflit avec le second test
    @Test
    public void testUpdateRealEstate() throws InterruptedException {
        RealEstateRoomRepository realEstateRepository = new RealEstateRoomRepository(mockRealEstateDao);
        // Créer un RealEstate simulé
        RealEstate fakeRealEstate3 = new RealEstate();

        // Mettre à jour le RealEstate simulé
        realEstateRepository.updateRealEstate(fakeRealEstate3);

        mRealEstateArgumentCaptor = ArgumentCaptor.forClass(RealEstate.class);

        // Vérifier que la méthode update du DAO a été appelée avec le RealEstate simulé
    //    verify(mockRealEstateDao).update(fakeRealEstate3);
        Thread.sleep(1000);

        verify(mockRealEstateDao).update(mRealEstateArgumentCaptor.capture());
        assertEquals(fakeRealEstate3, mRealEstateArgumentCaptor.getValue());
    }
}
