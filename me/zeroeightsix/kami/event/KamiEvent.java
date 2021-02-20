//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.event;

import me.zero.alpine.type.Cancellable;
import me.zeroeightsix.kami.util.Wrapper;

public class KamiEvent
extends Cancellable {
    private Era era = Era.PRE;
    private final float partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();

    public Era getEra() {
        return this.era;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public static enum Era {
        PRE,
        PERI,
        POST;

    }
}

