package fr.vegeto52.realestatemanager.database.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The MapsStaticApi interface defines the API endpoints for retrieving static maps.
 * It utilizes Retrofit for making HTTP requests to the Google Maps Static API.
 */
public interface MapsStaticApi {

    /**
     * Retrieve a static map image based on the specified parameters.
     *
     * @param address The address to be used as the center of the map.
     * @param zoom The zoom level of the map.
     * @param size The size of the map image (e.g., "widthxheight").
     * @param marker The marker to be displayed on the map.
     * @param apiKey The API key for accessing the Google Maps Static API.
     * @return A Call object representing the asynchronous request for the static map.
     */
    @GET("maps/api/staticmap")
    Call<ResponseBody> getStaticMaps(
            @Query("center") String address,
            @Query("zoom") int zoom,
            @Query("size") String size,
            @Query("markers") String marker,
            @Query("key") String apiKey
    );
}
