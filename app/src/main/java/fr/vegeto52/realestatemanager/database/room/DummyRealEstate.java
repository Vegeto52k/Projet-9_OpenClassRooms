package fr.vegeto52.realestatemanager.database.room;

import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.realestatemanager.model.RealEstate;

/**
 * Created by Vegeto52-PC on 10/11/2023.
 */
public class DummyRealEstate {

    public List<RealEstate> createListRealEstate() {

        List<RealEstate> realEstateList = new ArrayList<>();

        RealEstate realEstate1 = new RealEstate();
        realEstate1.setType("Flat");
        realEstate1.setPrice(17870000.0);
        realEstate1.setSurface(750.0);
        realEstate1.setNumberOfRooms(8);
        realEstate1.setDescription("Beautiful flat");
        realEstate1.setPhoto("/document/msf:31");
        realEstate1.setAddress("rue Paul Rougier, Tourves");
        realEstate1.setPointsOfInterest("Cinema, Pharmacy");
        realEstate1.setStatut(false);
        realEstate1.setDateOfEntry("2023-05-10");
        realEstate1.setAgent("Arthur");

        RealEstate realEstate2 = new RealEstate();
        realEstate2.setType("Flat");
        realEstate2.setPrice(25000000.0);
        realEstate2.setSurface(850.0);
        realEstate2.setNumberOfRooms(10);
        realEstate2.setDescription("Spacious flat with a view");
        realEstate2.setPhoto("TODO");
        realEstate2.setAddress("rue Hoche, Tourves");
        realEstate2.setPointsOfInterest("Shopping Mall, Park");
        realEstate2.setStatut(true);
        realEstate2.setDateOfEntry("2023-06-01");
        realEstate2.setDateOfSale("2023-11-10");
        realEstate2.setAgent("Sophie");

        RealEstate realEstate3 = new RealEstate();
        realEstate3.setType("House");
        realEstate3.setPrice(35000000.0);
        realEstate3.setSurface(1200.0);
        realEstate3.setNumberOfRooms(12);
        realEstate3.setDescription("Charming country house");
        realEstate3.setPhoto("TODO");
        realEstate3.setAddress("rue du jeu de la Paume, Tourves");
        realEstate3.setPointsOfInterest("Nature Reserve, School");
        realEstate3.setStatut(false);
        realEstate3.setDateOfEntry("2023-07-15");
        realEstate3.setAgent("Michael");

        RealEstate realEstate4 = new RealEstate();
        realEstate4.setType("Studio");
        realEstate4.setPrice(8000000.0);
        realEstate4.setSurface(400.0);
        realEstate4.setNumberOfRooms(1);
        realEstate4.setDescription("Cozy studio in the heart of the city");
        realEstate4.setPhoto("TODO");
        realEstate4.setAddress("Avenue Gambetta, Tourves");
        realEstate4.setPointsOfInterest("Public Transportation, Restaurants");
        realEstate4.setStatut(true);
        realEstate4.setDateOfEntry("2023-08-20");
        realEstate4.setDateOfSale("2023-10-05");
        realEstate4.setAgent("Emma");

        RealEstate realEstate5 = new RealEstate();
        realEstate5.setType("Mansion");
        realEstate5.setPrice(150000000.0);
        realEstate5.setSurface(2500.0);
        realEstate5.setNumberOfRooms(20);
        realEstate5.setDescription("Luxurious mansion with a pool");
        realEstate5.setPhoto("TODO");
        realEstate5.setAddress("chemin les Flouns, Tourves");
        realEstate5.setPointsOfInterest("Golf Course, Spa");
        realEstate5.setStatut(false);
        realEstate5.setDateOfEntry("2023-09-05");
        realEstate5.setAgent("Oliver");

        RealEstate realEstate6 = new RealEstate();
        realEstate6.setType("Oceanfront Apartment");
        realEstate6.setPrice(30000000.0);
        realEstate6.setSurface(1000.0);
        realEstate6.setNumberOfRooms(6);
        realEstate6.setDescription("Stunning apartment overlooking the ocean");
        realEstate6.setPhoto("TODO");
        realEstate6.setAddress("rue Pasteur, Tourves");
        realEstate6.setPointsOfInterest("Beach, Seafood Restaurants");
        realEstate6.setStatut(false);
        realEstate6.setDateOfEntry("2023-10-10");
        realEstate6.setAgent("Sophie");

        realEstateList.add(realEstate1);
        realEstateList.add(realEstate2);
        realEstateList.add(realEstate3);
        realEstateList.add(realEstate4);
        realEstateList.add(realEstate5);
        realEstateList.add(realEstate6);

        return realEstateList;
    }
}
