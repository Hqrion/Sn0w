//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
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
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(name="FPS", category=Module.Category.GUI)
public class FPSDISPLAY
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
        int totems = Totems.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Totems.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        this.sign = this.gamer.getValue() != false ? ": " : " ";
        if (this.smooth.getValue().booleanValue()) {
            if (this.rainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("FPS" + this.sign + Minecraft.getDebugFPS(), this.x.getValue(), this.y.getValue(), 0.6f, true);
            } else {
                this.cFontRenderer.drawStringWithShadow("FPS" + this.sign + Minecraft.getDebugFPS(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            }
        } else if (this.rainbow.getValue().booleanValue()) {
            if (this.fullrainbow.getValue().booleanValue()) {
                RainbowText.renderRainbowWave("FPS" + this.sign + Minecraft.getDebugFPS(), this.x.getValue(), Float.valueOf(yCount), 0.6f, false);
            } else {
                Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("FPS" + this.sign, this.x.getValue().floatValue(), yCount, -1);
                RainbowText.renderRainbowWave(String.valueOf(Minecraft.getDebugFPS()), Float.valueOf(this.x.getValue().floatValue() + (float)FPSDISPLAY.mc.fontRenderer.getStringWidth("FPS" + this.sign)), Float.valueOf(yCount), 0.6f, false);
            }
        } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("FPS" + this.sign + ChatFormatting.WHITE + Minecraft.getDebugFPS(), this.x.getValue().floatValue(), yCount, color);
        }
    }
}

