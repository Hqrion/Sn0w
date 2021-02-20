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
    private static RenderItem itemRender = Minecraft.func_71410_x().func_175599_af();
    private Setting<Boolean> damage = this.register(Settings.b("Damage", true));

    @Override
    public void onRender() {
        GlStateManager.func_179098_w();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.func_78326_a() / 2;
        int iteration = 0;
        int y = resolution.func_78328_b() - 55 - (ArmorHUD.mc.field_71439_g.func_70090_H() ? 10 : 0);
        for (ItemStack is : ArmorHUD.mc.field_71439_g.field_71071_by.field_70460_b) {
            ++iteration;
            if (is.func_190926_b()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.func_179126_j();
            ArmorHUD.itemRender.field_77023_b = 200.0f;
            itemRender.func_180450_b(is, x, y);
            itemRender.func_180453_a(ArmorHUD.mc.field_71466_p, is, x, y, "");
            ArmorHUD.itemRender.field_77023_b = 0.0f;
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
            ArmorHUD.mc.field_71466_p.func_175063_a(s, (float)(x + 19 - 2 - ArmorHUD.mc.field_71466_p.func_78256_a(s)), (float)(y + 9), 0xFFFFFF);
            if (!this.damage.getValue().booleanValue()) continue;
            float green = ((float)is.func_77958_k() - (float)is.func_77952_i()) / (float)is.func_77958_k();
            float red = 1.0f - green;
            int dmg = 100 - (int)(red * 100.0f);
            ArmorHUD.mc.field_71466_p.func_175063_a(dmg + "", (float)(x + 8 - ArmorHUD.mc.field_71466_p.func_78256_a(dmg + "") / 2), (float)(y - 11), ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.func_179126_j();
        GlStateManager.func_179140_f();
    }
}

