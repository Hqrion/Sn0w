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
        GlStateManager.func_179094_E();
        GlStateManager.func_179118_c();
        GlStateManager.func_179086_m((int)256);
        GlStateManager.func_179147_l();
    }

    private static void postboxrender() {
        GlStateManager.func_179084_k();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179126_j();
        GlStateManager.func_179141_d();
        GlStateManager.func_179121_F();
        GL11.glPopMatrix();
    }

    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.func_179086_m((int)256);
        GlStateManager.func_179097_i();
        GlStateManager.func_179126_j();
        RenderHelper.func_74519_b();
        GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)0.01f);
    }

    private static void postitemrender() {
        GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.func_74518_a();
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179140_f();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.func_179097_i();
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void onRender() {
        NonNullList items = InventoryViewer.mc.field_71439_g.field_71071_by.field_70462_a;
        this.boxrender(this.optionX.getValue(), this.optionY.getValue());
        this.itemrender((NonNullList<ItemStack>)items, this.optionX.getValue(), this.optionY.getValue());
    }

    private void boxrender(int x, int y) {
        InventoryViewer.preboxrender();
        InventoryViewer.mc.field_71446_o.func_110577_a(box);
        InventoryViewer.mc.field_71456_v.func_73729_b(x, y, 7, 17, 162, 54);
        InventoryViewer.postboxrender();
    }

    private void itemrender(NonNullList<ItemStack> items, int x, int y) {
        int size = items.size();
        for (int item = 9; item < size; ++item) {
            int slotx = x + 1 + item % 9 * 18;
            int sloty = y + 1 + (item / 9 - 1) * 18;
            InventoryViewer.preitemrender();
            mc.func_175599_af().func_180450_b((ItemStack)items.get(item), slotx, sloty);
            mc.func_175599_af().func_175030_a(InventoryViewer.mc.field_71466_p, (ItemStack)items.get(item), slotx, sloty);
            InventoryViewer.postitemrender();
        }
    }
}

