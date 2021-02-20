//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BedAuraUtil {
    double oneB;
    double twoB;
    double threeB;
    double fourB;
    Minecraft mc = Minecraft.getMinecraft();

    public BlockPos beddistance(BlockPos one, BlockPos two, BlockPos three, BlockPos four) {
        this.oneB = this.mc.player.getDistanceSqToCenter(one);
        this.twoB = this.mc.player.getDistanceSqToCenter(two);
        this.threeB = this.mc.player.getDistanceSqToCenter(three);
        this.fourB = this.mc.player.getDistanceSqToCenter(four);
        if (this.oneB < this.twoB && this.oneB < this.threeB && this.oneB < this.fourB && this.hasObsidianAbove(one)) {
            return one;
        }
        if (this.twoB < this.threeB && this.twoB < this.fourB && this.hasObsidianAbove(two)) {
            return two;
        }
        if (this.threeB < this.fourB && this.hasObsidianAbove(three)) {
            return three;
        }
        if (this.hasObsidianAbove(four)) {
            return four;
        }
        return null;
    }

    public boolean hasObsidianAbove(BlockPos block) {
        BlockPos above = new BlockPos(block.getX(), block.getY() + 1, block.getZ());
        IBlockState state = this.mc.world.getBlockState(above);
        return state.getBlock() == Blocks.AIR;
    }
}

