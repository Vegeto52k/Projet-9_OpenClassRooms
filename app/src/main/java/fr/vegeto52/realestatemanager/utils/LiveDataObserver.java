package fr.vegeto52.realestatemanager.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * The LiveDataObserver class provides utility methods for observing LiveData objects.
 */
public class LiveDataObserver {

    /**
     * Observes a LiveData object only once and automatically removes the observer after the first onChanged event.
     *
     * @param liveData The LiveData object to observe.
     * @param observer The Observer to be notified of changes.
     * @param <T>      The type of data held by the LiveData object.
     */
    public static <T> void observeOnce(LiveData<T> liveData, Observer<T> observer) {
        // Observe the LiveData using a temporary Observer
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                // Notify the provided Observer of the change
                observer.onChanged(t);
                // Remove the temporary Observer to ensure it observes only once
                liveData.removeObserver(this);
            }
        });
    }
}

