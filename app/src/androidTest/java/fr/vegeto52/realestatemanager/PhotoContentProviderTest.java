package fr.vegeto52.realestatemanager;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.provider.PhotoContentProvider;

/**
 * Created by Vegeto52-PC on 12/01/2024.
 */
public class PhotoContentProviderTest {

    private ContentResolver mContentResolver;
    private static final long USER_ID = 1;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), RealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();

        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void getPhotoWhenNoPhotoInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PhotoContentProvider.URI_PHOTO, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), greaterThanOrEqualTo(0));
        cursor.close();
    }
}
