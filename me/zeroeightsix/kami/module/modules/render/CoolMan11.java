//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.zeroeightsix.kami.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.Random;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.RainbowText;
import me.zeroeightsix.kami.util.Wrapper;

@Module.Info(name="Coord Display", category=Module.Category.GUI)
public class CoolMan11
extends Module {
    private Setting<Float> x;
    private Setting<Float> y;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Boolean> customfontlol = this.register(Settings.b("Custom Font", false));
    private Setting<Boolean> Rainbow = this.register(Settings.b("Rainbow", false));
    Random r;
    ZonedDateTime time;
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    private int counter = 0;
    DecimalFormat df;

    public CoolMan11() {
        this.x = this.register(Settings.f("X", 400.0f));
        this.y = this.register(Settings.f("Y", 0.0f));
        this.r = new Random();
        this.time = ZonedDateTime.now();
        this.red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(143).build());
        this.green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
        this.blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
        this.df = new DecimalFormat("#.#");
    }

    @Override
    public void onRender() {
        int color;
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int drgb = color = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        this.setDrawn(false);
        float xPos = this.x.getValue().floatValue();
        boolean inHell = CoolMan11.mc.world.getBiome(CoolMan11.mc.player.getPosition()).getBiomeName().equals("Hell");
        if (this.customfontlol.getValue().booleanValue()) {
            this.cFontRenderer.drawStringWithShadow(ChatFormatting.DARK_BLUE + "XYZ " + ChatFormatting.WHITE + Math.round(CoolMan11.mc.player.posX) + ", " + Math.round(CoolMan11.mc.player.posY) + ", " + Math.round(CoolMan11.mc.player.posZ), xPos, this.y.getValue().floatValue(), 0xFFFFFF);
        } else if (this.Rainbow.getValue().booleanValue()) {
            if (!inHell) {
                RainbowText.renderRainbowWave("XYZ ", Float.valueOf(xPos), this.y.getValue(), 0.6f, false);
                Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("" + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ), xPos + (float)CoolMan11.mc.fontRenderer.getStringWidth("XYZ "), this.y.getValue().floatValue(), -1);
                RainbowText.renderRainbowWave(" (" + this.df.format(CoolMan11.mc.player.posX / 7.0) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ / 7.0) + ")", Float.valueOf(xPos + (float)CoolMan11.mc.fontRenderer.getStringWidth("XYZ " + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ))), this.y.getValue(), 0.6f, false);
            } else {
                RainbowText.renderRainbowWave("XYZ ", Float.valueOf(xPos), this.y.getValue(), 0.6f, false);
                Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("" + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ), xPos + (float)CoolMan11.mc.fontRenderer.getStringWidth("XYZ "), this.y.getValue().floatValue(), -1);
                RainbowText.renderRainbowWave(" (" + this.df.format(CoolMan11.mc.player.posX * 7.0) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ * 7.0) + ")", Float.valueOf(xPos + (float)CoolMan11.mc.fontRenderer.getStringWidth("XYZ " + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ))), this.y.getValue(), 0.6f, false);
            }
        } else if (!inHell) {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("XYZ " + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX / 7.0) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ / 7.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue(), color);
        } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("XYZ " + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoolMan11.mc.player.posX * 7.0) + ", " + this.df.format(CoolMan11.mc.player.posY) + ", " + this.df.format(CoolMan11.mc.player.posZ * 7.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue(), color);
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).getRGB();
    }
}

