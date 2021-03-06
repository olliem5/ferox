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

@FeroxModule(name = "Xray", description = "only render the blocks you want", category = Category.Render)
public class Xray extends Module {

    /**
     * using public static boolean for xray,
     * because the various checks for xray will be called
     * hundreds of times per second
     */
    public static boolean doXray;

    public Xray() {
        // blocks on xray list will be customizable with commands in a future release
        // for now it is only the essentials

        // ore
        xrayBlocks.add(Blocks.DIAMOND_ORE);
        xrayBlocks.add(Blocks.REDSTONE_ORE);
        xrayBlocks.add(Blocks.GOLD_ORE);
        xrayBlocks.add(Blocks.LAPIS_ORE);
        xrayBlocks.add(Blocks.IRON_ORE);
        xrayBlocks.add(Blocks.QUARTZ_ORE);
        xrayBlocks.add(Blocks.COAL_ORE);
        xrayBlocks.add(Blocks.EMERALD_ORE);
        xrayBlocks.add(Blocks.LIT_REDSTONE_ORE);

        // liquid
        xrayBlocks.add(Blocks.WATER);
        xrayBlocks.add(Blocks.FLOWING_WATER);
        xrayBlocks.add(Blocks.LAVA);
        xrayBlocks.add(Blocks.FLOWING_LAVA);

        // storage
        xrayBlocks.add(Blocks.CHEST);
        xrayBlocks.add(Blocks.ENDER_CHEST);
        xrayBlocks.add(Blocks.TRAPPED_CHEST);
    }

    public static HashSet<Block> xrayBlocks = new HashSet<>();

    @Override
    public void onEnable() {
        doXray = true;

        if (mc.world == null)
            return;

        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        doXray = false;

        if (mc.world == null)
            return;

        mc.renderGlobal.loadRenderers();
    }

}
