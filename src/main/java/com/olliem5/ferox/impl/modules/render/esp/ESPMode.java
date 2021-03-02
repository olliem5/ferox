package com.olliem5.ferox.impl.modules.render.esp;

import com.olliem5.ferox.api.traits.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author olliem5
 */

public abstract class ESPMode implements Minecraft {
    public void drawESP(RenderWorldLastEvent event) {}
}
