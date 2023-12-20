package fr.vegeto52.realestatemanager.database.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 08/11/2023.
 */
@Dao
public interface RealEstateDao {

    @Insert
    void insert(RealEstate realEstate);

    @Insert
    long insertAndGetId(RealEstate realEstate);

    @Insert
    void insertList(List<RealEstate> realEstateList);

    @Update
    void update(RealEstate realEstate);

    @Delete
    void delete(RealEstate realEstate);

    @Query("SELECT * FROM real_estate")
    LiveData<List<RealEstate>> getListRealEstate();

    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    LiveData<RealEstate> getRealEstate(long realEstateId);
}
