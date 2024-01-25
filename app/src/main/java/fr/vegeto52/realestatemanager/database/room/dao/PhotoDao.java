package fr.vegeto52.realestatemanager.database.room.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.vegeto52.realestatemanager.model.Photo;

/**
 * The PhotoDao interface defines Data Access Object (DAO) methods for photo-related database operations.
 */
@Dao
public interface PhotoDao {

    /**
     * Insert a new photo into the database.
     *
     * @param photo The photo to be inserted.
     */
    @Insert
    void insert(Photo photo);

    /**
     * Insert a new photo into the database and get its ID.
     *
     * @param photo The photo to be inserted.
     * @return The ID of the inserted photo.
     */
    @Insert
    long insertAndGetId(Photo photo);

    /**
     * Update an existing photo in the database.
     *
     * @param photo The photo to be updated.
     */
    @Update
    void update(Photo photo);

    /**
     * Retrieve a LiveData list of all photos from the database.
     *
     * @return LiveData containing a list of all photos.
     */
    @Query("SELECT * FROM photo")
    LiveData<List<Photo>> getListPhoto();

    /**
     * Retrieve a LiveData list of photos associated with a specific real estate ID.
     *
     * @param realEstateId The ID of the real estate.
     * @return LiveData containing a list of photos associated with the given real estate ID.
     */
    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId")
    LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId);

    /**
     * Delete all photos associated with a specific real estate ID.
     *
     * @param realEstateId The ID of the real estate.
     */
    @Query("DELETE FROM photo WHERE realEstateId = :realEstateId")
    void deleteAllPhotos(long realEstateId);

    /**
     * Provider: Retrieve a Cursor containing all photos.
     *
     * @return Cursor containing all photos.
     */
    @Query("SELECT * FROM photo")
    Cursor getPhotoWithCursor();
}
