/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.BossInfoClient
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.BossInfo
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.HashMap;
import java.util.Map;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@Module.Info(name="BossStack", description="Modify the boss health GUI to take up less space", category=Module.Category.MISC)
public class BossStack
extends Module {
    private static Setting<BossStackMode> mode = Settings.e("Mode", BossStackMode.STACK);
    private static Setting<Double> scale = Settings.d("Scale", 0.5);
    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");

    public BossStack() {
        this.registerAll(mode, scale);
    }

    public static void render(RenderGameOverlayEvent.Post event) {
        block8: {
            block7: {
                if (mode.getValue() != BossStackMode.MINIMIZE) break block7;
                Map map = Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
                if (map == null) {
                    return;
                }
                ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
                int i = scaledresolution.func_78326_a();
                int j = 12;
                for (Map.Entry entry : map.entrySet()) {
                    BossInfoClient info = (BossInfoClient)entry.getValue();
                    String text = info.func_186744_e().func_150254_d();
                    int k = (int)((double)i / scale.getValue() / 2.0 - 91.0);
                    GL11.glScaled((double)scale.getValue(), (double)scale.getValue(), (double)1.0);
                    if (!event.isCanceled()) {
                        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        Minecraft.func_71410_x().func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
                        Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(k, j, (BossInfo)info);
                        Minecraft.func_71410_x().field_71466_p.func_175063_a(text, (float)((double)i / scale.getValue() / 2.0 - (double)(Minecraft.func_71410_x().field_71466_p.func_78256_a(text) / 2)), (float)(j - 9), 0xFFFFFF);
                    }
                    GL11.glScaled((double)(1.0 / scale.getValue()), (double)(1.0 / scale.getValue()), (double)1.0);
                    j += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b;
                }
                break block8;
            }
            if (mode.getValue() != BossStackMode.STACK) break block8;
            Map map = Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
            HashMap to = new HashMap();
            for (Map.Entry entry : map.entrySet()) {
                Pair p;
                String s = ((BossInfoClient)entry.getValue()).func_186744_e().func_150254_d();
                if (to.containsKey(s)) {
                    p = (Pair)to.get(s);
                    p = new Pair(p.getKey(), p.getValue() + 1);
                    to.put(s, p);
                    continue;
                }
                p = new Pair(entry.getValue(), 1);
                to.put(s, p);
            }
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
            int i = scaledresolution.func_78326_a();
            int j = 12;
            for (Map.Entry entry : to.entrySet()) {
                String text = (String)entry.getKey();
                BossInfoClient info = (BossInfoClient)((Pair)entry.getValue()).getKey();
                int a = (Integer)((Pair)entry.getValue()).getValue();
                text = text + " x" + a;
                int k = (int)((double)i / scale.getValue() / 2.0 - 91.0);
                GL11.glScaled((double)scale.getValue(), (double)scale.getValue(), (double)1.0);
                if (!event.isCanceled()) {
                    GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(GUI_BARS_TEXTURES);
                    Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(k, j, (BossInfo)info);
                    Minecraft.func_71410_x().field_71466_p.func_175063_a(text, (float)((double)i / scale.getValue() / 2.0 - (double)(Minecraft.func_71410_x().field_71466_p.func_78256_a(text) / 2)), (float)(j - 9), 0xFFFFFF);
                }
                GL11.glScaled((double)(1.0 / scale.getValue()), (double)(1.0 / scale.getValue()), (double)1.0);
                j += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b;
            }
        }
    }

    private static enum BossStackMode {
        REMOVE,
        STACK,
        MINIMIZE;

    }
}

