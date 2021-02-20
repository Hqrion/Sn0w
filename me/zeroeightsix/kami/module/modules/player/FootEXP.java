//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
            if (event.getPacket() instanceof CPacketPlayer.Rotation && FootEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                ((CPacketPlayer.Rotation)event.getPacket()).pitch = 90.0f;
            }
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && FootEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                ((CPacketPlayer.PositionRotation)event.getPacket()).pitch = 90.0f;
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

