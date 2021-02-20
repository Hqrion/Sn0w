//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@Module.Info(name="AntiForceLook", category=Module.Category.HIDDEN)
public class AntiForceLook
extends Module {
    @EventHandler
    Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            packet.yaw = AntiForceLook.mc.player.rotationYaw;
            packet.pitch = AntiForceLook.mc.player.rotationPitch;
        }
    }, new Predicate[0]);
}

