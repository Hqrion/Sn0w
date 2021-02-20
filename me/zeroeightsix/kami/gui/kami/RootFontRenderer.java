/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami;

import java.awt.Color;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class RootFontRenderer
implements FontRenderer {
    private final float fontsize;
    private final net.minecraft.client.gui.FontRenderer fontRenderer;

    public RootFontRenderer(float fontsize) {
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
        this.fontsize = fontsize;
    }

    @Override
    public int getFontHeight() {
        return (int)((float)Minecraft.func_71410_x().field_71466_p.field_78288_b * this.fontsize);
    }

    @Override
    public int getStringHeight(String text) {
        return this.getFontHeight();
    }

    @Override
    public int getStringWidth(String text) {
        return (int)((float)this.fontRenderer.func_78256_a(text) * this.fontsize);
    }

    @Override
    public void drawString(int x, int y, String text) {
        this.drawString(x, y, 255, 255, 255, text);
    }

    @Override
    public void drawString(int x, int y, int r, int g, int b, String text) {
        this.drawString(x, y, 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF, text);
    }

    @Override
    public void drawString(int x, int y, Color color, String text) {
        this.drawString(x, y, color.getRGB(), text);
    }

    @Override
    public void drawString(int x, int y, int colour2, String text) {
        this.drawString(x, y, colour2, text, true);
    }

    public void drawString(int x, int y, int colour2, String text, boolean shadow) {
        this.prepare(x, y);
        Minecraft.func_71410_x().field_71466_p.func_175065_a(text, 0.0f, 0.0f, colour2, shadow);
        this.pop(x, y);
    }

    @Override
    public void drawStringWithShadow(int x, int y, int r, int g, int b, String text) {
        this.drawString(x, y, 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF, text, true);
    }

    private void prepare(int x, int y) {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glScalef((float)this.fontsize, (float)this.fontsize, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void pop(int x, int y) {
        GL11.glScalef((float)(1.0f / this.fontsize), (float)(1.0f / this.fontsize), (float)1.0f);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
        GL11.glDisable((int)3553);
    }
}

