package com.olliem5.ferox.impl.modules.miscellaneous;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.impl.components.TimeComponent;
import com.olliem5.pace.annotation.PaceHandler;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author olliem5
 */

@FeroxModule(name = "ChatTweaks", description = "Tweaks how your chat renders", category = Category.Miscellaneous)
public final class ChatTweaks extends Module {
    public static final Setting<Boolean> clearBackground = new Setting<>("Clear Background", "Does not render the chatbox background", true);

    public static final Setting<Boolean> timestamps = new Setting<>("Timestamps", "Renders the time a message was sent in the chat", true);
    public static final Setting<HourModes> hourMode = new Setting<>(timestamps, "Hour", "How the hour is displayed", HourModes.Twelve);

//    public static final Setting<Boolean> highlight = new Setting<>("Highlight", "Highlights certain things in the chat", true);
//    public static final Setting<Boolean> yourName = new Setting<>(highlight, "Your Name", "Highlights your name in chat", true);
//    public static final Setting<Boolean> friendNames = new Setting<>(highlight, "Friend Names", "Highlights friends names in chat", true);
//    public static final Setting<Boolean> enemyNames = new Setting<>(highlight, "Enemy Names", "Highlights enemy names in chat", true);

//    public static final Setting<Boolean> infiniteScroll = new Setting<>("Infinite Scroll", "Allows you to scroll infinitely up in the chat", true);

    public ChatTweaks() {
        this.addSettings(
                clearBackground,
                timestamps/*,*/
//                highlight,
//                infiniteScroll
        );
    }

    @PaceHandler
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (nullCheck()) return;

        if (timestamps.getValue()) {
            TextComponentString textComponentString = new TextComponentString(ChatFormatting.DARK_PURPLE + "<" + (hourMode.getValue() == HourModes.Twelve ? new SimpleDateFormat("h:mm").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "> " + ChatFormatting.RESET);

            event.setMessage(textComponentString.appendSibling(event.getMessage()));
        }
    }

    public enum HourModes {
        Twelve,
        TwentyFour
    }
}
