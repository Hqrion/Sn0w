//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (Jesus.mc.player != null && event.getBlock() instanceof BlockLiquid && (EntityUtil.isDrivenByPlayer(event.getEntity()) || event.getEntity() == Jesus.mc.player) && !(event.getEntity() instanceof EntityBoat) && !Jesus.mc.player.isSneaking() && Jesus.mc.player.fallDistance < 3.0f && !EntityUtil.isInWater((Entity)Jesus.mc.player) && (EntityUtil.isAboveWater((Entity)Jesus.mc.player, false) || EntityUtil.isAboveWater(Jesus.mc.player.getRidingEntity(), false)) && Jesus.isAboveBlock((Entity)Jesus.mc.player, event.getPos())) {
            AxisAlignedBB axisalignedbb = WATER_WALK_AA.offset(event.getPos());
            if (event.getEntityBox().intersects(axisalignedbb)) {
                event.getCollidingBoxes().add(axisalignedbb);
            }
            event.cancel();
        }
    }, new Predicate[0]);
    @EventHandler
    Listener<PacketEvent.Send> packetEventSendListener = new Listener<PacketEvent.Send>(event -> {
        int ticks;
        if (event.getEra() == KamiEvent.Era.PRE && event.getPacket() instanceof CPacketPlayer && EntityUtil.isAboveWater((Entity)Jesus.mc.player, true) && !EntityUtil.isInWater((Entity)Jesus.mc.player) && !Jesus.isAboveLand((Entity)Jesus.mc.player) && (ticks = Jesus.mc.player.ticksExisted % 2) == 0) {
            ((CPacketPlayer)event.getPacket()).y += 0.02;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        if (!ModuleManager.isModuleEnabled("Freecam") && EntityUtil.isInWater((Entity)Jesus.mc.player) && !Jesus.mc.player.isSneaking()) {
            Jesus.mc.player.motionY = 0.1;
            if (Jesus.mc.player.getRidingEntity() != null && !(Jesus.mc.player.getRidingEntity() instanceof EntityBoat)) {
                Jesus.mc.player.getRidingEntity().motionY = 0.3;
            }
        }
    }

    private static boolean isAboveLand(Entity entity) {
        if (entity == null) {
            return false;
        }
        double y = entity.posY - 0.01;
        for (int x = MathHelper.floor((double)entity.posX); x < MathHelper.ceil((double)entity.posX); ++x) {
            for (int z = MathHelper.floor((double)entity.posZ); z < MathHelper.ceil((double)entity.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor((double)y), z);
                if (!Wrapper.getWorld().getBlockState(pos).getBlock().isFullBlock(Wrapper.getWorld().getBlockState(pos))) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean isAboveBlock(Entity entity, BlockPos pos) {
        return entity.posY >= (double)pos.getY();
    }
}

