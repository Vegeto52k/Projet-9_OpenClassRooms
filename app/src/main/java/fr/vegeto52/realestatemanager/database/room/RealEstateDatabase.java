package fr.vegeto52.realestatemanager.database.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import fr.vegeto52.realestatemanager.database.MainApplication;
import fr.vegeto52.realestatemanager.database.room.dao.PhotoDao;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 10/11/2023.
 */
@Database(entities = {RealEstate.class, Photo.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateDatabase extends RoomDatabase {
    public abstract RealEstateDao mRealEstateDao();
    public abstract PhotoDao mPhotoDao();
    private static RealEstateDatabase instance;

    public static RealEstateDatabase getInstance(Context context){
        if (instance == null){
            synchronized (RealEstateDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), RealEstateDatabase.class, "real_estate_db")
                    //        .fallbackToDestructiveMigration()
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return instance;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                DummyRealEstate dummyRealEstate = new DummyRealEstate();

                Executors.newSingleThreadExecutor().execute(() -> instance.mRealEstateDao().insertList(dummyRealEstate.createListRealEstate()));
            }
        };
    }
}
