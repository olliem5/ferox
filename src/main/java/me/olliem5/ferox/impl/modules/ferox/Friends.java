package me.olliem5.ferox.impl.modules.ferox;

import me.olliem5.ferox.api.module.Category;
import me.olliem5.ferox.api.module.Module;
import me.olliem5.ferox.api.module.ModuleInfo;

@ModuleInfo(name = "Friends", description = "Allows for usage of Ferox's friend system", category = Category.FEROX)
public class Friends extends Module {
    public Friends() {
        this.setEnabled(true);
    }
}
