/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlocksUtils;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

@Module.Info(name="BedAura TEST", category=Module.Category.COMBAT)
public class BedAuraRewrite
extends Module {
    private Setting<Boolean> announceusage;
    private Setting<Boolean> autobedswitch;
    private Setting<Boolean> antiSuicide;
    private Setting<Boolean> refill;
    private Setting<Integer> antiSuicideHlth = this.register(Settings.integerBuilder("Anti Suicide Health").withMinimum(0).withMaximum(36).withValue(16).withVisibility(b -> this.antiSuicide.getValue()).build());
    private Setting<Integer> range = this.register(Settings.integerBuilder("Range").withMinimum(0).withMaximum(10).withValue(6));
    BlockPos target;

    public BedAuraRewrite() {
        this.announceusage = this.register(Settings.b("Announce Usage", false));
        this.autobedswitch = this.register(Settings.b("Auto Switch", false));
        this.antiSuicide = this.register(Settings.b("AntiSuicide", true));
        this.refill = this.register(Settings.b("Refill", false));
    }

    @Override
    public void onUpdate() {
        if (BedAuraRewrite.mc.field_71439_g.func_110143_aJ() < (float)this.antiSuicideHlth.getValue().intValue() && this.antiSuicide.getValue().booleanValue()) {
            this.disable();
        }
        for (EntityPlayer player : this.getTargets()) {
            this.target = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
            if (BedAuraRewrite.mc.field_71439_g.func_184614_ca().func_77973_b() != ItemBlock.func_150899_d((int)355) || BedAuraRewrite.mc.field_71441_e.func_180495_p(this.target.func_177984_a().func_177984_a()) == Blocks.field_150350_a) continue;
            this.placeBlock(this.target.func_177984_a(), EnumFacing.DOWN);
        }
        if (BedAuraRewrite.mc.field_71439_g.field_71093_bK != 0) {
            BedAuraRewrite.mc.field_71441_e.field_147482_g.stream().filter(e -> e instanceof TileEntityBed).filter(e -> BedAuraRewrite.mc.field_71439_g.func_180425_c().func_185332_f(e.func_174877_v().field_177962_a, e.func_174877_v().field_177960_b, e.func_174877_v().field_177961_c) <= (double)this.range.getValue().intValue()).map(entity -> (TileEntityBed)entity).min(Comparator.comparing(e -> BedAuraRewrite.mc.field_71439_g.func_180425_c().func_185332_f(e.func_174877_v().field_177962_a, e.func_174877_v().field_177960_b, e.func_174877_v().field_177961_c))).ifPresent(bed -> BedAuraRewrite.mc.field_71442_b.func_187099_a(BedAuraRewrite.mc.field_71439_g, BedAuraRewrite.mc.field_71441_e, bed.func_174877_v(), EnumFacing.UP, new Vec3d((double)bed.func_174877_v().func_177958_n(), (double)bed.func_174877_v().func_177956_o(), (double)bed.func_174877_v().func_177952_p()), EnumHand.MAIN_HAND));
        } else {
            this.disable();
        }
        if (this.refill.getValue().booleanValue()) {
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                if (BedAuraRewrite.mc.field_71439_g.field_71071_by.func_70301_a(i) != ItemStack.field_190927_a) continue;
                slot = i;
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.autobedswitch.getValue().booleanValue()) {
            this.switchHandToItemIfNeed(ItemBlock.func_150899_d((int)355));
        }
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("Bed Aura Rewrite" + ChatFormatting.BLUE.toString() + " Enabled");
        }
    }

    @Override
    public void onDisable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("Bed Aura Rewrite" + ChatFormatting.BLUE.toString() + " Disabled");
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        boolean shouldSneak;
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        boolean bl = shouldSneak = !BedAuraRewrite.mc.field_71439_g.func_70093_af();
        if (shouldSneak) {
            BedAuraRewrite.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAuraRewrite.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        BlocksUtils.faceVectorPacketInstant(hitVec);
        BedAuraRewrite.mc.field_71442_b.func_187099_a(BedAuraRewrite.mc.field_71439_g, BedAuraRewrite.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        BedAuraRewrite.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        if (shouldSneak) {
            BedAuraRewrite.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAuraRewrite.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        BedAuraRewrite.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
    }

    public List<EntityPlayer> getTargets() {
        return BedAuraRewrite.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.func_70005_c_())).sorted(Comparator.comparing(e -> Float.valueOf(BedAuraRewrite.mc.field_71439_g.func_70032_d((Entity)e)))).collect(Collectors.toList());
    }

    private boolean switchHandToItemIfNeed(Item toItem) {
        if (BedAuraRewrite.mc.field_71439_g.func_184614_ca().func_77973_b() == toItem || BedAuraRewrite.mc.field_71439_g.func_184592_cb().func_77973_b() == toItem) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = BedAuraRewrite.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || stack.func_77973_b() != toItem) continue;
            BedAuraRewrite.mc.field_71439_g.field_71071_by.field_70461_c = i;
            BedAuraRewrite.mc.field_71442_b.func_78765_e();
            return true;
        }
        return true;
    }
}

