/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.GuiConnecting
 *  net.minecraft.client.multiplayer.ServerData
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

@Module.Info(name="AutoReconnect", description="Automatically reconnects after being disconnected", category=Module.Category.MISC, alwaysListening=true)
public class AutoReconnect
extends Module {
    private Setting<Integer> seconds = this.register(Settings.integerBuilder("Seconds").withValue(5).withMinimum(0).build());
    private static ServerData cServer;
    @EventHandler
    public Listener<GuiScreenEvent.Closed> closedListener = new Listener<GuiScreenEvent.Closed>(event -> {
        if (event.getScreen() instanceof GuiConnecting) {
            cServer = AutoReconnect.mc.field_71422_O;
        }
    }, new Predicate[0]);
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> displayedListener = new Listener<GuiScreenEvent.Displayed>(event -> {
        if (this.isEnabled() && event.getScreen() instanceof GuiDisconnected && (cServer != null || AutoReconnect.mc.field_71422_O != null)) {
            event.setScreen((GuiScreen)new KamiGuiDisconnected((GuiDisconnected)event.getScreen()));
        }
    }, new Predicate[0]);

    private class KamiGuiDisconnected
    extends GuiDisconnected {
        int millis;
        long cTime;

        public KamiGuiDisconnected(GuiDisconnected disconnected) {
            super(disconnected.field_146307_h, disconnected.field_146306_a, disconnected.field_146304_f);
            this.millis = (Integer)AutoReconnect.this.seconds.getValue() * 1000;
            this.cTime = System.currentTimeMillis();
        }

        public void func_73876_c() {
            if (this.millis <= 0) {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiConnecting(this.field_146307_h, this.field_146297_k, cServer == null ? this.field_146297_k.field_71422_O : cServer));
            }
        }

        public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
            super.func_73863_a(mouseX, mouseY, partialTicks);
            long a = System.currentTimeMillis();
            this.millis = (int)((long)this.millis - (a - this.cTime));
            this.cTime = a;
            String s = "Reconnecting in " + Math.max(0.0, Math.floor((double)this.millis / 100.0) / 10.0) + "s";
            this.field_146289_q.func_175065_a(s, (float)(this.field_146294_l / 2 - this.field_146289_q.func_78256_a(s) / 2), (float)(this.field_146295_m - 16), 0xFFFFFF, true);
        }
    }
}

