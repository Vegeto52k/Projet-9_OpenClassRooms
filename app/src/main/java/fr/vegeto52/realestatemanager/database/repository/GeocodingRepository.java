package fr.vegeto52.realestatemanager.database.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.vegeto52.realestatemanager.BuildConfig;
import fr.vegeto52.realestatemanager.api.GeocodingApi;
import fr.vegeto52.realestatemanager.api.RetrofitService;
import fr.vegeto52.realestatemanager.model.ResultsGeocodingApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vegeto52-PC on 28/12/2023.
 */
public class GeocodingRepository {

    private final static String MAPS_API_KEY = BuildConfig.MAPS_API_KEY;

    private final MutableLiveData<ResultsGeocodingApi> mResultsGeocodingApiMutableLiveData = new MutableLiveData<>();

    public GeocodingRepository() {
    }

    public interface GeocodingCallBack{
        void onGeocodingResult(ResultsGeocodingApi resultsGeocodingApi);
    }

    public void getGeocoding(String address, GeocodingCallBack callBack){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            ResultsGeocodingApi result = getGeocodingMethod(address);
            if (callBack != null){
                callBack.onGeocodingResult(result);
            }
        });
    }

    public LiveData<ResultsGeocodingApi> getGeocodingLiveData(){
        return mResultsGeocodingApiMutableLiveData;
    }

    private ResultsGeocodingApi getGeocodingMethod(String address){
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
