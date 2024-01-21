package fr.vegeto52.realestatemanager;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.vegeto52.realestatemanager.utils.Utils;

/**
 * Created by Vegeto52-PC on 11/01/2024.
 */
@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {

    @Test
    public void testIsInternetAvailable2() {
        Context context = ApplicationProvider.getApplicationContext();

        boolean isInternetAvailable = Utils.isInternetAvailable2(context);

        assertTrue(isInternetAvailable);
    }
}
