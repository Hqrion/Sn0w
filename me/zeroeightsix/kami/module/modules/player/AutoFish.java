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
        if (AutoFish.mc.field_71439_g != null && (AutoFish.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151112_aM || AutoFish.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151112_aM) && event.getPacket() instanceof SPacketSoundEffect && SoundEvents.field_187609_F.equals(((SPacketSoundEffect)event.getPacket()).func_186978_a())) {
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.func_147121_ag();
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.func_147121_ag();
            }).start();
        }
    }, new Predicate[0]);
}

