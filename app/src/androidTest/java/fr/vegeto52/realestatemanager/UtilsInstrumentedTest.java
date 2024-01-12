package fr.vegeto52.realestatemanager;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Vegeto52-PC on 11/01/2024.
 */
@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {

    @Test
    public void testIsInternetAvailable2() {
        // Obtenir le contexte de l'application via le framework d'instrumentation
        Context context = ApplicationProvider.getApplicationContext();

        // Appeler la méthode isInternetAvailable2
        boolean isInternetAvailable = Utils.isInternetAvailable2(context);

        // Vérifier que le résultat est conforme aux attentes
        assertTrue(isInternetAvailable);
    }
}
