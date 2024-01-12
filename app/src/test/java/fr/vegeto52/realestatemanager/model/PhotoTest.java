package fr.vegeto52.realestatemanager.model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;


/**
 * Created by Vegeto52-PC on 09/01/2024.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotoTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Uri mUriMock;

    @Mock
    Parcel mParcelMock;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDefaultConstructor() {
        Photo photo = new Photo();

        // Vérifier que l'objet n'est pas null
        assertNotNull(photo);

        // Vérifier que les champs sont correctement initialisés
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

    //    Long id = photo.getId();
    //    Uri uri = photo.getUriPhoto();
    //    String description = photo.getDescription();
    //    Long realEstateId = photo.getRealEstateId();

        assertEquals(0, photo.getId());
        assertEquals(mUriMock, photo.getUriPhoto());
        assertEquals("Description", photo.getDescription());
        assertEquals(1, photo.getRealEstateId());


    }

//    @Test
//    public void testParcelable() {
//        // Créer une instance de Photo avec des valeurs
//        Photo originalPhoto = new Photo();
//        originalPhoto.setId(1);
//        Uri uri = Uri.parse("content://example.com/image.jpg");
//        originalPhoto.setUriPhoto(uri);
//        originalPhoto.setDescription("Test description");
//        originalPhoto.setRealEstateId(123);
//
//        // Écrire l'objet dans un Parcel
//        Parcel parcel = Parcel.obtain();
//        originalPhoto.writeToParcel(parcel, 0);
//
//        // Déplacer le Parcel à la position initiale avant de lire
//        parcel.setDataPosition(0);
//
//        // Lire l'objet depuis le Parcel
//        Photo createdFromParcel = Photo.CREATOR.createFromParcel(parcel);
//
//        // Vérifier que les valeurs sont les mêmes
//        assertEquals(originalPhoto.getId(), createdFromParcel.getId());
//        assertEquals(originalPhoto.getUriPhoto(), createdFromParcel.getUriPhoto());
//        assertEquals(originalPhoto.getDescription(), createdFromParcel.getDescription());
//        assertEquals(originalPhoto.getRealEstateId(), createdFromParcel.getRealEstateId());
//
//        Photo photo = new Photo();
//
//        photo.setId(0);
//        photo.setUriPhoto(mUriMock);
//        photo.setDescription("Description");
//        photo.setRealEstateId(1);
//
//
//        Parcel parcel = Parcel.obtain();
//        photo.writeToParcel(parcel, photo.describeContents());
//
//        parcel.setDataPosition(0);
//
//        Photo createdFromParcel = Photo.CREATOR.createFromParcel(parcel);
//
//        assertEquals(photo.getId(), createdFromParcel.getId());
//        assertEquals(photo.getUriPhoto(), createdFromParcel.getUriPhoto());
//        assertEquals(photo.getDescription(), createdFromParcel.getDescription());
//        assertEquals(photo.getRealEstateId(), createdFromParcel.getRealEstateId());
//    }
}
