/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(name="FootEXP", category=Module.Category.PLAYER)
public class FootEXP
extends Module {
    @EventHandler
    public Listener<PacketEvent.Send> sendPacket = new Listener<PacketEvent.Send>(event -> {
        if (event.getEra() == KamiEvent.Era.PRE) {
            if (event.getPacket() instanceof CPacketPlayer.Rotation && FootEXP.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle) {
                ((CPacketPlayer.Rotation)event.getPacket()).field_149473_f = 90.0f;
            }
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && FootEXP.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle) {
                ((CPacketPlayer.PositionRotation)event.getPacket()).field_149473_f = 90.0f;
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
}

