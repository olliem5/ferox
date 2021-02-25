package com.olliem5.ferox.impl.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.ferox.api.component.Component;
import com.olliem5.ferox.api.component.FeroxComponent;
import com.olliem5.ferox.api.module.ModuleManager;
import com.olliem5.ferox.api.setting.Setting;
import com.olliem5.ferox.api.util.render.font.FontUtil;
import com.olliem5.ferox.impl.modules.ferox.Colours;

/**
 * @author Manesko
 * @author olliem5
 */

@FeroxComponent(name = "CombatInfo", description = "Displays info on combat modules on screen")
public class PvPInfoComponent extends Component {
    public static final Setting<PvPInfoModes> pvpinfoMode = new Setting<>("Mode", "The way of displaying the PvPInfo", PvPInfoModes.Normal);

    public static final Setting<Boolean> modules = new Setting<>("Modules", "The modules to use for PvPInfo", true);
    public static final Setting<Boolean> antiCrystalCheck = new Setting<>(modules, "AntiCrystal", "Allows AntiCrystal to be used in PvPInfo", false);
    public static final Setting<Boolean> auraCheck = new Setting<>(modules, "Aura", "Allows Aura to be used in PvPInfo", true);
    public static final Setting<Boolean> autoCreeperCheck = new Setting<>(modules, "AutoCreeper", "Allows AutoCreeper to be used in PvPInfo", false);
    public static final Setting<Boolean> autoCrystalCheck = new Setting<>(modules, "AutoCrystal", "Allows AutoCrystal to be used in PvPInfo", true);
    public static final Setting<Boolean> autoTrapCheck = new Setting<>(modules, "AutoTrap", "Allows AutoTrap to be used in PvPInfo", true);
    public static final Setting<Boolean> autoWebCheck = new Setting<>(modules, "AutoWeb", "Allows AutoWeb to be used in PvPInfo", false);
    public static final Setting<Boolean> holeFillCheck = new Setting<>(modules, "HoleFill", "Allows HoleFill to be used in PvPInfo", false);
    public static final Setting<Boolean> offhandCheck = new Setting<>(modules, "Offhand", "Allows Offhand to be used in PvPInfo", true);
    public static final Setting<Boolean> selfTrapCheck = new Setting<>(modules, "SelfTrap", "Allows SelfTrap to be used in PvPInfo", false);
    public static final Setting<Boolean> surroundCheck = new Setting<>(modules, "Surround", "Allows Surround to be used in PvPInfo", true);

    public PvPInfoComponent() {
        this.addSettings(
                pvpinfoMode,
                modules
        );
    }

    @Override
    public void render() {
        String antiCrystal = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "AntiCrystal" + (ModuleManager.getModuleByName("AntiCrystal").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "ANC" + (ModuleManager.getModuleByName("AntiCrystal").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String aura = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "Aura" + (ModuleManager.getModuleByName("Aura").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "AU" + (ModuleManager.getModuleByName("Aura").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String autoCreeper = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "AutoCreeper" + (ModuleManager.getModuleByName("AutoCreeper").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "ATC" + (ModuleManager.getModuleByName("AutoCreeper").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String autoCrystal = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "AutoCrystal" + (ModuleManager.getModuleByName("AutoCrystal").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "AC" + (ModuleManager.getModuleByName("AutoCrystal").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String autoTrap = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "AutoTrap" + (ModuleManager.getModuleByName("AutoTrap").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "AT" + (ModuleManager.getModuleByName("AutoTrap").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String autoWeb = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "AutoWeb" + (ModuleManager.getModuleByName("AutoWeb").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "AW" + (ModuleManager.getModuleByName("AutoWeb").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String holeFill = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "HoleFill" + (ModuleManager.getModuleByName("HoleFill").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "HF" + (ModuleManager.getModuleByName("HoleFill").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String offhand = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "Offhand" + (ModuleManager.getModuleByName("Offhand").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "OF" + (ModuleManager.getModuleByName("Offhand").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String selfTrap = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "SelfTrap" + (ModuleManager.getModuleByName("SelfTrap").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "ST" + (ModuleManager.getModuleByName("SelfTrap").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");
        String surround = pvpinfoMode.getValue() == PvPInfoModes.Normal ? "Surround" + (ModuleManager.getModuleByName("Surround").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF") : "SR" + (ModuleManager.getModuleByName("Surround").isEnabled() ? ChatFormatting.GREEN + " ON" : ChatFormatting.RED + " OFF");

        int boost = 0;

        if (antiCrystalCheck.getValue()) {
            FontUtil.drawText(antiCrystal, this.getX(), this.getY(), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (auraCheck.getValue()) {
            FontUtil.drawText(aura, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (autoCreeperCheck.getValue()) {
            FontUtil.drawText(autoCreeper, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (autoCrystalCheck.getValue()) {
            FontUtil.drawText(autoCrystal, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (autoTrapCheck.getValue()) {
            FontUtil.drawText(autoTrap, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (autoWebCheck.getValue()) {
            FontUtil.drawText(autoWeb, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (holeFillCheck.getValue()) {
            FontUtil.drawText(holeFill, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (offhandCheck.getValue()) {
            FontUtil.drawText(offhand, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (selfTrapCheck.getValue()) {
            FontUtil.drawText(selfTrap, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        if (surroundCheck.getValue()) {
            FontUtil.drawText(surround, this.getX(), this.getY() + (boost * FontUtil.getStringHeight("I am a shit coder lol")), Colours.clientColourPicker.getValue().getRGB());

            boost++;
        }

        //This is the longest value that is possible, I need to make this better lol
        this.setWidth((int) FontUtil.getStringWidth("AutoCrystal OFF"));
        this.setHeight((int) (boost * FontUtil.getStringHeight("I am a shit coder lol")));
    }

    public enum PvPInfoModes {
        Normal,
        Short
    }
}
