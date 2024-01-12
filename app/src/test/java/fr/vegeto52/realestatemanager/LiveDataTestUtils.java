package fr.vegeto52.realestatemanager;

import androidx.lifecycle.LiveData;

/**
 * Created by Vegeto52-PC on 10/01/2024.
 */
public class LiveDataTestUtils {

    public static <T> T getValueForTesting(final LiveData<T> liveData) {
        liveData.observeForever(ignored -> {
        });

        return liveData.getValue();
    }
}
