package fr.vegeto52.realestatemanager.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import fr.vegeto52.realestatemanager.database.room.Converters;

/**
 * Created by Vegeto52-PC on 04/12/2023.
 */
@Entity (tableName = "photo",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
        parentColumns = "id",
        childColumns = "realEstateId"),
        indices = {@Index("realEstateId")})
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @TypeConverters(Converters.class)
    private Uri uriPhoto;
    private long realEstateId;

    @Ignore
    public Photo(long id, Uri uriPhoto, long realEstateId) {
        this.id = id;
        this.uriPhoto = uriPhoto;
        this.realEstateId = realEstateId;
    }

    public Photo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(Uri uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public long getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(long realEstateId) {
        this.realEstateId = realEstateId;
    }
}
