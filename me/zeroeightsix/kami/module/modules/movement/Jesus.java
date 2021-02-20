/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.AddCollisionBoxToListEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Module.Info(name="Jesus", description="Allows you to walk on water", category=Module.Category.HIDDEN)
public class Jesus
extends Module {
    private static final AxisAlignedBB WATER_WALK_AA = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.99, 1.0);
    @EventHandler
    Listener<AddCollisionBoxToListEvent> addCollisionBoxToListEventListener = new Listener<AddCollisionBoxToListEvent>(event -> {
        if (Jesus.mc.field_71439_g != null && event.getBlock() instanceof BlockLiquid && (EntityUtil.isDrivenByPlayer(event.getEntity()) || event.getEntity() == Jesus.mc.field_71439_g) && !(event.getEntity() instanceof EntityBoat) && !Jesus.mc.field_71439_g.func_70093_af() && Jesus.mc.field_71439_g.field_70143_R < 3.0f && !EntityUtil.isInWater((Entity)Jesus.mc.field_71439_g) && (EntityUtil.isAboveWater((Entity)Jesus.mc.field_71439_g, false) || EntityUtil.isAboveWater(Jesus.mc.field_71439_g.func_184187_bx(), false)) && Jesus.isAboveBlock((Entity)Jesus.mc.field_71439_g, event.getPos())) {
            AxisAlignedBB axisalignedbb = WATER_WALK_AA.func_186670_a(event.getPos());
            if (event.getEntityBox().func_72326_a(axisalignedbb)) {
                event.getCollidingBoxes().add(axisalignedbb);
            }
            event.cancel();
        }
    }, new Predicate[0]);
    @EventHandler
    Listener<PacketEvent.Send> packetEventSendListener = new Listener<PacketEvent.Send>(event -> {
        int ticks;
        if (event.getEra() == KamiEvent.Era.PRE && event.getPacket() instanceof CPacketPlayer && EntityUtil.isAboveWater((Entity)Jesus.mc.field_71439_g, true) && !EntityUtil.isInWater((Entity)Jesus.mc.field_71439_g) && !Jesus.isAboveLand((Entity)Jesus.mc.field_71439_g) && (ticks = Jesus.mc.field_71439_g.field_70173_aa % 2) == 0) {
            ((CPacketPlayer)event.getPacket()).field_149477_b += 0.02;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        if (!ModuleManager.isModuleEnabled("Freecam") && EntityUtil.isInWater((Entity)Jesus.mc.field_71439_g) && !Jesus.mc.field_71439_g.func_70093_af()) {
            Jesus.mc.field_71439_g.field_70181_x = 0.1;
            if (Jesus.mc.field_71439_g.func_184187_bx() != null && !(Jesus.mc.field_71439_g.func_184187_bx() instanceof EntityBoat)) {
                Jesus.mc.field_71439_g.func_184187_bx().field_70181_x = 0.3;
            }
        }
    }

    private static boolean isAboveLand(Entity entity) {
        if (entity == null) {
            return false;
        }
        double y = entity.field_70163_u - 0.01;
        for (int x = MathHelper.func_76128_c((double)entity.field_70165_t); x < MathHelper.func_76143_f((double)entity.field_70165_t); ++x) {
            for (int z = MathHelper.func_76128_c((double)entity.field_70161_v); z < MathHelper.func_76143_f((double)entity.field_70161_v); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.func_76128_c((double)y), z);
                if (!Wrapper.getWorld().func_180495_p(pos).func_177230_c().func_149730_j(Wrapper.getWorld().func_180495_p(pos))) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean isAboveBlock(Entity entity, BlockPos pos) {
        return entity.field_70163_u >= (double)pos.func_177956_o();
    }
}

