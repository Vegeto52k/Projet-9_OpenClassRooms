package fr.vegeto52.realestatemanager.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vegeto52-PC on 10/01/2024.
 */
public class ResultsGeocodingApiTest {

    @Test
    public void testStatusGetterSetter() {
        ResultsGeocodingApi resultsGeocodingApi = new ResultsGeocodingApi();
        String status = "OK";

        resultsGeocodingApi.setStatus(status);

        assertEquals(status, resultsGeocodingApi.getStatus());
    }

    @Test
    public void testResultsGetterSetter() {
        ResultsGeocodingApi resultsGeocodingApi = new ResultsGeocodingApi();
        List<ResultsGeocodingApi.Results> resultsList = new ArrayList<>();

        resultsGeocodingApi.setResults(resultsList);

        assertEquals(resultsList, resultsGeocodingApi.getResults());
    }

    @Test
    public void testResultsFormattedAddressGetterSetter() {
        ResultsGeocodingApi.Results results = new ResultsGeocodingApi.Results();
        String formattedAddress = "Washington, USA";

        results.setFormatted_address(formattedAddress);

        assertEquals(formattedAddress, results.getFormatted_address());
    }

    @Test
    public void testResultsGeometryGetterSetter() {
        ResultsGeocodingApi.Results results = new ResultsGeocodingApi.Results();
        ResultsGeocodingApi.Results.Geometry geometry = new ResultsGeocodingApi.Results.Geometry();

        results.setGeometry(geometry);

        assertEquals(geometry, results.getGeometry());
    }

    @Test
    public void testResultsPlaceIdGetterSetter() {
        ResultsGeocodingApi.Results results = new ResultsGeocodingApi.Results();
        String placeId = "ChIJ-bDD5__lhVQRuvNfbGh4QpQ";

        results.setPlace_id(placeId);

        assertEquals(placeId, results.getPlace_id());
    }

    @Test
    public void testResultsAddressComponentsGetterSetter() {
        ResultsGeocodingApi.Results results = new ResultsGeocodingApi.Results();
        List<ResultsGeocodingApi.Results.AddressComponents> addressComponentsList = new ArrayList<>();

        results.setAddress_components(addressComponentsList);

        assertEquals(addressComponentsList, results.getAddress_components());
    }

    @Test
    public void testResultsTypesGetterSetter() {
        ResultsGeocodingApi.Results results = new ResultsGeocodingApi.Results();
        List<String> typesList = new ArrayList<>();

        results.setTypes(typesList);

        assertEquals(typesList, results.getTypes());
    }

    @Test
    public void testGeometryBoundsGetterSetter() {
        ResultsGeocodingApi.Results.Geometry geometry = new ResultsGeocodingApi.Results.Geometry();
        ResultsGeocodingApi.Results.Geometry.Bounds bounds = new ResultsGeocodingApi.Results.Geometry.Bounds();

        geometry.setBounds(bounds);

        assertEquals(bounds, geometry.getBounds());
    }

    @Test
    public void testGeometryLocationGetterSetter() {
        ResultsGeocodingApi.Results.Geometry geometry = new ResultsGeocodingApi.Results.Geometry();
        ResultsGeocodingApi.Results.Geometry.Location location = new ResultsGeocodingApi.Results.Geometry.Location();

        geometry.setLocation(location);

        assertEquals(location, geometry.getLocation());
    }

    @Test
    public void testGeometryLocationTypeGetterSetter() {
        ResultsGeocodingApi.Results.Geometry geometry = new ResultsGeocodingApi.Results.Geometry();
        String locationType = "APPROXIMATE";

        geometry.setLocation_type(locationType);

        assertEquals(locationType, geometry.getLocation_type());
    }

    @Test
    public void testGeometryViewportGetterSetter() {
        ResultsGeocodingApi.Results.Geometry geometry = new ResultsGeocodingApi.Results.Geometry();
        ResultsGeocodingApi.Results.Geometry.Viewport viewport = new ResultsGeocodingApi.Results.Geometry.Viewport();

        geometry.setViewport(viewport);

        assertEquals(viewport, geometry.getViewport());
    }

