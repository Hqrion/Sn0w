/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render.font;

import java.awt.Color;

public interface FontRenderer {
    public int getFontHeight();

    public int getStringHeight(String var1);

    public int getStringWidth(String var1);

    public void drawString(int var1, int var2, String var3);

    public void drawString(int var1, int var2, int var3, int var4, int var5, String var6);

    public void drawString(int var1, int var2, Color var3, String var4);

    public void drawString(int var1, int var2, int var3, String var4);

    public void drawStringWithShadow(int var1, int var2, int var3, int var4, int var5, String var6);
}

