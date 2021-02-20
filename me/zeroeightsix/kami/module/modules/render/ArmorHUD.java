//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

@Module.Info(name="ArmorHUD", category=Module.Category.RENDER)
public class ArmorHUD
extends Module {
    private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
    private Setting<Boolean> damage = this.register(Settings.b("Damage", true));

    @Override
    public void onRender() {
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 55 - (ArmorHUD.mc.player.isInWater() ? 10 : 0);
        for (ItemStack is : ArmorHUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            ArmorHUD.itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(ArmorHUD.mc.fontRenderer, is, x, y, "");
            ArmorHUD.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            ArmorHUD.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - ArmorHUD.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 0xFFFFFF);
            if (!this.damage.getValue().booleanValue()) continue;
            float green = ((float)is.getMaxDamage() - (float)is.getItemDamage()) / (float)is.getMaxDamage();
            float red = 1.0f - green;
            int dmg = 100 - (int)(red * 100.0f);
            ArmorHUD.mc.fontRenderer.drawStringWithShadow(dmg + "", (float)(x + 8 - ArmorHUD.mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
}

