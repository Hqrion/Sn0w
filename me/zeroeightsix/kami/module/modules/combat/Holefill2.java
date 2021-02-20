//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractHelperNew;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.BlockssssUtils;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Holefill2", category=Module.Category.COMBAT)
public class Holefill2
extends Module {
    private int totalTicksRunning = 0;
    private ArrayList<BlockPos> holes = new ArrayList();
    private List<Block> whiteList = Arrays.asList(Blocks.OBSIDIAN);
    String arm;
    BlockPos pos;
    private int waitCounter;
    private Setting<Integer> range = this.register(Settings.integerBuilder("range").withMinimum(1).withValue(4).withMaximum(6).build());
    private Setting<Boolean> chat = this.register(Settings.b("chat", false));
    private Setting<Boolean> rotate = this.register(Settings.b("rotate", false));
    private Setting<Boolean> triggerable = this.register(Settings.b("Triggerable", true));
    private Setting<Boolean> swing = this.register(Settings.b("Swing", false));
    private Setting<Boolean> Echests = this.register(Settings.b("Echests", false));
    private Setting<Boolean> Obsidian = this.register(Settings.b("Obsidian", false));
    private Setting<Integer> timeoutTicks = this.register(Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(1).withMaximum(100).withVisibility(b -> this.triggerable.getValue()).build());

    @Override
    public void onUpdate() {
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (this.holes.isEmpty()) {
            if (this.triggerable.getValue().booleanValue()) {
                this.disable();
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) continue;
            BlockInteractHelperNew.ValidResult result = BlockInteractHelperNew.valid(pos);
            if (result != BlockInteractHelperNew.ValidResult.Ok) {
                this.holes.remove(pos);
                continue;
            }
            pos_to_fill = pos;
            break;
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (pos_to_fill != null && BlockssssUtils.placeBlock(pos_to_fill, this.find_in_hotbar(), this.rotate.getValue(), this.rotate.getValue(), this.swing.getValue())) {
            this.holes.remove(pos_to_fill);
        }
    }

    @Override
    public void onEnable() {
        if (Holefill2.mc.player != null && this.chat.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
        }
        this.find_new_holes();
    }

    @Override
    public void onDisable() {
        Command.toggle_message(this);
    }

    public void find_new_holes() {
        this.holes.clear();
        for (BlockPos pos : BlockInteractionHelper.getSphere(EntityUtil.GetLocalPlayerPosFloored(), this.range.getValue().intValue(), this.range.getValue(), false, true, 0)) {
            if (!Holefill2.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !Holefill2.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !Holefill2.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;
            boolean possible = true;
            for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                Block block = Holefill2.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) continue;
                possible = false;
                break;
            }
            if (!possible) continue;
            this.holes.add(pos);
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Holefill2.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (block instanceof BlockEnderChest && this.Echests.getValue().booleanValue()) {
                return i;
            }
            if (!(block instanceof BlockObsidian) || !this.Obsidian.getValue().booleanValue()) continue;
            return i;
        }
        return -1;
    }
}

