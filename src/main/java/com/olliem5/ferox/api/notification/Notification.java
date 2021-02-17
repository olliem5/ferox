package com.olliem5.ferox.api.notification;

import com.olliem5.ferox.api.traits.Minecraft;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * @author olliem5
 */

public final class Notification implements Minecraft {
    private final String title;
    private final String message;
    private final NotificationType notificationType;

    private final int width = 200;
    private final int height = 30;

    private final ResourceLocation infoIcon = new ResourceLocation("ferox", "images/info_icon.png");
    private final ResourceLocation warningIcon = new ResourceLocation("ferox", "images/warning_icon.png");
    private final ResourceLocation normalIcon = new ResourceLocation("ferox", "images/normal_icon.png");

    public Notification(String title, String message, NotificationType notificationType) {
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
    }

    public void renderNotification(int x, int y) {
        Gui.drawRect(x, y, x + width, y + height, new Color(20, 20, 20, 100).getRGB());

        switch (notificationType) {
            case Info:
                mc.getTextureManager().bindTexture(infoIcon);
                Gui.drawModalRectWithCustomSizedTexture(x + 2, y + 2, 0, 0, 26, 26, 26, 26);
                break;
            case Warning:
                mc.getTextureManager().bindTexture(warningIcon);
                Gui.drawModalRectWithCustomSizedTexture(x + 2, y + 2, 0, 0, 26, 26, 26, 26);
                break;
            case Normal:
                mc.getTextureManager().bindTexture(normalIcon);
                Gui.drawModalRectWithCustomSizedTexture(x + 2, y + 2, 0, 0, 26, 26, 26, 26);
                break;
        }

        FontUtil.drawText(title, x + 30, y + 2, -1);
        FontUtil.drawText(message, x + 30, y + 28 - FontUtil.getFontHeight(), -1);
    }

    public enum NotificationType {
        Info,
        Warning,
        Normal
    }
}
