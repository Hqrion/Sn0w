//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.zeroeightsix.kami.util;

import java.awt.Color;
import java.awt.Font;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import net.minecraft.client.Minecraft;

public class RainbowText {
    static CFontRenderer cFontRenderer = new CFontRenderer(new Font("latolight", 0, 18), true, false);

    public static void renderRainbowWave(String s, Float x, Float y, float bright, boolean cfont) {
        Float updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            String str = s.charAt(i) + "";
            if (cfont) {
                cFontRenderer.drawStringWithShadow(str, updateX.floatValue(), y.floatValue(), RainbowText.effect((long)i * 3500000L, bright, 100).getRGB());
                updateX = Float.valueOf(updateX.floatValue() + (float)cFontRenderer.getStringWidth(str));
                continue;
            }
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, updateX.floatValue(), y.floatValue(), RainbowText.effect((long)i * 3500000L, bright, 100).getRGB());
            updateX = Float.valueOf(updateX.floatValue() + (float)Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i)));
        }
    }

    public static Color effect(long offset, float brightness, int speed) {
        float hue = (float)(System.nanoTime() + offset * (long)speed) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, brightness, 1.0f)), 16);
        Color c = new Color((int)color);
        return new Color((float)c.getRed() / 255.0f, (float)c.getGreen() / 255.0f, (float)c.getBlue() / 255.0f, (float)c.getAlpha() / 255.0f);
    }

    public static void renderChristmasString(String s, Float x, Float y) {
        Float updateX = x;
        for (int i = 0; i < s.length(); ++i) {
            String str = s.charAt(i) + "";
            Color hacker = i % 2 == 0 ? new Color(255, 0, 0) : new Color(0, 155, 0);
            if (hacker == null) continue;
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(str, updateX.floatValue(), y.floatValue(), hacker.getRGB());
            updateX = Float.valueOf(updateX.floatValue() + (float)Minecraft.getMinecraft().fontRenderer.getCharWidth(s.charAt(i)));
        }
    }
}

