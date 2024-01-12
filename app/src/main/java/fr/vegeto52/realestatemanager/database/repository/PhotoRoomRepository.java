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
 * Created by Vegeto52-PC on 04/12/2023.
 */
public class PhotoRoomRepository {

    private final PhotoDao mPhotoDao;
    private final LiveData<List<Photo>> mListPhoto;

    public PhotoRoomRepository(PhotoDao photoDao) {
        mPhotoDao = photoDao;
        mListPhoto = mPhotoDao.getListPhoto();
    }

    public LiveData<List<Photo>> getListPhoto() {
        return mListPhoto;
    }

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

    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId) {
        return mPhotoDao.getListPhotoToRealEstate(realEstateId);
    }

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
