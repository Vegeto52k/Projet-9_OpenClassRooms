package fr.vegeto52.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */
public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * <p>
     * //     * @param dollars
     *
     * @return
     */
//    public static int convertDollarToEuro(int dollars) {
//        return (int) Math.round(dollars * 0.812);
//    }
    public static int convertEurotoDollar(int euros) {
        return (int) Math.round(euros / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
//    public static String getTodayDate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return dateFormat.format(new Date());
//    }
    public static String getTodayDate2() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
//    public static Boolean isInternetAvailable(Context context) {
//        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        return wifi.isWifiEnabled();
//    }
    public static Boolean isInternetAvailable2(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
