/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHandSide
 */
package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.KamiEvent;
import net.minecraft.util.EnumHandSide;

public class TransformSideFirstPersonEvent
extends KamiEvent {
    private final EnumHandSide handSide;

    public TransformSideFirstPersonEvent(EnumHandSide handSide) {
        this.handSide = handSide;
    }

    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}

