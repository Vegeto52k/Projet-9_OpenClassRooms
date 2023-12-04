package fr.vegeto52.realestatemanager.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

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

    public LiveData<List<Photo>> getListPhoto(){
        return mListPhoto;
    }

    public LiveData<Photo> getPhoto(long photoId){
        return mPhotoDao.getPhoto(photoId);
    }

    public void insertPhoto(Photo photo){
        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.insert(photo));
    }

    public void updatePhoto(Photo photo){
        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.update(photo));
    }

    public void deletePhoto(Photo photo){
        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.delete(photo));
    }
}
