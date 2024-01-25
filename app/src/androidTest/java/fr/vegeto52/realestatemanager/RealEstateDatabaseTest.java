package fr.vegeto52.realestatemanager;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.database.room.dao.PhotoDao;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 25/01/2024.
 */
@RunWith(AndroidJUnit4.class)
public class RealEstateDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PhotoDao mPhotoDao;
    private RealEstateDao mRealEstateDao;
    private RealEstateDatabase mRealEstateDatabase;

    @Before
    public void setUp(){
        Context context = ApplicationProvider.getApplicationContext();

        mRealEstateDatabase = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();

        mPhotoDao = mRealEstateDatabase.mPhotoDao();
        mRealEstateDao = mRealEstateDatabase.mRealEstateDao();
    }

    @After
    public void tearDown(){
        mRealEstateDatabase.close();
    }

    @Test
    public void insertRealEstate(){
        RealEstate realEstate = new RealEstate();

        mRealEstateDao.insert(realEstate);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mRealEstateDao.getListRealEstate()).size()).isEqualTo(1);
    }

    @Test
    public void insertPhoto(){
        RealEstate realEstate = new RealEstate();
        Photo photo = new Photo(2, null, 1);

        mRealEstateDao.insert(realEstate);
        mPhotoDao.insert(photo);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mPhotoDao.getListPhoto()).size()).isEqualTo(1);
    }

    @Test
    public void updatePhoto(){
        RealEstate realEstate = new RealEstate();
        Photo photo = new Photo(2, null, 1);

        mRealEstateDao.insert(realEstate);
        mPhotoDao.insert(photo);

        photo.setDescription("Castle");
        mPhotoDao.update(photo);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mPhotoDao.getListPhoto()).get(0).getDescription()).isEqualTo("Castle");
    }

    @Test
    public void getPhotosByRealEstateId(){
        RealEstate realEstate = new RealEstate();
        Photo photo = new Photo(1, null, 1);
        Photo photo2 = new Photo(2, null, 1);

        mRealEstateDao.insert(realEstate);
        mPhotoDao.insert(photo);
        mPhotoDao.insert(photo2);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mPhotoDao.getListPhotoToRealEstate(1)).size()).isEqualTo(2);
    }

    @Test
    public void deletePhotosByRealEstateId(){
        RealEstate realEstate = new RealEstate();
        Photo photo = new Photo(1, null, 1);
        Photo photo2 = new Photo(2, null, 1);

        mRealEstateDao.insert(realEstate);
        mPhotoDao.insert(photo);
        mPhotoDao.insert(photo2);

        mPhotoDao.deleteAllPhotos(1);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mPhotoDao.getListPhoto()).size()).isEqualTo(0);
    }

    @Test
    public void insertRealEstateAndGetId(){
        RealEstate realEstate = new RealEstate();

        long id = mRealEstateDao.insertAndGetId(realEstate);

        Truth.assertThat(id).isEqualTo(1);
    }

    @Test
    public void insertRealEstateList(){
        RealEstate realEstate = new RealEstate();
        RealEstate realEstate2 = new RealEstate();
        RealEstate realEstate3 = new RealEstate();
        List<RealEstate> list = Arrays.asList(realEstate, realEstate2, realEstate3);

        mRealEstateDao.insertList(list);

        Truth.assertThat(LiveDataTestUtil.getValueForTesting(mRealEstateDao.getListRealEstate()).size()).isEqualTo(3);
    }
}
