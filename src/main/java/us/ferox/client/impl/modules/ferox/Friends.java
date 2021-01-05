package us.ferox.client.impl.modules.ferox;

import us.ferox.client.api.module.Category;
import us.ferox.client.api.module.Module;
import us.ferox.client.api.module.ModuleInfo;

@ModuleInfo(name = "Friends", description = "Allows for usage of Ferox's friend system", category = Category.FEROX)
public class Friends extends Module {
    public Friends() {
        this.setEnabled(true);
    }
}
