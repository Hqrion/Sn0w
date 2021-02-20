/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.KamiEvent;
import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent
extends KamiEvent {
    BlockPos pos;

    public DestroyBlockEvent(BlockPos blockPos) {
        this.pos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }
}

