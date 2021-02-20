/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Font;
import java.text.DecimalFormat;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.RainbowText;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.util.math.MathHelper;

@Module.Info(name="KM/H", category=Module.Category.GUI)
public class SpeedDisplay
extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 15.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 220.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> fullrainbow = this.register(Settings.b("Full Rainbow", false));
    private Setting<Boolean> smooth = this.register(Settings.b("Custom Font", true));
    private Setting<Boolean> gamer = this.register(Settings.b(": or no", true));
    private Setting<Boolean> OrderOtherWay;
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(143).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    String sign;
    float xPos;
    DecimalFormat df = new DecimalFormat("#.#");
    DecimalFormat df2 = new DecimalFormat("#.###");

    public SpeedDisplay() {
        this.OrderOtherWay = this.register(Settings.b("Renderotherwayidk", false));
    }

    public static double coordsDiff(char s) {
        switch (s) {
            case 'x': {
                return SpeedDisplay.mc.field_71439_g.field_70165_t - SpeedDisplay.mc.field_71439_g.field_70169_q;
            }
            case 'z': {
                return SpeedDisplay.mc.field_71439_g.field_70161_v - SpeedDisplay.mc.field_71439_g.field_70166_s;
            }
        }
        return 0.0;
    }

    @Override
    public void onRender() {
        int color;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int drgb = color = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        this.sign = this.gamer.getValue() != false ? ": " : " ";
        this.xPos = this.OrderOtherWay.getValue() != false ? this.x.getValue().floatValue() - (float)SpeedDisplay.mc.field_71466_p.func_78256_a("Speed" + this.sign + KamiMod.getInstance().guiManager.getHUDTextColor() + this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h") : this.x.getValue().floatValue();
        if (this.smooth.getValue().booleanValue()) {
            if (this.rainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("Speed" + this.sign + this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h", Float.valueOf(this.xPos), this.y.getValue(), 0.6f, true);
            } else {
                this.cFontRenderer.drawStringWithShadow("Speed" + this.sign + KamiMod.getInstance().guiManager.getHUDTextColor() + this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h", this.xPos, (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            }
        } else if (this.rainbow.getValue().booleanValue()) {
            if (this.fullrainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("Speed" + this.sign + this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h", Float.valueOf(this.xPos), Float.valueOf(yCount), 0.6f, false);
            } else {
                Wrapper.getMinecraft().field_71466_p.func_175063_a("Speed" + this.sign, this.xPos, yCount, -1);
                RainbowText.renderRainbowWave(this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h", Float.valueOf(this.xPos + (float)SpeedDisplay.mc.field_71466_p.func_78256_a("Speed" + this.sign)), Float.valueOf(yCount), 0.6f, false);
            }
        } else {
            Wrapper.getMinecraft().field_71466_p.func_175063_a("Speed" + this.sign + KamiMod.getInstance().guiManager.getHUDTextColor() + this.df.format((double)MathHelper.func_76133_a((double)(Math.pow(SpeedDisplay.coordsDiff('x'), 2.0) + Math.pow(SpeedDisplay.coordsDiff('z'), 2.0))) / 0.05 * 3.6) + " km/h", this.xPos, yCount, color);
        }
    }
}

