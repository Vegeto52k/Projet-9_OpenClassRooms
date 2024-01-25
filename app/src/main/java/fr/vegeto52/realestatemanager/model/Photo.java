package fr.vegeto52.realestatemanager.model;

import android.content.ContentValues;
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
 * The Photo class represents an image associated with a real estate property.
 * It includes information such as the image URI, description, and the ID of the associated real estate.
 */
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
                parentColumns = "id",
                childColumns = "realEstateId"),
        indices = {@Index("realEstateId")})
public class Photo implements Parcelable {

    // Auto-generated ID for the photo
    @PrimaryKey(autoGenerate = true)
    private long id;

    // URI of the photo using TypeConverters for database storage
    @TypeConverters(Converters.class)
    private Uri uriPhoto;

    // Description of the photo
    private String description;

    // ID of the real estate associated with the photo
    private long realEstateId;


    // Constructors
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


    // Parcelable implementation
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

    // Creator for Parcelable
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

    // Parcelable constructor
    protected Photo(Parcel in) {
        id = in.readLong();
        uriPhoto = in.readParcelable(Uri.class.getClassLoader());
        description = in.readString();
        realEstateId = in.readLong();
    }

    /**
     * Creates a Photo instance from ContentValues.
     *
     * @param values ContentValues containing photo information.
     * @return Photo instance.
     */
    public static Photo fromContentValues(ContentValues values){
        Photo photo = new Photo();
        if (values.containsKey("description")) photo.setDescription(values.getAsString("description"));
        if (values.containsKey("realEstateId")) photo.setRealEstateId(values.getAsLong("realEstateId"));
        return photo;
    }
}
