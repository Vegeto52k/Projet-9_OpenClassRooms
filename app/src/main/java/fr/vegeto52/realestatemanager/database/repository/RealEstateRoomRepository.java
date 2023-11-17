package fr.vegeto52.realestatemanager.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

import fr.vegeto52.realestatemanager.database.MainApplication;
import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class RealEstateRoomRepository {

    private final RealEstateDao mRealEstateDao;
    private final LiveData<List<RealEstate>> mListRealEstate;

    public RealEstateRoomRepository(RealEstateDao realEstateDao){
        mRealEstateDao = realEstateDao;
        mListRealEstate = mRealEstateDao.getListRealEstate();
    }

    public LiveData<List<RealEstate>> getListRealEstate(){
        return mListRealEstate;
    }

    public LiveData<RealEstate> getRealEstate(long realEstateId){
        return mRealEstateDao.getRealEstate(realEstateId);
    }

    public void insertRealEstate(RealEstate realEstate){
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.insert(realEstate));
    }

    public void updateRealEstate(RealEstate realEstate){
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.update(realEstate));
    }

    public void deleteRealEstate(RealEstate realEstate){
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.delete(realEstate));
    }
}
