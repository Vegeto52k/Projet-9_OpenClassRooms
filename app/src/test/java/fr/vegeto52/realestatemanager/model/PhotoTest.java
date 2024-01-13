package fr.vegeto52.realestatemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Created by Vegeto52-PC on 09/01/2024.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Uri mUriMock;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDefaultConstructor() {
        Photo photo = new Photo();

        assertNotNull(photo);

        assertEquals(0, photo.getId());
        assertNull(photo.getUriPhoto());
        assertNull(photo.getDescription());
        assertEquals(0, photo.getRealEstateId());
    }

    @Test
    public void testGettersAndSetters() {
        Photo photo = new Photo();

        photo.setId(0);
        photo.setUriPhoto(mUriMock);
        photo.setDescription("Description");
        photo.setRealEstateId(1);

        assertEquals(0, photo.getId());
        assertEquals(mUriMock, photo.getUriPhoto());
        assertEquals("Description", photo.getDescription());
        assertEquals(1, photo.getRealEstateId());


    }
}
