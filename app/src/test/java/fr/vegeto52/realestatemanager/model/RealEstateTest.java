package fr.vegeto52.realestatemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Vegeto52-PC on 10/01/2024.
 */
@RunWith(MockitoJUnitRunner.class)
public class RealEstateTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDefaultConstructor() {
        long id = 1L;
        String type = "House";
        Double price = 250000.0;
        Double surface = 150.0;
        Integer numberOfRooms = 3;
        String description = "Spacious house with a garden";
        String address = "123 Main Street";
        String pointsOfInterest = "School, Park, Shopping Center";
        boolean statut = true;
        String dateOfEntry = "2022-01-01";
        String dateOfSale = "2022-02-01";
        String agent = "John Doe";

        RealEstate realEstate = new RealEstate(id, type, price, surface, numberOfRooms, description, address, pointsOfInterest, statut, dateOfEntry, dateOfSale, agent);

        assertNotNull(realEstate);

        assertEquals(id, realEstate.getId());
        assertEquals(type, realEstate.getType());
        assertEquals(price, realEstate.getPrice());
        assertEquals(surface, realEstate.getSurface());
        assertEquals(numberOfRooms, realEstate.getNumberOfRooms());
        assertEquals(description, realEstate.getDescription());
        assertEquals(address, realEstate.getAddress());
        assertEquals(pointsOfInterest, realEstate.getPointsOfInterest());
        assertEquals(statut, realEstate.isStatut());
        assertEquals(dateOfEntry, realEstate.getDateOfEntry());
        assertEquals(dateOfSale, realEstate.getDateOfSale());
        assertEquals(agent, realEstate.getAgent());
    }

    @Test
    public void testGettersAndSetters() {
        RealEstate realEstate = new RealEstate();

        long id = 1L;
        String type = "House";
        Double price = 250000.0;
        Double surface = 150.0;
        Integer numberOfRooms = 3;
        String description = "Spacious house with a garden";
        String address = "123 Main Street";
        String pointsOfInterest = "School, Park, Shopping Center";
        boolean statut = true;
        String dateOfEntry = "2022-01-01";
        String dateOfSale = "2022-02-01";
        String agent = "John Doe";
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        realEstate.setId(id);
        realEstate.setType(type);
        realEstate.setPrice(price);
        realEstate.setSurface(surface);
        realEstate.setNumberOfRooms(numberOfRooms);
        realEstate.setDescription(description);
        realEstate.setAddress(address);
        realEstate.setPointsOfInterest(pointsOfInterest);
        realEstate.setStatut(statut);
        realEstate.setDateOfEntry(dateOfEntry);
        realEstate.setDateOfSale(dateOfSale);
        realEstate.setAgent(agent);
        realEstate.setLatitude(latitude);
        realEstate.setLongitude(longitude);

        assertEquals(id, realEstate.getId());
        assertEquals(type, realEstate.getType());
        assertEquals(price, realEstate.getPrice());
        assertEquals(surface, realEstate.getSurface());
        assertEquals(numberOfRooms, realEstate.getNumberOfRooms());
        assertEquals(description, realEstate.getDescription());
        assertEquals(address, realEstate.getAddress());
        assertEquals(pointsOfInterest, realEstate.getPointsOfInterest());
        assertEquals(statut, realEstate.isStatut());
        assertEquals(dateOfEntry, realEstate.getDateOfEntry());
        assertEquals(dateOfSale, realEstate.getDateOfSale());
        assertEquals(agent, realEstate.getAgent());
        assertEquals(latitude, realEstate.getLatitude());
        assertEquals(longitude, realEstate.getLongitude());
    }

    @Test
    public void testNullableGettersWithNullValues() {
        RealEstate realEstate = new RealEstate();

        assertNull(realEstate.getType());
        assertNull(realEstate.getPrice());
        assertNull(realEstate.getSurface());
        assertNull(realEstate.getNumberOfRooms());
        assertNull(realEstate.getDescription());
        assertNull(realEstate.getAddress());
        assertNull(realEstate.getPointsOfInterest());
        assertNull(realEstate.getDateOfSale());
        assertNull(realEstate.getAgent());
        assertNull(realEstate.getLatitude());
        assertNull(realEstate.getLongitude());
    }
}
