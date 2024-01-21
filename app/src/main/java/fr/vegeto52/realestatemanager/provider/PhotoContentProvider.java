package fr.vegeto52.realestatemanager.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.model.Photo;

/**
 * Created by Vegeto52-PC on 07/01/2024.
 */
public class PhotoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "fr.vegeto52.realestatemanager.provider.photo";
    public static final String TABLE_NAME = Photo.class.getSimpleName();
    public static final Uri URI_PHOTO = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (getContext() != null) {
            final Cursor cursor = RealEstateDatabase.getInstance(getContext()).mPhotoDao().getPhotoWithCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (getContext() != null){
            assert contentValues != null;
            final long id = RealEstateDatabase.getInstance(getContext()).mPhotoDao().insertAndGetId(Photo.fromContentValues(contentValues));
            if (id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    //    return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
