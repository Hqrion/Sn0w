//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.GameType
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.Timeridk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;

@Module.Info(name="Burrow", description="", category=Module.Category.COMBAT)
public class Burrow
extends Module {
    private Setting<Float> delay = this.register(Settings.f("Delay", 1.0f));
    private Setting<Boolean> noGlitchBlocks = this.register(Settings.b("NoGlitchBlocks"));
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate"));
    boolean gamer;
    private long delay2;
    Timeridk timer = new Timeridk();
    int lastHotbarSlot;
    int playerHotbarSlot;
    int timerhacker;
    boolean isSneaking;

    @Override
    public void onEnable() {
        if (Burrow.mc.player == null) {
            this.disable();
            return;
        }
        this.playerHotbarSlot = Burrow.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
        Burrow.mc.player.jump();
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        if (Burrow.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Burrow.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }

    @Override
    public void onUpdate() {
        if (this.timer.hasReached(this.longValue(this.delay.getValue().floatValue()))) {
            BlockPos offsetPos = new BlockPos(0, -1, 0);
            BlockPos targetPos = new BlockPos(Burrow.mc.player.getPositionVector()).add(offsetPos.x, offsetPos.y, offsetPos.z);
            if (this.placeBlock(targetPos)) {
                if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                    Burrow.mc.player.inventory.currentItem = this.playerHotbarSlot;
                    this.lastHotbarSlot = this.playerHotbarSlot;
                }
                if (this.isSneaking) {
                    Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    this.isSneaking = false;
                }
                Burrow.mc.player.onGround = false;
                Burrow.mc.player.motionY = 20.0;
            }
            this.disable();
        }
    }

    public long longValue(float value) {
        return (long)value;
    }

    private boolean placeBlock(BlockPos pos) {
        Block block = Burrow.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (Entity entity : Burrow.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
            return false;
        }
        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            return false;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = Burrow.mc.world.getBlockState(neighbour).getBlock();
        int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            Burrow.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if (!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        Burrow.mc.playerController.processRightClickBlock(Burrow.mc.player, Burrow.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Burrow.mc.player.swingArm(EnumHand.MAIN_HAND);
        Burrow.mc.rightClickDelayTimer = 4;
        if (this.noGlitchBlocks.getValue().booleanValue() && !Burrow.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
        }
        return true;
    }

    public int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Burrow.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            slot = i;
            break;
        }
        return slot;
    }
}

