package fr.vegeto52.realestatemanager.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class RealEstateRoomRepository {

    private final RealEstateDao mRealEstateDao;
    private final LiveData<List<RealEstate>> mListRealEstate;

    public RealEstateRoomRepository(RealEstateDao realEstateDao) {
        mRealEstateDao = realEstateDao;
        mListRealEstate = mRealEstateDao.getListRealEstate();
    }

    public LiveData<List<RealEstate>> getListRealEstate() {
        return mListRealEstate;
    }

    public LiveData<RealEstate> getRealEstate(long realEstateId) {
        return mRealEstateDao.getRealEstate(realEstateId);
    }

    public void insertRealEstate(RealEstate realEstate) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.insert(realEstate)));
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ignored) {
        } finally {
            executor.shutdown();
        }
    }

    public long insertRealEstateAndGetId(RealEstate realEstate) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Long> future = executor.submit(() -> mRealEstateDao.insertAndGetId(realEstate));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        } finally {
            executor.shutdown();
        }
    }

    public void updateRealEstate(RealEstate realEstate) {
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.update(realEstate));
    }
}
