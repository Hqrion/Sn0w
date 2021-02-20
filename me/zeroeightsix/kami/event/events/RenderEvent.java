/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.KamiEvent;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class RenderEvent
extends KamiEvent {
    private final Tessellator tessellator;
    private final Vec3d renderPos;

    public RenderEvent(Tessellator tessellator, Vec3d renderPos) {
        this.tessellator = tessellator;
        this.renderPos = renderPos;
    }

    public Tessellator getTessellator() {
        return this.tessellator;
    }

    public BufferBuilder getBuffer() {
        return this.tessellator.func_178180_c();
    }

    public Vec3d getRenderPos() {
        return this.renderPos;
    }

    public void setTranslation(Vec3d translation) {
        this.getBuffer().func_178969_c(-translation.field_72450_a, -translation.field_72448_b, -translation.field_72449_c);
    }

    public void resetTranslation() {
        this.setTranslation(this.renderPos);
    }
}

