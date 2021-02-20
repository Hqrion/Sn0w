/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Auto 32k Dispenser", category=Module.Category.COMBAT, description="Do not use with any AntiGhostBlock Mod!")
public class DispenserMeta
extends Module {
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", false));
    private Setting<Boolean> grabItem = this.register(Settings.b("Grab Item", false));
    private Setting<Boolean> autoEnableHitAura = this.register(Settings.b("Auto enable Hit Aura", false));
    private Setting<Boolean> autoEnableBypass = this.register(Settings.b("Auto enable Secret Close", false));
    private Setting<Boolean> SideRedstone = this.register(Settings.b("Redstone On Side", false));
    private Setting<Boolean> debugMessages = this.register(Settings.b("Debug Messages", false));
    private int stage;
    private BlockPos placeTarget;
    private BlockPos redstonetarget;
    private int obiSlot;
    private int dispenserSlot;
    private int shulkerSlot;
    private int redstoneSlot;
    private int hopperSlot;
    private boolean isSneaking;

    @Override
    protected void onEnable() {
        if (DispenserMeta.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            this.disable();
            return;
        }
        df.setRoundingMode(RoundingMode.CEILING);
        this.stage = 0;
        this.placeTarget = null;
        this.obiSlot = -1;
        this.dispenserSlot = -1;
        this.shulkerSlot = -1;
        this.redstoneSlot = -1;
        this.hopperSlot = -1;
        this.isSneaking = false;
        for (int i = 0; i < 9 && (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1); ++i) {
            ItemStack stack = DispenserMeta.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block == Blocks.field_150438_bZ) {
                this.hopperSlot = i;
                continue;
            }
            if (BlockInteractionHelper.shulkerList.contains(block)) {
                this.shulkerSlot = i;
                continue;
            }
            if (block == Blocks.field_150343_Z) {
                this.obiSlot = i;
                continue;
            }
            if (block == Blocks.field_150367_z) {
                this.dispenserSlot = i;
                continue;
            }
            if (block != Blocks.field_150451_bX) continue;
            this.redstoneSlot = i;
        }
        if (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[Auto32k] Items missing, disabling.");
            }
            this.disable();
            return;
        }
        if (DispenserMeta.mc.field_71476_x == null || DispenserMeta.mc.field_71476_x.func_178782_a() == null || DispenserMeta.mc.field_71476_x.func_178782_a().func_177984_a() == null) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[Auto32k] Not a valid place target, disabling.");
            }
            this.disable();
            return;
        }
        this.placeTarget = DispenserMeta.mc.field_71476_x.func_178782_a().func_177984_a();
        if (DispenserMeta.mc.field_71439_g.func_174811_aO().equals((Object)EnumFacing.NORTH)) {
            this.redstonetarget = this.placeTarget.func_177984_a().func_177984_a().func_177974_f();
        }
        if (DispenserMeta.mc.field_71439_g.func_174811_aO().equals((Object)EnumFacing.WEST)) {
            this.redstonetarget = this.placeTarget.func_177984_a().func_177984_a().func_177978_c();
        }
        if (DispenserMeta.mc.field_71439_g.func_174811_aO().equals((Object)EnumFacing.EAST)) {
            this.redstonetarget = this.placeTarget.func_177984_a().func_177984_a().func_177968_d();
        }
        if (DispenserMeta.mc.field_71439_g.func_174811_aO().equals((Object)EnumFacing.SOUTH)) {
            this.redstonetarget = this.placeTarget.func_177984_a().func_177984_a().func_177976_e();
        }
        if (this.autoEnableBypass.getValue().booleanValue()) {
            ModuleManager.getModuleByName("Secret Close").enable();
        }
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[Auto32k] Place Target: " + this.placeTarget.field_177962_a + " " + this.placeTarget.field_177960_b + " " + this.placeTarget.field_177961_c + " Distance: " + df.format(DispenserMeta.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)this.placeTarget))));
        }
    }

    @Override
    public void onUpdate() {
        if (DispenserMeta.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.stage == 0) {
            DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c = this.obiSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c = this.dispenserSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.func_177982_a(0, 1, 0)), EnumFacing.DOWN);
            DispenserMeta.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            DispenserMeta.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.func_177982_a(0, 1, 0), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.stage = 1;
            return;
        }
        if (this.stage == 1) {
            if (!(DispenserMeta.mc.field_71462_r instanceof GuiContainer)) {
                return;
            }
            DispenserMeta.mc.field_71442_b.func_187098_a(DispenserMeta.mc.field_71439_g.field_71070_bA.field_75152_c, 1, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)DispenserMeta.mc.field_71439_g);
            DispenserMeta.mc.field_71439_g.func_71053_j();
            DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c = this.redstoneSlot;
            if (this.SideRedstone.getValue().booleanValue()) {
                this.placeBlock(new BlockPos((Vec3i)this.redstonetarget), EnumFacing.DOWN);
            } else {
                this.placeBlock(new BlockPos((Vec3i)this.placeTarget.func_177982_a(0, 2, 0)), EnumFacing.DOWN);
            }
            this.stage = 2;
            return;
        }
        if (this.stage == 2) {
            Block block = DispenserMeta.mc.field_71441_e.func_180495_p(this.placeTarget.func_177972_a(DispenserMeta.mc.field_71439_g.func_174811_aO().func_176734_d()).func_177984_a()).func_177230_c();
            if (block instanceof BlockAir || block instanceof BlockLiquid) {
                return;
            }
            DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c = this.hopperSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.func_177972_a(DispenserMeta.mc.field_71439_g.func_174811_aO().func_176734_d())), DispenserMeta.mc.field_71439_g.func_174811_aO());
            DispenserMeta.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            DispenserMeta.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.func_177972_a(DispenserMeta.mc.field_71439_g.func_174811_aO().func_176734_d()), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c = this.shulkerSlot;
            if (!this.grabItem.getValue().booleanValue()) {
                this.disable();
                return;
            }
            this.stage = 3;
            return;
        }
        if (this.stage == 3) {
            if (!(DispenserMeta.mc.field_71462_r instanceof GuiContainer)) {
                return;
            }
            if (((GuiContainer)DispenserMeta.mc.field_71462_r).field_147002_h.func_75139_a((int)0).func_75211_c().field_190928_g) {
                return;
            }
            DispenserMeta.mc.field_71442_b.func_187098_a(DispenserMeta.mc.field_71439_g.field_71070_bA.field_75152_c, 0, DispenserMeta.mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, (EntityPlayer)DispenserMeta.mc.field_71439_g);
            if (this.autoEnableHitAura.getValue().booleanValue()) {
                ModuleManager.getModuleByName("Aura").enable();
            }
            this.disable();
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        if (!this.isSneaking) {
            DispenserMeta.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        DispenserMeta.mc.field_71442_b.func_187099_a(DispenserMeta.mc.field_71439_g, DispenserMeta.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        DispenserMeta.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
}

