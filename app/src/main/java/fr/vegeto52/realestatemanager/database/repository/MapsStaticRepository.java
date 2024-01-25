package fr.vegeto52.realestatemanager.database.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.database.api.MapsStaticApi;
import fr.vegeto52.realestatemanager.database.api.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The MapsStaticRepository class acts as a repository for retrieving static maps.
 * It uses the MapsStaticApi interface and RetrofitService for making HTTP requests.
 */
public class MapsStaticRepository {

    // API key for accessing the Google Maps Static API
    private final static String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;
    private final MutableLiveData<Response<ResponseBody>> mResponseLiveData = new MutableLiveData<>();

    /**
     * Default constructor for the MapsStaticRepository class.
     */
    public MapsStaticRepository() {
    }

    /**
     * Request a static map image asynchronously using the MapsStaticApi.
     *
     * @param address The address to be used as the center of the map.
     */
    public void getMapsStatic(String address){
        MapsStaticApi mapsStaticApi = RetrofitService.getRetrofitInstance().create(MapsStaticApi.class);
        Call<ResponseBody> call = mapsStaticApi.getStaticMaps(address, 18, "200x200", "color:blue|" + address, MAPS_API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mResponseLiveData.setValue(response);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mResponseLiveData.setValue(null);
            }
        });
    }

    /**
     * Get the LiveData object for observing responses from the static maps API.
     * Initiates the static map request when LiveData is observed.
     *
     * @param address The address to be used as the center of the map.
     * @return The LiveData object containing the API response.
     */
    public LiveData<Response<ResponseBody>> getLiveDataMapsStatic(String address){
        getMapsStatic(address);
        return mResponseLiveData;
    }
}
