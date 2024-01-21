package fr.vegeto52.realestatemanager;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import fr.vegeto52.realestatemanager.database.room.RealEstateDatabase;
import fr.vegeto52.realestatemanager.provider.RealEstateContentProvider;

/**
 * Created by Vegeto52-PC on 12/01/2024.
 */
public class RealEstateContentProviderTest {

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
    public void getRealEstateWhenNoRealEstateInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_REALESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), greaterThanOrEqualTo(0));
        cursor.close();
    }

    @Test
    public void insertAndGetRealEstate(){
        final Uri uri = mContentResolver.insert(RealEstateContentProvider.URI_REALESTATE, generateRealEstate());
        assertThat(uri, notNullValue());
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_REALESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());

        assertThat(cursor.getCount(), greaterThanOrEqualTo(1));
        assertThat(cursor.moveToLast(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), is("Castle"));

        cursor.close();
    }

    private ContentValues generateRealEstate(){
        final ContentValues values = new ContentValues();
        values.put("type", "Castle");
        values.put("description", "Kaamelott");
        return values;
    }
}
