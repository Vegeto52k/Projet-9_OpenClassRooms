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
 * The PhotoContentProvider class extends ContentProvider and provides access to photo data.
 * It interacts with the underlying RealEstateDatabase to perform CRUD operations on photos.
 */
public class PhotoContentProvider extends ContentProvider {

    // Authority for the content provider
    public static final String AUTHORITY = "fr.vegeto52.realestatemanager.provider.photo";

    // Table name for the photo content provider
    public static final String TABLE_NAME = Photo.class.getSimpleName();

    // Content URI for accessing the photo data
    public static final Uri URI_PHOTO = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    /**
     * Called when the content provider is created. Returns true to indicate successful creation.
     */
    @Override
    public boolean onCreate() {
        return true;
    }

    /**
     * Performs a query on the content provider.
     *
     * @param uri           The URI to query.
     * @param strings       The projection.
     * @param s             The selection clause.
     * @param strings1      The selection arguments.
     * @param s1            The sort order.
     * @return A Cursor containing the results of the query.
     */
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

    /**
     * Gets the MIME type of the data at the given URI.
     *
     * @param uri The URI to query.
     * @return The MIME type of the data.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
    }

    /**
     * Inserts new data into the content provider.
     *
     * @param uri            The URI to insert at.
     * @param contentValues  The values to insert.
     * @return The URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    //    if (getContext() != null){
    //        assert contentValues != null;
    //        final long id = RealEstateDatabase.getInstance(getContext()).mPhotoDao().insertAndGetId(Photo.fromContentValues(contentValues));
    //        if (id != 0){
    //            getContext().getContentResolver().notifyChange(uri, null);
    //            return ContentUris.withAppendedId(uri, id);
    //        }
    //    }
    //    throw new IllegalArgumentException("Failed to insert row into " + uri);
        return null;
    }

    /**
     * Deletes data from the content provider.
     *
     * @param uri       The URI to delete from.
     * @param s         The selection clause.
     * @param strings   The selection arguments.
     * @return The number of rows deleted.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    /**
     * Updates data in the content provider.
     *
     * @param uri             The URI to update.
     * @param contentValues   The values to update.
     * @param s               The selection clause.
     * @param strings         The selection arguments.
     * @return The number of rows updated.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
