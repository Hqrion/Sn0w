//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockWeb
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.GameType
 */
package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.exploits.NoBreakAnimation;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;

@Module.Info(name="Web Trap", category=Module.Category.HIDDEN)
public class WebAuraCombined
extends Module {
    private Setting<Double> range = this.register(Settings.doubleBuilder("Range").withMinimum(3.5).withValue(6.0).withMaximum(32.0).build());
    private Setting<Integer> blocksPerTick = this.register(Settings.integerBuilder("BlocksPerTick").withMinimum(1).withValue(2).withMaximum(23).build());
    private Setting<Integer> tickDelay = this.register(Settings.integerBuilder("TickDelay").withMinimum(0).withValue(0).withMaximum(10).build());
    private Setting<Cage> cage = this.register(Settings.e("Cage", Cage.FULL));
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private Setting<Boolean> noGlitchBlocks = this.register(Settings.b("NoGlitchBlocks", true));
    private Setting<Boolean> activeInFreecam = this.register(Settings.b("ActiveInFreecam", true));
    private Setting<Boolean> announceUsage = this.register(Settings.b("AnnounceUsage", true));
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int delayStep = 0;
    private boolean isSneaking = false;
    private int offsetStep = 0;
    private boolean firstRun;

    @Override
    protected void onEnable() {
        if (WebAuraCombined.mc.player == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = WebAuraCombined.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }

    @Override
    protected void onDisable() {
        if (WebAuraCombined.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            WebAuraCombined.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            WebAuraCombined.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue().booleanValue()) {
            Command.sendChatMessage("[WebAuraCombined] " + ChatFormatting.RED.toString() + "Off" + ChatFormatting.RESET.toString() + "!");
        }
    }

    @Override
    public void onUpdate() {
        if (WebAuraCombined.mc.player == null) {
            return;
        }
        if (!this.activeInFreecam.getValue().booleanValue() && ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
        }
        this.findClosestTarget();
        if (this.closestTarget == null) {
            if (this.firstRun) {
                this.firstRun = false;
                if (this.announceUsage.getValue().booleanValue()) {
                    Command.sendChatMessage("[WebAuraCombined] " + ChatFormatting.GREEN.toString() + "On" + ChatFormatting.RESET.toString() + ", waiting for target.");
                }
            }
            return;
        }
        if (this.firstRun) {
            this.firstRun = false;
            this.lastTickTargetName = this.closestTarget.getName();
            if (this.announceUsage.getValue().booleanValue()) {
                Command.sendChatMessage("[WebAuraCombined] " + ChatFormatting.GREEN.toString() + "On" + ChatFormatting.RESET.toString() + ", target: " + this.lastTickTargetName);
            }
        } else if (!this.lastTickTargetName.equals(this.closestTarget.getName())) {
            this.lastTickTargetName = this.closestTarget.getName();
            this.offsetStep = 0;
            if (this.announceUsage.getValue().booleanValue()) {
                Command.sendChatMessage("[WebAuraCombined] New target: " + this.lastTickTargetName);
            }
        }
        ArrayList placeTargets = new ArrayList();
        if (this.cage.getValue().equals((Object)Cage.FULL)) {
            Collections.addAll(placeTargets, Offsets.FULL);
        }
        if (this.cage.getValue().equals((Object)Cage.FEET)) {
            Collections.addAll(placeTargets, Offsets.FEET);
        }
        if (this.cage.getValue().equals((Object)Cage.BODY)) {
            Collections.addAll(placeTargets, Offsets.BODY);
        }
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= placeTargets.size()) {
                this.offsetStep = 0;
                break;
            }
            BlockPos offsetPos = new BlockPos((Vec3d)placeTargets.get(this.offsetStep));
            BlockPos targetPos = new BlockPos(this.closestTarget.getPositionVector()).down().add(offsetPos.x, offsetPos.y, offsetPos.z);
            if (this.placeBlockInRange(targetPos, this.range.getValue())) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                WebAuraCombined.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                WebAuraCombined.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
    }

    private boolean placeBlockInRange(BlockPos pos, double range) {
        Block block = WebAuraCombined.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
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
        Block neighbourBlock = WebAuraCombined.mc.world.getBlockState(neighbour).getBlock();
        if (WebAuraCombined.mc.player.getPositionVector().distanceTo(hitVec) > range) {
            return false;
        }
        int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            WebAuraCombined.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if (!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            WebAuraCombined.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        WebAuraCombined.mc.playerController.processRightClickBlock(WebAuraCombined.mc.player, WebAuraCombined.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        WebAuraCombined.mc.player.swingArm(EnumHand.MAIN_HAND);
        WebAuraCombined.mc.rightClickDelayTimer = 4;
        if (this.noGlitchBlocks.getValue().booleanValue() && !WebAuraCombined.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            WebAuraCombined.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
            if (ModuleManager.getModuleByName("NoBreakAnimation").isEnabled()) {
                ((NoBreakAnimation)ModuleManager.getModuleByName("NoBreakAnimation")).resetMining();
            }
        }
        return true;
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = WebAuraCombined.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockWeb)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    private void findClosestTarget() {
        List playerList = WebAuraCombined.mc.world.playerEntities;
        this.closestTarget = null;
        for (EntityPlayer target : playerList) {
            if (target == WebAuraCombined.mc.player || Friends.isFriend(target.getName()) || !EntityUtil.isLiving((Entity)target) || target.getHealth() <= 0.0f) continue;
            if (this.closestTarget == null) {
                this.closestTarget = target;
                continue;
            }
            if (!(WebAuraCombined.mc.player.getDistance((Entity)target) < WebAuraCombined.mc.player.getDistance((Entity)this.closestTarget))) continue;
            this.closestTarget = target;
        }
    }

    @Override
    public String getHudInfo() {
        if (this.closestTarget != null) {
            return this.closestTarget.getName().toUpperCase();
        }
        return "NO TARGET";
    }

    private static class Offsets {
        private static final Vec3d[] FULL = new Vec3d[]{new Vec3d(0.0, 2.0, 0.0), new Vec3d(0.0, 1.0, 0.0)};
        private static final Vec3d[] FEET = new Vec3d[]{new Vec3d(0.0, 1.0, 0.0)};
        private static final Vec3d[] BODY = new Vec3d[]{new Vec3d(0.0, 2.0, 0.0)};

        private Offsets() {
        }
    }

    private static enum Cage {
        FULL,
        FEET,
        BODY;

    }
}

