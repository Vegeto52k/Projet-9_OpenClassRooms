package fr.vegeto52.realestatemanager.database.api;

import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The GeocodingApi interface defines the API endpoints for geocoding operations.
 * It utilizes Retrofit for making HTTP requests to a geocoding service.
 */
public interface GeocodingApi {

    /**
     * Perform a geocoding request to convert an address into geographic coordinates.
     *
     * @param address The address to be geocoded.
     * @param apiKey The API key for accessing the geocoding service.
     * @return A Call object representing the asynchronous geocoding request.
     */
    @GET("maps/api/geocode/json?")
    Call<ResultsGeocodingApi> getGeocoding(
            @Query("address") String address,
            @Query("key") String apiKey
    );
}
