package fr.vegeto52.realestatemanager.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * Created by Vegeto52-PC on 28/12/2023.
 */
public class LiveDataObserver {

    public static <T> void observeOnce(LiveData<T> liveData, Observer<T> observer) {
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                observer.onChanged(t);
                liveData.removeObserver(this);
            }
        });
    }
}

