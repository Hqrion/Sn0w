/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.zeroeightsix.kami.util;

import java.awt.Color;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Rainbow {
    public static Rainbow INSTANCE;
    float hue = 0.0f;
    Color c;
    int rgb;
    int speed = 10;

    public Rainbow() {
        INSTANCE = this;
    }

    public int getRgb() {
        return this.rgb;
    }

    public Color getC() {
        return this.c;
    }

    public void setRainbowSpeed(int s) {
        this.speed = s;
    }

    public int getRainbowSpeed() {
        return this.speed;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        this.c = Color.getHSBColor(this.hue, 1.0f, 1.0f);
        this.rgb = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        this.hue += (float)this.speed / 2000.0f;
    }
}

