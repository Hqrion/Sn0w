/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.world.World
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.setting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class wrapper2 {
    private static FontRenderer fontRenderer;
    public static Minecraft mc;

    public static Minecraft getMinecraft() {
        return Minecraft.func_71410_x();
    }

    public static EntityPlayerSP getPlayer() {
        return wrapper2.getMinecraft().field_71439_g;
    }

    public static World getWorld() {
        return wrapper2.getMinecraft().field_71441_e;
    }

    public static int getKey(String keyname) {
        return Keyboard.getKeyIndex((String)keyname.toUpperCase());
    }

    public static FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    static {
        mc = Minecraft.func_71410_x();
    }
}

