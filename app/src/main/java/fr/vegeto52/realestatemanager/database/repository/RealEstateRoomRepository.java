package fr.vegeto52.realestatemanager.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.vegeto52.realestatemanager.database.MainApplication;
import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 15/11/2023.
 */
public class RealEstateRoomRepository {

    private final RealEstateDao mRealEstateDao;
    private final LiveData<List<RealEstate>> mListRealEstate;

    public RealEstateRoomRepository(RealEstateDao realEstateDao){
        mRealEstateDao = realEstateDao;
        mListRealEstate = mRealEstateDao.getListRealEstate();
    }

    public LiveData<List<RealEstate>> getListRealEstate(){
        return mListRealEstate;
    }

    public LiveData<RealEstate> getRealEstate(long realEstateId){
        return mRealEstateDao.getRealEstate(realEstateId);
    }

    public void insertRealEstate(RealEstate realEstate){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            // Votre tâche en arrière-plan ici
            Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.insert(realEstate));
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

    public long insertRealEstateAndGetId(RealEstate realEstate){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Long> future = executor.submit(() -> {
            // Insérer le bien immobilier et récupérer l'ID généré
            return mRealEstateDao.insertAndGetId(realEstate);
        });

        try {
            return future.get(); // Renvoie l'ID généré
        } catch (InterruptedException | ExecutionException e) {
            // Gérer les exceptions si nécessaire
            return -1; // Ou une valeur qui indique une erreur, à toi de décider
        } finally {
            executor.shutdown(); // Arrêter l'ExecutorService une fois terminé
        }
    }

    public void updateRealEstate(RealEstate realEstate){
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.update(realEstate));
    }

    public void deleteRealEstate(RealEstate realEstate){
        Executors.newSingleThreadExecutor().execute(() -> mRealEstateDao.delete(realEstate));
    }
}
