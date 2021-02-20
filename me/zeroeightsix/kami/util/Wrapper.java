/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.world.World
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.util;

import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Wrapper {
    private static FontRenderer fontRenderer;

    public static void init() {
        fontRenderer = KamiGUI.fontRenderer;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.func_71410_x();
    }

    public static EntityPlayerSP getPlayer() {
        return Wrapper.getMinecraft().field_71439_g;
    }

    public static World getWorld() {
        return Wrapper.getMinecraft().field_71441_e;
    }

    public static int getKey(String keyname) {
        return Keyboard.getKeyIndex((String)keyname.toUpperCase());
    }

    public static FontRenderer getFontRenderer() {
        return fontRenderer;
    }
}

