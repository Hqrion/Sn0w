/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.event;

import me.zero.alpine.type.Cancellable;
import me.zeroeightsix.kami.util.Wrapper;

public class KamiEvent
extends Cancellable {
    private Era era = Era.PRE;
    private final float partialTicks = Wrapper.getMinecraft().func_184121_ak();

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

