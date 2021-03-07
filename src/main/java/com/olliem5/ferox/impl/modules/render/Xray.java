package com.olliem5.ferox.impl.modules.render;

import com.olliem5.ferox.api.module.Category;
import com.olliem5.ferox.api.module.FeroxModule;
import com.olliem5.ferox.api.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.HashSet;

/**
 * @author Gav06
 */

@FeroxModule(name = "X-Ray", description = "Allows you to cancel the rendering of blocks", category = Category.Render)
public final class Xray extends Module {
    public static boolean doXray;

    public Xray() {
        xrayBlocks.add(Blocks.DIAMOND_ORE);
        xrayBlocks.add(Blocks.REDSTONE_ORE);
        xrayBlocks.add(Blocks.GOLD_ORE);
        xrayBlocks.add(Blocks.LAPIS_ORE);
        xrayBlocks.add(Blocks.IRON_ORE);
        xrayBlocks.add(Blocks.QUARTZ_ORE);
        xrayBlocks.add(Blocks.COAL_ORE);
        xrayBlocks.add(Blocks.EMERALD_ORE);
        xrayBlocks.add(Blocks.LIT_REDSTONE_ORE);

        xrayBlocks.add(Blocks.WATER);
        xrayBlocks.add(Blocks.FLOWING_WATER);
        xrayBlocks.add(Blocks.LAVA);
        xrayBlocks.add(Blocks.FLOWING_LAVA);

        xrayBlocks.add(Blocks.CHEST);
        xrayBlocks.add(Blocks.ENDER_CHEST);
        xrayBlocks.add(Blocks.TRAPPED_CHEST);
    }

    public static HashSet<Block> xrayBlocks = new HashSet<>();

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        doXray = true;

        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        doXray = false;

        mc.renderGlobal.loadRenderers();
    }
}
