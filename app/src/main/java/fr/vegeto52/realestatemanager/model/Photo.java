package fr.vegeto52.realestatemanager.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
                parentColumns = "id",
                childColumns = "realEstateId"),
        indices = {@Index("realEstateId")})
public class Photo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @TypeConverters(Converters.class)
    private Uri uriPhoto;

    private String description;
    private long realEstateId;


    // Constructor
    @Ignore
    public Photo(long id, Uri uriPhoto, long realEstateId) {
        this.id = id;
        this.uriPhoto = uriPhoto;
        this.realEstateId = realEstateId;
    }

    public Photo() {
    }


    // Getters/Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(long realEstateId) {
        this.realEstateId = realEstateId;
    }


    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeParcelable(uriPhoto, i);
        parcel.writeString(description);
        parcel.writeLong(realEstateId);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    protected Photo(Parcel in) {
        id = in.readLong();
        uriPhoto = in.readParcelable(Uri.class.getClassLoader());
        description = in.readString();
        realEstateId = in.readLong();
    }
}
