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
        if (WebAuraCombined.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = WebAuraCombined.mc.field_71439_g.field_71071_by.field_70461_c;
        this.lastHotbarSlot = -1;
    }

    @Override
    protected void onDisable() {
        if (WebAuraCombined.mc.field_71439_g == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            WebAuraCombined.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            WebAuraCombined.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
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
        if (WebAuraCombined.mc.field_71439_g == null) {
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
            this.lastTickTargetName = this.closestTarget.func_70005_c_();
            if (this.announceUsage.getValue().booleanValue()) {
                Command.sendChatMessage("[WebAuraCombined] " + ChatFormatting.GREEN.toString() + "On" + ChatFormatting.RESET.toString() + ", target: " + this.lastTickTargetName);
            }
        } else if (!this.lastTickTargetName.equals(this.closestTarget.func_70005_c_())) {
            this.lastTickTargetName = this.closestTarget.func_70005_c_();
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
            BlockPos targetPos = new BlockPos(this.closestTarget.func_174791_d()).func_177977_b().func_177982_a(offsetPos.field_177962_a, offsetPos.field_177960_b, offsetPos.field_177961_c);
            if (this.placeBlockInRange(targetPos, this.range.getValue())) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                WebAuraCombined.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                WebAuraCombined.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
    }

    private boolean placeBlockInRange(BlockPos pos, double range) {
        Block block = WebAuraCombined.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            return false;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        Block neighbourBlock = WebAuraCombined.mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
        if (WebAuraCombined.mc.field_71439_g.func_174791_d().func_72438_d(hitVec) > range) {
            return false;
        }
        int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            WebAuraCombined.mc.field_71439_g.field_71071_by.field_70461_c = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if (!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            WebAuraCombined.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WebAuraCombined.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        WebAuraCombined.mc.field_71442_b.func_187099_a(WebAuraCombined.mc.field_71439_g, WebAuraCombined.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        WebAuraCombined.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        WebAuraCombined.mc.field_71467_ac = 4;
        if (this.noGlitchBlocks.getValue().booleanValue() && !WebAuraCombined.mc.field_71442_b.func_178889_l().equals((Object)GameType.CREATIVE)) {
            WebAuraCombined.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
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
            ItemStack stack = WebAuraCombined.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || !((block = ((ItemBlock)stack.func_77973_b()).func_179223_d()) instanceof BlockWeb)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    private void findClosestTarget() {
        List playerList = WebAuraCombined.mc.field_71441_e.field_73010_i;
        this.closestTarget = null;
        for (EntityPlayer target : playerList) {
            if (target == WebAuraCombined.mc.field_71439_g || Friends.isFriend(target.func_70005_c_()) || !EntityUtil.isLiving((Entity)target) || target.func_110143_aJ() <= 0.0f) continue;
            if (this.closestTarget == null) {
                this.closestTarget = target;
                continue;
            }
            if (!(WebAuraCombined.mc.field_71439_g.func_70032_d((Entity)target) < WebAuraCombined.mc.field_71439_g.func_70032_d((Entity)this.closestTarget))) continue;
            this.closestTarget = target;
        }
    }

    @Override
    public String getHudInfo() {
        if (this.closestTarget != null) {
            return this.closestTarget.func_70005_c_().toUpperCase();
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

