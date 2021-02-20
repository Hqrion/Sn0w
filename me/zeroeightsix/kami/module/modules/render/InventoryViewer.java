//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@Module.Info(name="InventoryViewer", category=Module.Category.RENDER, description="View Inventory")
public class InventoryViewer
extends Module {
    private static final ResourceLocation box = new ResourceLocation("textures/gui/container/shulker_box.png");
    private Setting<Integer> optionX = this.register(Settings.i("X", 0));
    private Setting<Integer> optionY = this.register(Settings.i("Y", 0));

    private static void preboxrender() {
        GL11.glPushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear((int)256);
        GlStateManager.enableBlend();
    }

    private static void postboxrender() {
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }

    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)0.01f);
    }

    private static void postitemrender() {
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void onRender() {
        NonNullList items = InventoryViewer.mc.player.inventory.mainInventory;
        this.boxrender(this.optionX.getValue(), this.optionY.getValue());
        this.itemrender((NonNullList<ItemStack>)items, this.optionX.getValue(), this.optionY.getValue());
    }

    private void boxrender(int x, int y) {
        InventoryViewer.preboxrender();
        InventoryViewer.mc.renderEngine.bindTexture(box);
        InventoryViewer.mc.ingameGUI.drawTexturedModalRect(x, y, 7, 17, 162, 54);
        InventoryViewer.postboxrender();
    }

    private void itemrender(NonNullList<ItemStack> items, int x, int y) {
        int size = items.size();
        for (int item = 9; item < size; ++item) {
            int slotx = x + 1 + item % 9 * 18;
            int sloty = y + 1 + (item / 9 - 1) * 18;
            InventoryViewer.preitemrender();
            mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)items.get(item), slotx, sloty);
            mc.getRenderItem().renderItemOverlays(InventoryViewer.mc.fontRenderer, (ItemStack)items.get(item), slotx, sloty);
            InventoryViewer.postitemrender();
        }
    }
}

