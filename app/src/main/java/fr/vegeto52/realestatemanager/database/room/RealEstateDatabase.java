package fr.vegeto52.realestatemanager.database.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import fr.vegeto52.realestatemanager.database.room.dao.PhotoDao;
import fr.vegeto52.realestatemanager.database.room.dao.RealEstateDao;
import fr.vegeto52.realestatemanager.model.Photo;
import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * The RealEstateDatabase class serves as the Room database for the real estate application.
 * It defines entities, version, and type converters for Room.
 */
@Database(entities = {RealEstate.class, Photo.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateDatabase extends RoomDatabase {

    /**
     * Abstract method to retrieve the RealEstateDao interface for database operations on RealEstate entities.
     *
     * @return RealEstateDao instance.
     */
    public abstract RealEstateDao mRealEstateDao();

    /**
     * Abstract method to retrieve the PhotoDao interface for database operations on Photo entities.
     *
     * @return PhotoDao instance.
     */
    public abstract PhotoDao mPhotoDao();

    // Singleton pattern to ensure a single instance of the database
    private static volatile RealEstateDatabase instance;

    /**
     * Get the singleton instance of the RealEstateDatabase.
     *
     * @param context The application context.
     * @return RealEstateDatabase instance.
     */
    public static RealEstateDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (RealEstateDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), RealEstateDatabase.class, "real_estate_db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return instance;
    }

    /**
     * Callback to prepopulate the database with dummy real estate data on creation.
     *
     * @return Callback instance.
     */
    private static Callback prepopulateDatabase() {
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
