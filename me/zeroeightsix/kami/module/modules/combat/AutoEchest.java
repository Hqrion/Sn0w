//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Position
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

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.exploits.NoBreakAnimation;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;

@Module.Info(name="Auto Echest", category=Module.Category.COMBAT)
public class AutoEchest
extends Module {
    private Setting<Mode> mode = this.register(Settings.e("Mode", Mode.FULL));
    private Setting<Boolean> triggerable = this.register(Settings.b("Triggerable", true));
    private Setting<Boolean> autoCenter = this.register(Settings.b("TpCenter", true));
    private Setting<Integer> timeoutTicks = this.register(Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(40).withMaximum(100).withVisibility(b -> this.triggerable.getValue()).build());
    private Setting<Integer> blocksPerTick = this.register(Settings.integerBuilder("BlocksPerTick").withMinimum(1).withValue(8).withMaximum(9).build());
    private Setting<Integer> tickDelay = this.register(Settings.integerBuilder("TickDelay").withMinimum(0).withValue(0).withMaximum(10).build());
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private Setting<Boolean> noGlitchBlocks = this.register(Settings.b("NoGlitchBlocks", true));
    private Setting<Boolean> announceUsage = this.register(Settings.b("AnnounceUsage", true));
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    private int delayStep = 0;
    private int totalTicksRunning = 0;
    private boolean firstRun;
    private boolean isSneaking = false;
    private Vec3d playerPos;
    private BlockPos basePos;

    private void centerPlayer(double x, double y, double z) {
        AutoEchest.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, true));
        AutoEchest.mc.player.setPosition(x, y, z);
    }

    double getDst(Vec3d vec) {
        return this.playerPos.distanceTo(vec);
    }

    @Override
    protected void onEnable() {
        if (AutoEchest.mc.player == null) {
            this.disable();
            return;
        }
        BlockPos centerPos = AutoEchest.mc.player.getPosition();
        this.playerPos = AutoEchest.mc.player.getPositionVector();
        double y = centerPos.getY();
        double x = centerPos.getX();
        double z = centerPos.getZ();
        Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);
        if (this.autoCenter.getValue().booleanValue()) {
            if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                x = (double)centerPos.getX() + 0.5;
                z = (double)centerPos.getZ() + 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                x = (double)centerPos.getX() + 0.5;
                z = (double)centerPos.getZ() - 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                x = (double)centerPos.getX() - 0.5;
                z = (double)centerPos.getZ() - 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                x = (double)centerPos.getX() - 0.5;
                z = (double)centerPos.getZ() + 0.5;
                this.centerPlayer(x, y, z);
            }
        }
        this.firstRun = true;
        this.playerHotbarSlot = AutoEchest.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoEchest] " + ChatFormatting.GREEN.toString() + "ON");
        }
    }

    @Override
    protected void onDisable() {
        if (AutoEchest.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            AutoEchest.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoEchest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEchest.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoEchest] " + ChatFormatting.RED.toString() + "OFF");
        }
    }

    @Override
    public void onUpdate() {
        if (AutoEchest.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.triggerable.getValue().booleanValue() && this.totalTicksRunning >= this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
            return;
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
        }
        if (this.firstRun) {
            this.firstRun = false;
        }
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            Vec3d[] offsetPattern = new Vec3d[]{};
            int maxSteps = 0;
            if (this.mode.getValue().equals((Object)Mode.FULL)) {
                offsetPattern = Offsets.FULL;
                maxSteps = Offsets.FULL.length;
            }
            if (this.mode.getValue().equals((Object)Mode.ECHEST12)) {
                offsetPattern = Offsets.ECHEST12;
                maxSteps = Offsets.ECHEST12.length;
            }
            if (this.offsetStep >= maxSteps) {
                this.offsetStep = 0;
                break;
            }
            BlockPos offsetPos = new BlockPos(offsetPattern[this.offsetStep]);
            BlockPos targetPos = new BlockPos(AutoEchest.mc.player.getPositionVector()).add(offsetPos.x, offsetPos.y, offsetPos.z);
            if (this.placeBlock(targetPos)) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                AutoEchest.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                AutoEchest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEchest.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
        ++this.totalTicksRunning;
    }

    private boolean placeBlock(BlockPos pos) {
        Block block = AutoEchest.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (Entity entity : AutoEchest.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
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
        Block neighbourBlock = AutoEchest.mc.world.getBlockState(neighbour).getBlock();
        int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            AutoEchest.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if (!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            AutoEchest.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoEchest.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        AutoEchest.mc.playerController.processRightClickBlock(AutoEchest.mc.player, AutoEchest.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoEchest.mc.player.swingArm(EnumHand.MAIN_HAND);
        AutoEchest.mc.rightClickDelayTimer = 4;
        if (this.noGlitchBlocks.getValue().booleanValue() && !AutoEchest.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            AutoEchest.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
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
            ItemStack stack = AutoEchest.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockEnderChest)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    private static class Offsets {
        private static final Vec3d[] ECHEST12 = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0)};
        private static final Vec3d[] FULL = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};

        private Offsets() {
        }
    }

    private static enum Mode {
        ECHEST12,
        FULL;

    }
}

