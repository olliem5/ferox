package me.olliem5.ferox.api.util.minecraft;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public final class BlockUtil {
    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<>();

        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int) r;

        while ((float) x <= (float) cx + r) {
            int z = cz - (int) r;

            while ((float) z <= (float) cz + r) {
                int y = sphere ? cy - (int) r : cy;

                do {
                    float f = sphere ? (float) cy + r : (float) (cy + h);

                    if (!((float) y < f)) break;

                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);

                    if (!(!(dist < (double) (r * r)) || hollow && dist < (double) ((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }

                    ++y;

                } while (true);

                ++z;
            }

            ++x;
        }

        return circleblocks;
    }
}
