package me.olliem5.ferox.api.notification;

import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class NotificationManager {
    public static final ArrayList<Notification> notifications = new ArrayList<>();

    public static void queueNotification(Notification notification) {
        notifications.add(notification);
    }

    public static ArrayList<Notification> getNotifications() {
        return notifications;
    }
}
