package us.ferox.client.impl.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.yagel15637.venture.command.AbstractCommand;
import us.ferox.client.Ferox;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.util.client.MessageUtil;

public class ToggleCommand extends AbstractCommand {
    public ToggleCommand() {
        super("Toggles a specified module", "<t/toggle> <module name>", "t", "toggle");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1)  {
            MessageUtil.sendClientMessage("Syntax : " + ChatFormatting.RED + "<t/toggle> <module name>");
        }
            for (Module module : Ferox.moduleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(args[1])) {
                    System.out.println("jil;sjdfkl;sjf;akl");
                    module.toggle();

                    if (module.isEnabled()) {
                        MessageUtil.sendClientMessage("Module " + ChatFormatting.AQUA + module.getName() + ChatFormatting.WHITE + " has been " + ChatFormatting.GREEN + "ENABLED");
                    } else {
                        MessageUtil.sendClientMessage("Module " + ChatFormatting.AQUA + module.getName() + ChatFormatting.WHITE + " has been " + ChatFormatting.RED + "DISABLED");
                    }
                }
            }
        }

}
