//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.time.ZonedDateTime;
import java.util.Random;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.RainbowText;
import me.zeroeightsix.kami.util.Wrapper;

@Module.Info(name="Welcomer", category=Module.Category.GUI)
public class WelcomerV3
extends Module {
    private Setting<Float> x;
    private Setting<Float> y;
    private Setting<Boolean> Rainbowlol = this.register(Settings.b("Rainbow Font", false));
    private Setting<Boolean> testGame = this.register(Settings.b("TestGame", false));
    private Setting<Boolean> Customfonttest = this.register(Settings.b("Custom Font", false));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    Random r;
    ZonedDateTime time;
    private int counter = 0;

    public WelcomerV3() {
        this.x = this.register(Settings.f("X", 400.0f));
        this.y = this.register(Settings.f("Y", 0.0f));
        this.r = new Random();
        this.time = ZonedDateTime.now();
    }

    @Override
    public void onRender() {
        String timer;
        int drgb;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int color = drgb = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        this.setDrawn(false);
        float xPos = this.x.getValue().floatValue();
        String string = this.time.getHour() <= 11 ? "Good Morning " : (this.time.getHour() <= 18 && this.time.getHour() > 11 ? "Good Afternoon " : (timer = this.time.getHour() <= 23 && this.time.getHour() > 18 ? "Good Evening " : ""));
        if (this.Rainbowlol.getValue().booleanValue()) {
            if (this.Customfonttest.getValue().booleanValue()) {
                RainbowText.renderRainbowWave(timer + Wrapper.getPlayer().getDisplayNameString() + " :)", Float.valueOf(xPos), Float.valueOf(this.y.getValue().floatValue()), 0.6f, true);
            } else {
                RainbowText.renderRainbowWave(timer + Wrapper.getPlayer().getDisplayNameString() + " >:^)", Float.valueOf(xPos), Float.valueOf(this.y.getValue().floatValue()), 0.6f, false);
            }
        } else if (this.testGame.getValue().booleanValue()) {
            RainbowText.renderChristmasString(timer + Wrapper.getPlayer().getDisplayNameString() + " :)", Float.valueOf(xPos), Float.valueOf(this.y.getValue().floatValue()));
        } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(timer + Wrapper.getPlayer().getDisplayNameString() + " (^:<", xPos, this.y.getValue().floatValue(), color);
        }
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).getRGB();
    }
}

