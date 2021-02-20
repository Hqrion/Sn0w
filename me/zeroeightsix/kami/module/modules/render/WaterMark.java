/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Font;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.render.Totems;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.RainbowText;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(name="WaterMark", category=Module.Category.GUI)
public class WaterMark
extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 15.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 220.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> smooth = this.register(Settings.b("Custom Font", true));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(143).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    @Override
    public void onRender() {
        int color;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int drgb = color = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        int totems = Totems.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        if (Totems.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
            ++totems;
        }
        if (this.smooth.getValue().booleanValue()) {
            if (this.rainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("Sn0w v1.2 - cyborghack edition", this.x.getValue(), this.y.getValue(), 0.6f, true);
            } else {
                this.cFontRenderer.drawStringWithShadow("Sn0w v1.2 - cyborghack edition", this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            }
        } else if (this.rainbow.getValue().booleanValue()) {
            RainbowText.renderRainbowWave("Sn0w v1.2 - cyborghack edition", this.x.getValue(), this.y.getValue(), 0.6f, false);
        } else {
            Wrapper.getMinecraft().field_71466_p.func_175063_a("Sn0w v1.2 - cyborghack edition", this.x.getValue().floatValue(), (yCount += 10.0f) - (float)Wrapper.getMinecraft().field_71466_p.field_78288_b, color);
        }
    }
}

