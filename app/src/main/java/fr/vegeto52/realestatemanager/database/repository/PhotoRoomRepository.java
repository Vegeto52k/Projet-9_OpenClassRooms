package fr.vegeto52.realestatemanager.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.vegeto52.realestatemanager.database.room.dao.PhotoDao;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * The PhotoRoomRepository class acts as a repository for managing photo data using Room database.
 * It uses the PhotoDao for database operations and provides LiveData for observing photo data changes.
 */
public class PhotoRoomRepository {

    private final PhotoDao mPhotoDao;
    private final LiveData<List<Photo>> mListPhoto;

    /**
     * Constructor for the PhotoRoomRepository class.
     *
     * @param photoDao The PhotoDao used for database operations.
     */
    public PhotoRoomRepository(PhotoDao photoDao) {
        mPhotoDao = photoDao;
        mListPhoto = mPhotoDao.getListPhoto();
    }

    /**
     * Get LiveData for observing the list of photos.
     *
     * @return The LiveData object containing the list of photos.
     */
    public LiveData<List<Photo>> getListPhoto() {
        return mListPhoto;
    }

    /**
     * Insert a new photo into the database.
     *
     * @param photo The photo to be inserted.
     */
    public void insertPhoto(Photo photo) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.insert(photo)));
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ignored) {
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Get LiveData for observing the list of photos associated with a specific real estate.
     *
     * @param realEstateId The ID of the real estate for which photos are retrieved.
     * @return The LiveData object containing the list of photos for the specified real estate.
     */
    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId) {
        return mPhotoDao.getListPhotoToRealEstate(realEstateId);
    }

    /**
     * Delete all photos associated with a specific real estate.
     *
     * @param realEstateId The ID of the real estate for which photos are deleted.
     */
    public void deleteAllPhotos(long realEstateId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> executor.execute(() -> mPhotoDao.deleteAllPhotos(realEstateId)));
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ignored) {
        } finally {
            executor.shutdown();
        }
    }
}
