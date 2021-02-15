package me.olliem5.ferox.impl.components;

import me.olliem5.ferox.api.component.Component;
import me.olliem5.ferox.api.component.FeroxComponent;
import me.olliem5.ferox.api.notification.Notification;
import me.olliem5.ferox.api.notification.NotificationManager;
import me.olliem5.ferox.impl.gui.screens.editor.HUDEditorScreen;

@FeroxComponent(name = "Notifications", description = "Shows client notifications for events set in the 'Notifications' module")
public final class NotificationComponent extends Component {
    public NotificationComponent() {
        this.setWidth(200);
        this.setHeight(30);
    }

    @Override
    public void render() {
        if (mc.currentScreen instanceof HUDEditorScreen) {
            Notification notification = new Notification("Example Notification", "This is an example notification!", Notification.NotificationType.Normal);
            notification.renderNotification(getPosX(), getPosY());
        } else {
            int boost = 0;

            for (Notification notification : NotificationManager.getNotifications()) {
                notification.renderNotification(getPosX(), getPosY() + boost * 32);

                boost++;
            }
        }
    }
}
