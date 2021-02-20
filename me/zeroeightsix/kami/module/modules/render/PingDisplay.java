/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
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

@Module.Info(name="Ping", category=Module.Category.GUI)
public class PingDisplay
extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 15.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 220.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> fullrainbow = this.register(Settings.b("Full Rainbow", false));
    private Setting<Boolean> smooth = this.register(Settings.b("Custom Font", true));
    private Setting<Boolean> gamer = this.register(Settings.b(": or no", true));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(143).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    String sign;
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
        this.sign = this.gamer.getValue() != false ? ": " : " ";
        if (this.smooth.getValue().booleanValue()) {
            if (this.rainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("Ping" + this.sign + (mc.func_147114_u() != null && PingDisplay.mc.field_71439_g != null && mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i) != null ? Integer.valueOf(mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i).func_178853_c()) : "-1") + "ms", this.x.getValue(), this.y.getValue(), 0.6f, true);
            } else {
                this.cFontRenderer.drawStringWithShadow("Ping" + this.sign + (mc.func_147114_u() != null && PingDisplay.mc.field_71439_g != null && mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i) != null ? Integer.valueOf(mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i).func_178853_c()) : "-1") + "ms", this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            }
        } else if (this.rainbow.getValue().booleanValue()) {
            if (this.fullrainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("Ping" + this.sign + (mc.func_147114_u() != null && PingDisplay.mc.field_71439_g != null && mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i) != null ? Integer.valueOf(mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i).func_178853_c()) : "-1") + "ms", this.x.getValue(), Float.valueOf(yCount), 0.6f, false);
            } else {
                Wrapper.getMinecraft().field_71466_p.func_175063_a("Ping" + this.sign, this.x.getValue().floatValue(), yCount, -1);
                RainbowText.renderRainbowWave("" + (mc.func_147114_u() != null && PingDisplay.mc.field_71439_g != null && mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i) != null ? Integer.valueOf(mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i).func_178853_c()) : "-1") + "ms", Float.valueOf(this.x.getValue().floatValue() + (float)PingDisplay.mc.field_71466_p.func_78256_a("Ping" + this.sign)), Float.valueOf(yCount), 0.6f, false);
            }
        } else {
            Wrapper.getMinecraft().field_71466_p.func_175063_a("Ping" + this.sign + ChatFormatting.WHITE + (mc.func_147114_u() != null && PingDisplay.mc.field_71439_g != null && mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i) != null ? Integer.valueOf(mc.func_147114_u().func_175102_a(PingDisplay.mc.field_71439_g.field_96093_i).func_178853_c()) : "-1") + "ms", this.x.getValue().floatValue(), yCount, color);
        }
    }
}

