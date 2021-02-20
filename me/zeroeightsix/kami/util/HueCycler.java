/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.util;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class HueCycler {
    int index = 0;
    int[] cycles;

    public HueCycler(int cycles) {
        if (cycles <= 0) {
            throw new IllegalArgumentException("cycles <= 0");
        }
        this.cycles = new int[cycles];
        double hue = 0.0;
        double add = 1.0 / (double)cycles;
        for (int i = 0; i < cycles; ++i) {
            this.cycles[i] = Color.HSBtoRGB((float)hue, 1.0f, 1.0f);
            hue += add;
        }
    }

    public void reset() {
        this.index = 0;
    }

    public void reset(int index) {
        this.index = index;
    }

    public int next() {
        int a = this.cycles[this.index];
        ++this.index;
        if (this.index >= this.cycles.length) {
            this.index = 0;
        }
        return a;
    }

    public void setNext() {
        int rgb = this.next();
    }

    public void set() {
        int rgb = this.cycles[this.index];
        float red = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float green = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(rgb & 0xFF) / 255.0f;
        GL11.glColor3f((float)red, (float)green, (float)blue);
    }

    public void setNext(float alpha) {
        int rgb = this.next();
        float red = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float green = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(rgb & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public int current() {
        return this.cycles[this.index];
    }
}

