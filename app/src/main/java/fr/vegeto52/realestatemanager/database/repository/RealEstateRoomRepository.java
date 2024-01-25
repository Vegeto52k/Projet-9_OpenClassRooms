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
 * The RealEstateRoomRepository class acts as a repository for managing real estate data using Room database.
 * It uses the RealEstateDao for database operations and provides LiveData for observing real estate data changes.
 */
public class RealEstateRoomRepository {

    private final RealEstateDao mRealEstateDao;
    private final LiveData<List<RealEstate>> mListRealEstate;

    /**
     * Constructor for the RealEstateRoomRepository class.
     *
     * @param realEstateDao The RealEstateDao used for database operations.
     */
    public RealEstateRoomRepository(RealEstateDao realEstateDao) {
        mRealEstateDao = realEstateDao;
        mListRealEstate = mRealEstateDao.getListRealEstate();
    }

    /**
     * Get LiveData for observing the list of real estate.
     *
     * @return The LiveData object containing the list of real estate.
     */
    public LiveData<List<RealEstate>> getListRealEstate() {
        return mListRealEstate;
    }

    /**
     * Get LiveData for observing a specific real estate by its ID.
     *
     * @param realEstateId The ID of the real estate to be observed.
     * @return The LiveData object containing the specific real estate.
     */
    public LiveData<RealEstate> getRealEstate(long realEstateId) {
        return mRealEstateDao.getRealEstate(realEstateId);
    }

    /**
     * Insert a new real estate into the database.
     *
     * @param realEstate The real estate to be inserted.
     */
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

    /**
     * Insert a new real estate into the database and get its ID.
     *
     * @param realEstate The real estate to be inserted.
     * @return The ID of the inserted real estate.
     */
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

    /**
     * Update an existing real estate in the database.
     *
     * @param realEstate The real estate to be updated.
     */
    public void updateRealEstate(RealEstate realEstate) {
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.update(realEstate));
    }
}
