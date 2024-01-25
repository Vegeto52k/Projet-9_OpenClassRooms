package fr.vegeto52.realestatemanager.database.repository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.database.api.GeocodingApi;
import fr.vegeto52.realestatemanager.database.api.RetrofitService;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The GeocodingRepository class acts as a repository for geocoding operations.
 * It uses the GeocodingApi interface and RetrofitService for making HTTP requests.
 */
public class GeocodingRepository {

    // API key for accessing the geocoding service
    private final static String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;

    /**
     * Default constructor for the GeocodingRepository class.
     */
    public GeocodingRepository() {
    }

    /**
     * Callback interface for handling geocoding results asynchronously.
     */
    public interface GeocodingCallBack {
        void onGeocodingResult(ResultsGeocodingApi resultsGeocodingApi);
    }

    /**
     * Perform a geocoding request asynchronously and invoke the callback with the results.
     *
     * @param address The address to be geocoded.
     * @param callBack The callback to be invoked with the geocoding results.
     */
    public void getGeocoding(String address, GeocodingCallBack callBack) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            ResultsGeocodingApi result = getGeocodingMethod(address);
            if (callBack != null) {
                callBack.onGeocodingResult(result);
            }
        });
    }

    /**
     * Perform the actual geocoding request using Retrofit and the GeocodingApi.
     *
     * @param address The address to be geocoded.
     * @return The geocoding results.
     */
    private ResultsGeocodingApi getGeocodingMethod(String address) {
        GeocodingApi geocodingApi = RetrofitService.getRetrofitInstance().create(GeocodingApi.class);
        Call<ResultsGeocodingApi> call = geocodingApi.getGeocoding(address, MAPS_API_KEY);

        try {
            Response<ResultsGeocodingApi> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
