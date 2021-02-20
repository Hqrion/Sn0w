//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.network.play.server.SPacketSoundEffect
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;

@Module.Info(name="AutoFish", category=Module.Category.PLAYER, description="Automatically catch fish")
public class AutoFish
extends Module {
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (AutoFish.mc.player != null && (AutoFish.mc.player.getHeldItemMainhand().getItem() == Items.FISHING_ROD || AutoFish.mc.player.getHeldItemOffhand().getItem() == Items.FISHING_ROD) && event.getPacket() instanceof SPacketSoundEffect && SoundEvents.ENTITY_BOBBER_SPLASH.equals(((SPacketSoundEffect)event.getPacket()).getSound())) {
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.rightClickMouse();
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.rightClickMouse();
            }).start();
        }
    }, new Predicate[0]);
}

