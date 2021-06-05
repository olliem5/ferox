package com.feroxclient.fabric.module.modules;

import com.feroxclient.fabric.clickgui.ClickGUI;
import com.feroxclient.fabric.module.Category;
import com.feroxclient.fabric.module.Module;
import com.feroxclient.fabric.module.ModuleManifest;
import org.lwjgl.glfw.GLFW;

@ModuleManifest(name = "ClickGUI", description = "ClickGUI", key = GLFW.GLFW_KEY_P, category = Category.FEROX)
public class ClickGUIModule extends Module {
    ClickGUI clickGUI;

    @Override
    public void onEnable() {
        if(clickGUI == null) clickGUI = new ClickGUI();
        mc.openScreen(clickGUI);
        toggle();
    }
}
