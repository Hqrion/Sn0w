//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.zeroeightsix.kami.util;

import net.minecraft.client.Minecraft;

public class DimensionUtils {
    public String biomeName;

    public DimensionUtils() {
        this.biomeName = Minecraft.getMinecraft().world.getBiome(Minecraft.getMinecraft().player.getPosition()).getBiomeName();
    }
}

