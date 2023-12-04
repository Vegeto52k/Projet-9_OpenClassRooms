package fr.vegeto52.realestatemanager.database.room;

import android.net.Uri;

import androidx.room.TypeConverter;

/**
 * Created by Vegeto52-PC on 04/12/2023.
 */
public class Converters {

    @TypeConverter
    public static Uri fromString(String value){
        return value == null ? null : Uri.parse(value);
    }

    @TypeConverter
    public static String uriToString(Uri uri){
        return uri == null ? null : uri.toString();
    }
}
