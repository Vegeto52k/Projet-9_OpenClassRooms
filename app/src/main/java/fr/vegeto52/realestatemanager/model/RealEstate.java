package fr.vegeto52.realestatemanager.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * The RealEstate class represents a real estate property with details such as type, price, and location.
 */
@Entity(tableName = "real_estate")
public class RealEstate implements Parcelable {

    // Auto-generated ID for the real estate property
    @PrimaryKey(autoGenerate = true)
    private long id;

    // Type of the real estate property (e.g., Flat, House, Studio)
    private String type;

    // Price of the real estate property
    private Double price;

    // Surface area of the real estate property
    private Double surface;

    // Number of rooms in the real estate property
    private Integer numberOfRooms;

    // Description of the real estate property
    private String description;

    // Address of the real estate property
    private String address;

    // Points of interest near the real estate property
    private String pointsOfInterest;

    // Status of the real estate property (available or sold)
    private boolean statut;

    // Date of entry of the real estate property into the market
    private String dateOfEntry;

    // Date of sale of the real estate property (if sold)
    private String dateOfSale;

    // Agent handling the real estate property
    private String agent;

    // Latitude of the real estate property's location
    private Double latitude;

    // Longitude of the real estate property's location
    private Double longitude;


    // Constructors
    @Ignore
    public RealEstate(long id, String type, Double price, Double surface, Integer numberOfRooms, String description, String address, String pointsOfInterest, boolean statut, String dateOfEntry, String dateOfSale, String agent) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.numberOfRooms = numberOfRooms;
        this.description = description;
        this.address = address;
        this.pointsOfInterest = pointsOfInterest;
        this.statut = statut;
        this.dateOfEntry = dateOfEntry;
        this.dateOfSale = dateOfSale;
        this.agent = agent;
    }

    public RealEstate() {

    }


    // Getters/Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Nullable
    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    @Nullable
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Nullable
    public String getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(String pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }


    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    @Nullable
    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    @Nullable
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // Parcelable implementation
    protected RealEstate(Parcel in) {
        id = in.readLong();
        type = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            surface = null;
        } else {
            surface = in.readDouble();
        }
        if (in.readByte() == 0) {
            numberOfRooms = null;
        } else {
            numberOfRooms = in.readInt();
        }
        description = in.readString();
        address = in.readString();
        pointsOfInterest = in.readString();
        statut = in.readByte() != 0;
        dateOfEntry = in.readString();
        dateOfSale = in.readString();
        agent = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    public static final Creator<RealEstate> CREATOR = new Creator<RealEstate>() {
        @Override
        public RealEstate createFromParcel(Parcel in) {
            return new RealEstate(in);
        }

        @Override
        public RealEstate[] newArray(int size) {
            return new RealEstate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(type);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        if (surface == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(surface);
        }
        if (numberOfRooms == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfRooms);
        }
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeString(pointsOfInterest);
        parcel.writeByte((byte) (statut ? 1 : 0));
        parcel.writeString(dateOfEntry);
        parcel.writeString(dateOfSale);
        parcel.writeString(agent);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
    }

    /**
     * Creates a RealEstate instance from ContentValues.
     *
     * @param values ContentValues containing real estate information.
     * @return RealEstate instance.
     */
    public static RealEstate fromContentValues(ContentValues values){
        RealEstate realEstate = new RealEstate();
        if (values.containsKey("type")) realEstate.setType(values.getAsString("type"));
        if (values.containsKey("description")) realEstate.setDescription(values.getAsString("description"));
        return realEstate;
    }
}
