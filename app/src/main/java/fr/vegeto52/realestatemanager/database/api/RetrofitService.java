package fr.vegeto52.realestatemanager.database.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The RetrofitService class provides a singleton instance of the Retrofit client
 * for making HTTP requests to external APIs.
 */
public class RetrofitService {

    private static Retrofit sRetrofit;

    /**
     * Get the singleton instance of the Retrofit client.
     *
     * @return The Retrofit instance.
     */
    public static Retrofit getRetrofitInstance() {

        // Create a logging interceptor for debugging purposes
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Create an OkHttpClient with the logging interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        // Initialize Retrofit if not already instantiated
        if (sRetrofit == null) {
            String BASE_URL = "https://maps.googleapis.com/";
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return sRetrofit;
    }
}
