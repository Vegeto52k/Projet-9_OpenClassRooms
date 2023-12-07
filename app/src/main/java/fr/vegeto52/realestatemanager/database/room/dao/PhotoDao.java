package fr.vegeto52.realestatemanager.database.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.vegeto52.realestatemanager.model.Photo;

/**
 * Created by Vegeto52-PC on 04/12/2023.
 */
@Dao
public interface PhotoDao {

    @Insert
    void insert(Photo photo);

    @Insert
    void insertList(List<Photo> photoList);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("SELECT * FROM photo")
    LiveData<List<Photo>> getListPhoto();

    @Query("SELECT * FROM photo WHERE id = :photoId")
    LiveData<Photo> getPhoto(long photoId);

    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId")
    LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId);

    @Query("DELETE FROM photo WHERE realEstateId = :realEstateId")
    void deleteAllPhotos(long realEstateId);
}
