package fr.vegeto52.realestatemanager.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Vegeto52-PC on 07/11/2023.
 */
@Entity(tableName = "real_estate")
public class RealEstate {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    private double price;
    private double surface;
    private int numberOfRooms;
    private String description;
    private String photo;
    private String address;
    private String pointsOfInterest;
    private boolean statut;
    private String dateOfEntry;
    private String dateOfSale;
    private String agent;

    public RealEstate(long id, String type, double price, double surface, int numberOfRooms, String description, String photo, String address, String pointsOfInterest, boolean statut, String dateOfEntry, String dateOfSale, String agent) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.numberOfRooms = numberOfRooms;
        this.description = description;
        this.photo = photo;
        this.address = address;
        this.pointsOfInterest = pointsOfInterest;
        this.statut = statut;
        this.dateOfEntry = dateOfEntry;
        this.dateOfSale = dateOfSale;
        this.agent = agent;
    }

    public RealEstate() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
