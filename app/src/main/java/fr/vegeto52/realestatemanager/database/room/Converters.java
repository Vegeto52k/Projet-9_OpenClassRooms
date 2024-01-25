package fr.vegeto52.realestatemanager.database.room;

import android.net.Uri;

import androidx.room.TypeConverter;

/**
 * The Converters class provides type conversion methods for Room database to handle custom data types.
 * In this case, it converts between String and Uri for database storage.
 */
public class Converters {

    /**
     * Convert a String to a Uri.
     *
     * @param value The String to be converted to Uri.
     * @return The converted Uri, or null if the input value is null.
     */
    @TypeConverter
    public static Uri fromString(String value) {
        return value == null ? null : Uri.parse(value);
    }

    /**
     * Convert a Uri to a String.
     *
     * @param uri The Uri to be converted to String.
     * @return The converted String, or null if the input Uri is null.
     */
    @TypeConverter
    public static String uriToString(Uri uri) {
        return uri == null ? null : uri.toString();
    }
}
