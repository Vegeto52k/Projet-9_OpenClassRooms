package fr.vegeto52.realestatemanager.database.room.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The RealEstateDao interface defines Data Access Object (DAO) methods for real estate-related database operations.
 */
@Dao
public interface RealEstateDao {

    /**
     * Insert a new real estate into the database.
     *
     * @param realEstate The real estate to be inserted.
     */
    @Insert
    void insert(RealEstate realEstate);

    /**
     * Insert a new real estate into the database and get its ID.
     *
     * @param realEstate The real estate to be inserted.
     * @return The ID of the inserted real estate.
     */
    @Insert
    long insertAndGetId(RealEstate realEstate);

    /**
     * Insert a list of real estate into the database.
     *
     * @param realEstateList The list of real estate to be inserted.
     */
    @Insert
    void insertList(List<RealEstate> realEstateList);

    /**
     * Update an existing real estate in the database.
     *
     * @param realEstate The real estate to be updated.
     */
    @Update
    void update(RealEstate realEstate);

    /**
     * Retrieve a LiveData list of all real estate from the database.
     *
     * @return LiveData containing a list of all real estate.
     */
    @Query("SELECT * FROM real_estate")
    LiveData<List<RealEstate>> getListRealEstate();

    /**
     * Retrieve a LiveData instance of a single real estate based on its ID.
     *
     * @param realEstateId The ID of the real estate.
     * @return LiveData containing a single real estate.
     */
    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    LiveData<RealEstate> getRealEstate(long realEstateId);

    /**
     * Provider: Retrieve a Cursor containing all real estate.
     *
     * @return Cursor containing all real estate.
     */
    @Query("SELECT * FROM real_estate")
    Cursor getRealEstateWithCursor();
}
