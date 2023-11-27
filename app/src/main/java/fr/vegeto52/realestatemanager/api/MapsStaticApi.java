package fr.vegeto52.realestatemanager.api;

import fr.vegeto52.realestatemanager.model.RealEstate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vegeto52-PC on 22/11/2023.
 */
public interface MapsStaticApi {

    @GET("maps/api/staticmap")
    Call<ResponseBody> getStaticMaps(
            @Query("center") String adress,
            @Query("zoom") int zoom,
            @Query("size") String size,
            @Query("markers") String marker,
            @Query("key") String apiKey
    );
}
