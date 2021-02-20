/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.BlockFalling
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(name="Scaffold", category=Module.Category.HIDDEN)
public class Scaffold
extends Module {
    @Override
    public void onUpdate() {
        if (this.isDisabled() || Scaffold.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        int oldSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
        Vec3d interpol1 = EntityUtil.getInterpolatedPos((Entity)Scaffold.mc.field_71439_g, 2.0f);
        Vec3d interpol2 = EntityUtil.getInterpolatedPos((Entity)Scaffold.mc.field_71439_g, 4.0f);
        this.doBlockScaffold(interpol1);
        this.doBlockScaffold(interpol2);
        Wrapper.getPlayer().field_71071_by.field_70461_c = oldSlot;
    }

    private void doBlockScaffold(Vec3d vec) {
        BlockPos blockPos = new BlockPos(vec).func_177977_b();
        BlockPos belowBlockPos = blockPos.func_177977_b();
        if (!Wrapper.getWorld().func_180495_p(blockPos).func_185904_a().func_76222_j()) {
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Wrapper.getPlayer().field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || BlockInteractionHelper.blackList.contains(block = ((ItemBlock)stack.func_77973_b()).func_179223_d()) || block instanceof BlockContainer || !Block.func_149634_a((Item)stack.func_77973_b()).func_176223_P().func_185913_b() || ((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof BlockFalling && Wrapper.getWorld().func_180495_p(belowBlockPos).func_185904_a().func_76222_j()) continue;
            newSlot = i;
            break;
        }
        if (newSlot == -1) {
            return;
        }
        Wrapper.getPlayer().field_71071_by.field_70461_c = newSlot;
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            return;
        }
        BlockInteractionHelper.placeBlockScaffold(blockPos);
    }
}

