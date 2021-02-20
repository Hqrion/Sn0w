//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (this.isDisabled() || Scaffold.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        int oldSlot = Wrapper.getPlayer().inventory.currentItem;
        Vec3d interpol1 = EntityUtil.getInterpolatedPos((Entity)Scaffold.mc.player, 2.0f);
        Vec3d interpol2 = EntityUtil.getInterpolatedPos((Entity)Scaffold.mc.player, 4.0f);
        this.doBlockScaffold(interpol1);
        this.doBlockScaffold(interpol2);
        Wrapper.getPlayer().inventory.currentItem = oldSlot;
    }

    private void doBlockScaffold(Vec3d vec) {
        BlockPos blockPos = new BlockPos(vec).down();
        BlockPos belowBlockPos = blockPos.down();
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || BlockInteractionHelper.blackList.contains(block = ((ItemBlock)stack.getItem()).getBlock()) || block instanceof BlockContainer || !Block.getBlockFromItem((Item)stack.getItem()).getDefaultState().isFullBlock() || ((ItemBlock)stack.getItem()).getBlock() instanceof BlockFalling && Wrapper.getWorld().getBlockState(belowBlockPos).getMaterial().isReplaceable()) continue;
            newSlot = i;
            break;
        }
        if (newSlot == -1) {
            return;
        }
        Wrapper.getPlayer().inventory.currentItem = newSlot;
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            return;
        }
        BlockInteractionHelper.placeBlockScaffold(blockPos);
    }
}

