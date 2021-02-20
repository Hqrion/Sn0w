//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.util;

import java.util.Arrays;
import java.util.EventListener;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.PacketEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class LagCompensator
implements EventListener {
    public static LagCompensator INSTANCE;
    private final float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate;
    @EventHandler
    Listener<PacketEvent.Receive> packetEventListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            INSTANCE.onTimeUpdate();
        }
    }, new Predicate[0]);

    public LagCompensator() {
        KamiMod.EVENT_BUS.subscribe((Object)this);
        this.reset();
    }

    public void reset() {
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(this.tickRates, 0.0f);
    }

    public float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (float tickRate : this.tickRates) {
            if (!(tickRate > 0.0f)) continue;
            sumTickRates += tickRate;
            numTicks += 1.0f;
        }
        return MathHelper.clamp((float)(sumTickRates / numTicks), (float)0.0f, (float)20.0f);
    }

    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            float timeElapsed = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.clamp((float)(20.0f / timeElapsed), (float)0.0f, (float)20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
}

