/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBow
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="SpeedHelper", description="Lets u shoot urself with speed ez", category=Module.Category.PLAYER)
public class SpeedHelper
extends Module {
    @EventHandler
    public Listener<PacketEvent.Send> sendPacket = new Listener<PacketEvent.Send>(event -> {
        if (event.getEra() == KamiEvent.Era.PRE) {
            if (event.getPacket() instanceof CPacketPlayer.Rotation && SpeedHelper.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow) {
                ((CPacketPlayer.Rotation)event.getPacket()).field_149473_f = 5.0f;
            }
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && SpeedHelper.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBow) {
                ((CPacketPlayer.PositionRotation)event.getPacket()).field_149473_f = 5.0f;
            }
        }
    }, new Predicate[0]);

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (SpeedHelper.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && SpeedHelper.mc.field_71439_g.func_184587_cr() && SpeedHelper.mc.field_71439_g.func_184612_cw() >= 4) {
            SpeedHelper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, SpeedHelper.mc.field_71439_g.func_174811_aO()));
            SpeedHelper.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(SpeedHelper.mc.field_71439_g.func_184600_cs()));
            SpeedHelper.mc.field_71439_g.func_184597_cx();
        }
    }
}

