//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
import java.awt.Color;
import java.awt.Font;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(name="PvPInfo", category=Module.Category.GUI)
public class PvPInfo
extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 16.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 320.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    private Setting<Boolean> CustomFont = this.register(Settings.b("Custom Font", false));

    @Override
    public void onRender() {
        int drgb;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int color = drgb = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        int totems = PvPInfo.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (PvPInfo.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        if (this.rainbow.getValue().booleanValue()) {
            int argb;
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int red = rgb >> 16 & 0xFF;
            int green = rgb >> 8 & 0xFF;
            int blue = rgb & 0xFF;
            color = argb = ColourUtils.toRGBA(red, green, blue, 255);
        }
        if (this.CustomFont.getValue().booleanValue()) {
            this.cFontRenderer.drawStringWithShadow("Crystal: " + this.getCA(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            this.cFontRenderer.drawStringWithShadow("AutoTrap: " + this.getAutoTrap(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            this.cFontRenderer.drawStringWithShadow("FeetPlace: " + this.getAutoFeetPlace(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
        } else {
            PvPInfo.mc.fontRenderer.drawStringWithShadow(ChatFormatting.WHITE + "Crystal: " + ChatFormatting.RESET + this.getCA(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            PvPInfo.mc.fontRenderer.drawStringWithShadow(ChatFormatting.WHITE + "AutoTrap: " + ChatFormatting.RESET + this.getAutoTrap(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
            PvPInfo.mc.fontRenderer.drawStringWithShadow(ChatFormatting.WHITE + "Feet: " + ChatFormatting.RESET + this.getAutoFeetPlace(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float)this.cFontRenderer.getHeight() - 1.0f, color);
        }
    }

    private String getAutoTrap() {
        try {
            return ModuleManager.getModuleByName("AutoTrap").isEnabled() ? ChatFormatting.DARK_BLUE.toString() + "ON" : ChatFormatting.WHITE.toString() + "OFF";
        }
        catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }

    private String getAutoFeetPlace() {
        try {
            return ModuleManager.getModuleByName("AutoFeetPlace").isEnabled() ? ChatFormatting.DARK_BLUE.toString() + "ON" : ChatFormatting.WHITE.toString() + "OFF";
        }
        catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }

    private String getSelfTrap() {
        try {
            return ModuleManager.getModuleByName("SelfTrap").isEnabled() ? ChatFormatting.DARK_BLUE.toString() + "ON" : ChatFormatting.WHITE.toString() + "OFF";
        }
        catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }

    private String getCA() {
        try {
            return ModuleManager.getModuleByName("Crystal Aura").isEnabled() ? ChatFormatting.DARK_BLUE.toString() + "ON" : ChatFormatting.WHITE.toString() + "OFF";
        }
        catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }
}

