package fr.vegeto52.realestatemanager.database.repository;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

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

    public LiveData<List<Photo>> getListPhoto(){
        return mListPhoto;
    }

    public LiveData<Photo> getPhoto(long photoId){
        return mPhotoDao.getPhoto(photoId);
    }

//    public void insertPhoto(Photo photo){
//        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.insert(photo));
//    }

    public void insertPhoto(Photo photo){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            // Votre tâche en arrière-plan ici
            Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.insert(photo));
        });

// Attendre que la tâche en arrière-plan se termine
        try {
            future.get(); // Ceci bloque jusqu'à ce que la tâche soit terminée
        } catch (InterruptedException | ExecutionException e) {
            // Gérer les exceptions si nécessaire
        } finally {
            executor.shutdown(); // Arrêter l'ExecutorService une fois terminé
        }
    }

    public void updatePhoto(Photo photo){
        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.update(photo));
    }

    public void deletePhoto(Photo photo){
        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.delete(photo));
    }

    public LiveData<List<Photo>> getListPhotoToRealEstate(long realEstateId){
        return mPhotoDao.getListPhotoToRealEstate(realEstateId);
    }

//    public void deleteAllPhotos(long realEstateId){
//        Executors.newSingleThreadExecutor().execute(() -> mPhotoDao.deleteAllPhotos(realEstateId));
//    }

    public void deleteAllPhotos(long realEstateId){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            executor.execute(() -> mPhotoDao.deleteAllPhotos(realEstateId));
            // Votre tâche en arrière-plan ici
        });

// Attendre que la tâche en arrière-plan se termine
        try {
            future.get(); // Ceci bloque jusqu'à ce que la tâche soit terminée
        } catch (InterruptedException | ExecutionException e) {
            // Gérer les exceptions si nécessaire
        } finally {
            executor.shutdown(); // Arrêter l'ExecutorService une fois terminé
        }
    }
}
