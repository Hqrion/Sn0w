/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBed
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.tileentity.TileEntityBed
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BedAuraUtil;
import me.zeroeightsix.kami.util.BlocksUtils;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="AutoBedBomb", category=Module.Category.COMBAT)
public class BedAura
extends Module {
    private BlockPos render;
    private Setting<Boolean> autoSwitch;
    private Setting<Boolean> antiSuicide;
    private Setting<Boolean> announceusage;
    private Setting<Boolean> refill;
    private Setting<Boolean> rotate;
    private Setting<Integer> range;
    private Setting<Integer> switchDelay;
    private Setting<Integer> antiSuicideHlth;
    boolean moving = false;
    BlockPos targetBlock;
    BlockPos targetPlayer;
    BlockPos west;
    BlockPos east;
    BlockPos north;
    BlockPos south;
    EnumFacing facing;
    BlockPos targetPlace;
    BedAuraUtil util = new BedAuraUtil();

    public BedAura() {
        this.range = this.register(Settings.integerBuilder("Range").withMinimum(0).withMaximum(10).withValue(6));
        this.switchDelay = this.register(Settings.integerBuilder("Switch Delay").withMinimum(0).withMaximum(10).withValue(3));
        this.antiSuicideHlth = this.register(Settings.integerBuilder("Anti Suicide Health").withMinimum(0).withMaximum(36).withValue(16).withVisibility(b -> this.antiSuicide.getValue()).build());
        this.announceusage = this.register(Settings.b("Announce Usage", true));
        this.antiSuicide = this.register(Settings.b("AntiSuicide", true));
        this.autoSwitch = this.register(Settings.b("AutoSwitch", true));
        this.refill = this.register(Settings.b("Auto Refill", true));
        this.rotate = this.register(Settings.b("Rotate", true));
    }

    @Override
    public void onEnable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("AutoBedBomb" + ChatFormatting.GREEN.toString() + " Enabled");
        }
    }

    @Override
    public void onDisable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("AutoBedBomb" + ChatFormatting.RED.toString() + " Disabled");
        }
    }

    @Override
    public void onUpdate() {
        BedAura.mc.field_71441_e.field_147482_g.stream().filter(e -> e instanceof TileEntityBed).filter(e -> BedAura.mc.field_71439_g.func_70011_f((double)e.func_174877_v().func_177958_n(), (double)e.func_174877_v().func_177956_o(), (double)e.func_174877_v().func_177952_p()) <= (double)this.range.getValue().intValue()).sorted(Comparator.comparing(e -> BedAura.mc.field_71439_g.func_70011_f((double)e.func_174877_v().func_177958_n(), (double)e.func_174877_v().func_177956_o(), (double)e.func_174877_v().func_177952_p()))).forEach(bed -> {
            if (BedAura.mc.field_71439_g.field_71093_bK == 0) {
                return;
            }
            if (this.rotate.getValue().booleanValue()) {
                BlocksUtils.faceVectorPacketInstant(new Vec3d((Vec3i)bed.func_174877_v().func_177963_a(0.5, 0.5, 0.5)));
            }
            BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(bed.func_174877_v(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        });
        if (this.refill.getValue().booleanValue()) {
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                if (BedAura.mc.field_71439_g.field_71071_by.func_70301_a(i) != ItemStack.field_190927_a) continue;
                slot = i;
                break;
            }
            if (this.moving && slot != -1) {
                BedAura.mc.field_71442_b.func_187098_a(0, slot + 36, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.field_71439_g);
                this.moving = false;
                slot = -1;
            }
            int bedSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = BedAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (!(stack.func_77973_b() instanceof ItemBed)) continue;
                bedSlot = 1;
                break;
            }
            if (slot != -1 && !(BedAura.mc.field_71462_r instanceof GuiContainer) && BedAura.mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (BedAura.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_151104_aV || i < 9) continue;
                    t = i;
                    break;
                }
                if (bedSlot != -1 && BedAura.mc.field_71439_g.field_70173_aa % this.switchDelay.getValue() == 0) {
                    BedAura.mc.field_71439_g.field_71071_by.field_70461_c = bedSlot;
                    BedAura.mc.field_71442_b.func_78765_e();
                }
                if (t != -1) {
                    BedAura.mc.field_71442_b.func_187098_a(0, t, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.field_71439_g);
                    this.moving = true;
                }
            }
        }
        if (BedAura.mc.field_71439_g.func_110143_aJ() < (float)this.antiSuicideHlth.getValue().intValue() && this.antiSuicide.getValue().booleanValue()) {
            this.disable();
        }
        for (EntityPlayer player : this.getTargets()) {
            this.targetPlayer = new BlockPos((int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
            if (!(this.targetPlayer.func_185332_f((int)BedAura.mc.field_71439_g.field_70165_t, (int)BedAura.mc.field_71439_g.field_70163_u, (int)BedAura.mc.field_71439_g.field_70161_v) <= (double)this.range.getValue().intValue())) continue;
            this.west = this.targetPlayer.func_177976_e();
            this.east = this.targetPlayer.func_177974_f();
            this.north = this.targetPlayer.func_177978_c();
            this.south = this.targetPlayer.func_177968_d();
            if (player.func_184613_cA()) {
                this.targetBlock = this.util.beddistance(this.west, this.east, this.north, this.south);
                this.targetBlock = new BlockPos(this.targetBlock.func_177958_n(), this.targetBlock.func_177956_o() - 1, this.targetBlock.func_177952_p());
            } else {
                this.targetBlock = this.util.beddistance(this.west, this.east, this.north, this.south);
            }
            if (this.targetBlock == null) continue;
            if (this.targetBlock == this.west) {
                this.facing = EnumFacing.EAST;
            }
            if (this.targetBlock == this.east) {
                this.facing = EnumFacing.WEST;
            }
            if (this.targetBlock == this.north) {
                this.facing = EnumFacing.SOUTH;
            }
            if (this.targetBlock == this.south) {
                this.facing = EnumFacing.NORTH;
            }
            if (this.facing == null) continue;
            if (this.autoSwitch.getValue().booleanValue()) {
                this.switchHandToItemIfNeed(Items.field_151104_aV);
            }
            if (BedAura.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151104_aV) continue;
            this.targetPlace = new BlockPos(this.targetBlock.func_177958_n(), this.targetBlock.func_177956_o() + 1, this.targetBlock.func_177952_p());
            this.placeBlock(this.targetPlace, this.facing);
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.targetBlock != null) {
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            KamiTessellator.prepare(7);
            KamiTessellator.drawBox(this.targetBlock, 5, 5, 255, 52, 63);
            KamiTessellator.release();
            KamiTessellator.prepare(7);
            KamiTessellator.drawBoundingBoxBlockPos(this.targetBlock, 1.0f, 5, 5, 255, 255);
        }
        KamiTessellator.release();
    }

    public List<EntityPlayer> getTargets() {
        return BedAura.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.func_70005_c_())).sorted(Comparator.comparing(e -> Float.valueOf(BedAura.mc.field_71439_g.func_70032_d((Entity)e)))).collect(Collectors.toList());
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        boolean shouldSneak;
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = this.facing;
        boolean bl = shouldSneak = !BedAura.mc.field_71439_g.func_70093_af();
        if (shouldSneak) {
            BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAura.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        BlocksUtils.faceVectorPacketInstant(hitVec);
        BedAura.mc.field_71442_b.func_187099_a(BedAura.mc.field_71439_g, BedAura.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        BedAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        if (shouldSneak) {
            BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAura.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
    }

    private boolean switchHandToItemIfNeed(Item toItem) {
        if (BedAura.mc.field_71439_g.func_184614_ca().func_77973_b() == toItem || BedAura.mc.field_71439_g.func_184592_cb().func_77973_b() == toItem) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = BedAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || stack.func_77973_b() != toItem) continue;
            BedAura.mc.field_71439_g.field_71071_by.field_70461_c = i;
            BedAura.mc.field_71442_b.func_78765_e();
            return true;
        }
        return true;
    }
}

