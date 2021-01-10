package us.ferox.client.impl.command;

import me.yagel15637.venture.command.AbstractCommand;
import us.ferox.client.api.util.client.MessageUtil;

public class FriendCommand extends AbstractCommand {
    public FriendCommand() {
        super("Adds a player as a friend", "<f/friend> <add/remove> <name>", "f", "friend");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        StringBuilder msg = new StringBuilder();
        for (String s : args) msg.append(s).append(" ");
        MessageUtil.sendClientMessage(msg.toString());
    }
}
