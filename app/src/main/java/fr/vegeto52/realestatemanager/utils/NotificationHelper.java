package fr.vegeto52.realestatemanager.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * The NotificationHelper class provides methods for showing notifications.
 */
public class NotificationHelper {

    // Constants for notification channel
    private static final String CHANNEL_ID = "100";
    private static final String CHANNEL_NAME = "Notification";
    private static final int NOTIFICATION_ID = 1;

    /**
     * Shows a notification with the specified title and message.
     *
     * @param context The context in which the notification should be shown.
     * @param title   The title of the notification.
     * @param message The message content of the notification.
     */
    @SuppressLint("MissingPermission")
    public static void showNotification(Context context, String title, String message) {
        // Create or retrieve the notification channel
        createNotificationChannel(context);

        // Build the notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_add)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get the NotificationManagerCompat and notify with the built notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Creates the notification channel for Android Oreo (API 26) and above.
     *
     * @param context The context in which the notification channel should be created.
     */
    private static void createNotificationChannel(Context context) {
        // Check if the device is running Android Oreo or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            // Get the NotificationManager and create the channel
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
