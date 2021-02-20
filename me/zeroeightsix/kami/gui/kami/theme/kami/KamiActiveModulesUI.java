//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.awt.Font;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.render.GUI;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.GuiManager;
import me.zeroeightsix.kami.util.Wrapper;
import org.lwjgl.opengl.GL11;

public class KamiActiveModulesUI
extends AbstractComponentUI<ActiveModules> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    String text;
    int textheight;
    int textwidth;
    List<Module> mods;

    @Override
    public void renderComponent(ActiveModules component, FontRenderer f) {
        Function<Integer, Integer> xFunc;
        GL11.glDisable((int)2884);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3553);
        FontRenderer renderer = Wrapper.getFontRenderer();
        this.mods = GUI.BracketsArraylistss.getValue().booleanValue() ? (ModuleManager.isModuleEnabled("CF") ? ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.cFontRenderer.getStringWidth(module.getName() + (module.getHudInfo() == null ? "" : " [" + module.getHudInfo() + "]")) * (component.sort_up ? -1 : 1))).collect(Collectors.toList()) : ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> renderer.getStringWidth(module.getName() + (module.getHudInfo() == null ? "" : " [" + module.getHudInfo() + "]")) * (component.sort_up ? -1 : 1))).collect(Collectors.toList())) : (ModuleManager.isModuleEnabled("CF") ? ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.cFontRenderer.getStringWidth(module.getName() + (module.getHudInfo() == null ? "" : module.getHudInfo() + " ")) * (component.sort_up ? -1 : 1))).collect(Collectors.toList()) : ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> renderer.getStringWidth(module.getName() + (module.getHudInfo() == null ? "" : module.getHudInfo() + " ")) * (component.sort_up ? -1 : 1))).collect(Collectors.toList()));
        int[] y = new int[]{2};
        if (GUI.CareForPotionHud.getValue().booleanValue() && component.getParent().getY() < 26 && Wrapper.getPlayer().getActivePotionEffects().size() > 0 && component.getParent().getOpacity() == 0.0f) {
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());
        }
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        switch (component.getAlignment()) {
            case RIGHT: {
                xFunc = i -> component.getWidth() - i;
                break;
            }
            case CENTER: {
                xFunc = i -> component.getWidth() / 2 - i / 2;
                break;
            }
            default: {
                xFunc = i -> 0;
            }
        }
        this.mods.forEach(module -> {
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            String s = module.getHudInfo();
            this.text = GUI.BracketsArraylistss.getValue() != false ? module.getName() + (s == null ? "" : " " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + s + ChatFormatting.GRAY + "]" + ChatFormatting.RESET) : module.getName() + (s == null ? "" : " " + KamiMod.getInstance().guiManager.getTextColor() + s);
            this.textwidth = ModuleManager.isModuleEnabled("CF") ? this.cFontRenderer.getStringWidth(this.text) : renderer.getStringWidth(this.text);
            this.textheight = renderer.getFontHeight() + 1;
            int red = 0;
            int green = 0;
            int blue = 0;
            if (KamiMod.getInstance().guiManager.getModuleListMode().equals((Object)GuiManager.ModuleListMode.STATIC)) {
                red = KamiMod.getInstance().guiManager.getModuleListRed();
                green = KamiMod.getInstance().guiManager.getModuleListGreen();
                blue = KamiMod.getInstance().guiManager.getModuleListBlue();
            }
            if (KamiMod.getInstance().guiManager.getModuleListMode().equals((Object)GuiManager.ModuleListMode.RAINBOW)) {
                red = rgb >> 16 & 0xFF;
                green = rgb >> 8 & 0xFF;
                blue = rgb & 0xFF;
            }
            if (ModuleManager.isModuleEnabled("CF")) {
                int hacker;
                int argb = hacker = ColourUtils.toRGBA(red, green, blue, 255);
                this.cFontRenderer.drawStringWithShadow(this.text, ((Integer)xFunc.apply(this.textwidth)).intValue(), y[0], hacker);
                hue[0] = hue[0] + 0.02f;
                y[0] = y[0] + this.textheight;
            } else {
                renderer.drawStringWithShadow((Integer)xFunc.apply(this.textwidth), y[0], red, green, blue, this.text);
                hue[0] = hue[0] + 0.02f;
                y[0] = y[0] + this.textheight;
            }
        });
        component.setHeight(y[0]);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleSizeComponent(ActiveModules component) {
        component.setWidth(100);
        component.setHeight(100);
    }
}

