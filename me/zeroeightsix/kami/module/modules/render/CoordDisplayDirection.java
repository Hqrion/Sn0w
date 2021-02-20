/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.gui.GuiChat
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
import net.minecraft.client.gui.GuiChat;

@Module.Info(name="CoordDisplayDIRECTION", category=Module.Category.GUI)
public class CoordDisplayDirection
extends Module {
    private Setting<Float> x;
    private Setting<Float> y;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    int k;
    private Setting<Boolean> customfontlol = this.register(Settings.b("Custom Font", false));
    private Setting<Boolean> Rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> idkdiffcolor = this.register(Settings.b("White XYZ", false));
    Random r;
    ZonedDateTime time;
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    private int counter = 0;
    DecimalFormat df;

    public CoordDisplayDirection() {
        this.x = this.register(Settings.f("X", 400.0f));
        this.y = this.register(Settings.f("Y", 0.0f));
        this.r = new Random();
        this.time = ZonedDateTime.now();
        this.red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(143).build());
        this.green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
        this.blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
        this.df = new DecimalFormat("#.#");
    }

    private String getFacing(String in) {
        String facing = CoordDisplayDirection.getTitle(in);
        String add = in.equalsIgnoreCase("North") ? " (-Z)" : (in.equalsIgnoreCase("East") ? " (+X)" : (in.equalsIgnoreCase("South") ? " (+Z)" : (in.equalsIgnoreCase("West") ? " (-X)" : " ERROR")));
        return facing + add;
    }

    public static String getTitle(String in) {
        in = Character.toUpperCase(in.toLowerCase().charAt(0)) + in.toLowerCase().substring(1);
        return in;
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
        this.k = CoordDisplayDirection.mc.field_71462_r instanceof GuiChat ? 14 : 0;
        boolean inHell = CoordDisplayDirection.mc.field_71441_e.func_180494_b(CoordDisplayDirection.mc.field_71439_g.func_180425_c()).func_185359_l().equals("Hell");
        if (this.customfontlol.getValue().booleanValue()) {
            this.cFontRenderer.drawStringWithShadow(ChatFormatting.DARK_BLUE + this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ " + ChatFormatting.WHITE + Math.round(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + Math.round(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + Math.round(CoordDisplayDirection.mc.field_71439_g.field_70161_v), xPos, this.y.getValue().floatValue() - (float)this.k, 0xFFFFFF);
        } else if (this.Rainbow.getValue().booleanValue()) {
            if (!inHell) {
                RainbowText.renderRainbowWave(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ ", Float.valueOf(xPos), Float.valueOf(this.y.getValue().floatValue() - (float)this.k), 0.6f, false);
                Wrapper.getMinecraft().field_71466_p.func_175063_a("" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v), xPos + (float)CoordDisplayDirection.mc.field_71466_p.func_78256_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ "), this.y.getValue().floatValue() - (float)this.k, -1);
                RainbowText.renderRainbowWave(" (" + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t / 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v / 8.0) + ")", Float.valueOf(xPos + (float)CoordDisplayDirection.mc.field_71466_p.func_78256_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v))), Float.valueOf(this.y.getValue().floatValue() - (float)this.k), 0.6f, false);
            } else {
                RainbowText.renderRainbowWave(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ ", Float.valueOf(xPos), Float.valueOf(this.y.getValue().floatValue() - (float)this.k), 0.6f, false);
                Wrapper.getMinecraft().field_71466_p.func_175063_a("" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v), xPos + (float)CoordDisplayDirection.mc.field_71466_p.func_78256_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ "), this.y.getValue().floatValue() - (float)this.k, -1);
                RainbowText.renderRainbowWave(" (" + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t * 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v * 8.0) + ")", Float.valueOf(xPos + (float)CoordDisplayDirection.mc.field_71466_p.func_78256_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v))), Float.valueOf(this.y.getValue().floatValue() - (float)this.k), 0.6f, false);
            }
        } else if (!inHell) {
            if (this.idkdiffcolor.getValue().booleanValue()) {
                Wrapper.getMinecraft().field_71466_p.func_175063_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + ChatFormatting.WHITE + " XYZ " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t / 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v / 8.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue() - (float)this.k, color);
            } else {
                Wrapper.getMinecraft().field_71466_p.func_175063_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ " + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t / 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v / 8.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue() - (float)this.k, color);
            }
        } else if (this.idkdiffcolor.getValue().booleanValue()) {
            Wrapper.getMinecraft().field_71466_p.func_175063_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + ChatFormatting.WHITE + " XYZ " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t * 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v * 8.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue() - (float)this.k, color);
        } else {
            Wrapper.getMinecraft().field_71466_p.func_175063_a(this.getFacing(CoordDisplayDirection.mc.field_71439_g.func_174811_aO().func_176610_l().toUpperCase()) + " XYZ " + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v) + ChatFormatting.RESET + " (" + ChatFormatting.WHITE + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70165_t * 8.0) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70163_u) + ", " + this.df.format(CoordDisplayDirection.mc.field_71439_g.field_70161_v * 8.0) + ChatFormatting.RESET + ")", xPos, this.y.getValue().floatValue() - (float)this.k, color);
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).getRGB();
    }
}

