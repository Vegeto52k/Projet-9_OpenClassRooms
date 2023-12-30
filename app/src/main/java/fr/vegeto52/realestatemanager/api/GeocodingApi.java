package fr.vegeto52.realestatemanager.api;

import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vegeto52-PC on 28/12/2023.
 */
public interface GeocodingApi {

    @GET("maps/api/geocode/json?")
    Call<ResultsGeocodingApi> getGeocoding(
            @Query("address") String address,
            @Query("key") String apiKey
    );
}
