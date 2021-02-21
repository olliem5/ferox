package com.olliem5.ferox.impl.commands;

import com.olliem5.ferox.api.util.client.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;

import java.awt.*;
import java.io.File;

/**
 * @author olliem5
 */

public final class FolderCommand extends AbstractCommand {
    public FolderCommand() {
        super("Opens your config folder", "folder/open", "folder", "open");
    }

    @Override
    public void execute(String[] args) {
        try {
            Desktop.getDesktop().open(new File("ferox"));
        } catch (Exception exception) {
            MessageUtil.sendClientMessage("Opening your config folder failed.");
        }

        MessageUtil.sendClientMessage("Opened your config folder!");
    }
}
