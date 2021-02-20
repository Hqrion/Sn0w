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
    Minecraft mc = Minecraft.func_71410_x();

    public BlockPos beddistance(BlockPos one, BlockPos two, BlockPos three, BlockPos four) {
        this.oneB = this.mc.field_71439_g.func_174831_c(one);
        this.twoB = this.mc.field_71439_g.func_174831_c(two);
        this.threeB = this.mc.field_71439_g.func_174831_c(three);
        this.fourB = this.mc.field_71439_g.func_174831_c(four);
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
        BlockPos above = new BlockPos(block.func_177958_n(), block.func_177956_o() + 1, block.func_177952_p());
        IBlockState state = this.mc.field_71441_e.func_180495_p(above);
        return state.func_177230_c() == Blocks.field_150350_a;
    }
}