    @Test
    public void testBoundsNortheastGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds bounds = new ResultsGeocodingApi.Results.Geometry.Bounds();
        ResultsGeocodingApi.Results.Geometry.Bounds.Northeast northeast = new ResultsGeocodingApi.Results.Geometry.Bounds.Northeast();

        bounds.setNortheast(northeast);

        assertEquals(northeast, bounds.getNortheast());
    }

    @Test
    public void testBoundsSouthwestGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds bounds = new ResultsGeocodingApi.Results.Geometry.Bounds();
        ResultsGeocodingApi.Results.Geometry.Bounds.Southwest southwest = new ResultsGeocodingApi.Results.Geometry.Bounds.Southwest();

        bounds.setSouthwest(southwest);

        assertEquals(southwest, bounds.getSouthwest());
    }

    @Test
    public void testNortheastLatGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds.Northeast northeast = new ResultsGeocodingApi.Results.Geometry.Bounds.Northeast();
        double lat = 49.0024442;

        northeast.setLat(lat);

        assertEquals(lat, northeast.getLat(), 0);
    }

    @Test
    public void testNortheastLngGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds.Northeast northeast = new ResultsGeocodingApi.Results.Geometry.Bounds.Northeast();
        double lng = -116.91558;

        northeast.setLng(lng);

        assertEquals(lng, northeast.getLng(), 0);
    }

    @Test
    public void testLocationLatGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Location location = new ResultsGeocodingApi.Results.Geometry.Location();
        double lat = 47.7510741;

        location.setLat(lat);

        assertEquals(lat, location.getLat(), 0);
    }

    @Test
    public void testLocationLngGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Location location = new ResultsGeocodingApi.Results.Geometry.Location();
        double lng = -120.7401385;

        location.setLng(lng);

        assertEquals(lng, location.getLng(), 0);
    }

    @Test
    public void testViewportNortheastGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Viewport viewport = new ResultsGeocodingApi.Results.Geometry.Viewport();
        ResultsGeocodingApi.Results.Geometry.Viewport.Northeast northeast = new ResultsGeocodingApi.Results.Geometry.Viewport.Northeast();

        viewport.setNortheast(northeast);

        assertEquals(northeast, viewport.getNortheast());
    }

    @Test
    public void testViewportSouthwestGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Viewport viewport = new ResultsGeocodingApi.Results.Geometry.Viewport();
        ResultsGeocodingApi.Results.Geometry.Viewport.Southwest southwest = new ResultsGeocodingApi.Results.Geometry.Viewport.Southwest();

        viewport.setSouthwest(southwest);

        assertEquals(southwest, viewport.getSouthwest());
    }

    @Test
    public void testSouthwestLatGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds.Southwest southwest = new ResultsGeocodingApi.Results.Geometry.Bounds.Southwest();
        double lat = 45.543541;

        southwest.setLat(lat);

        assertEquals(lat, southwest.getLat(), 0);
    }

    @Test
    public void testSouthwestLngGetterSetter() {
        ResultsGeocodingApi.Results.Geometry.Bounds.Southwest southwest = new ResultsGeocodingApi.Results.Geometry.Bounds.Southwest();
        double lng = -124.8489739;

        southwest.setLng(lng);

        assertEquals(lng, southwest.getLng(), 0);
    }

    @Test
    public void testAddressComponentsLongNameGetterSetter() {
        ResultsGeocodingApi.Results.AddressComponents addressComponents = new ResultsGeocodingApi.Results.AddressComponents();
        String longName = "Washington";

        addressComponents.setLong_name(longName);

        assertEquals(longName, addressComponents.getLong_name());
    }

    @Test
    public void testAddressComponentsShortNameGetterSetter() {
        ResultsGeocodingApi.Results.AddressComponents addressComponents = new ResultsGeocodingApi.Results.AddressComponents();
        String shortName = "WA";

        addressComponents.setShort_name(shortName);

        assertEquals(shortName, addressComponents.getShort_name());
    }

    @Test
    public void testAddressComponentsTypesGetterSetter() {
        ResultsGeocodingApi.Results.AddressComponents addressComponents = new ResultsGeocodingApi.Results.AddressComponents();
        List<String> typesList = new ArrayList<>();

        addressComponents.setTypes(typesList);

        assertEquals(typesList, addressComponents.getTypes());
    }
}
