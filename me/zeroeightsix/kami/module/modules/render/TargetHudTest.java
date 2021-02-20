/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@Module.Info(name="Target Hud TEST", category=Module.Category.GUI)
public class TargetHudTest
extends Module {
    private Gui gui = new Gui();

    @Override
    public void onRender() {
        if (this.getTarget() != null && this.getTarget() instanceof EntityPlayer) {
            EntityLivingBase ent = this.getTarget();
            Gui.func_73734_a((int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 14), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 25 + 50), (int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 + 14), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 - 1 + 50), (int)new Color(2, 2, 2, 255).getRGB());
            Gui.func_73734_a((int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 52), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 23 + 50), (int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 + 52), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 52 + 50), (int)new Color(30, 30, 30, 255).getRGB());
            Gui.func_73734_a((int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 51), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 24 + 50), (int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 + 51), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 51 + 50), (int)new Color(35, 35, 35, 40).getRGB());
            Gui.func_73734_a((int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 50), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 25 + 50), (int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 + 50), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 50 + 50), (int)new Color(14, 14, 14, 255).getRGB());
            Gui.func_73734_a((int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 13), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 24 + 50), (int)(TargetHudTest.getScaledResolution().func_78326_a() / 2 + 13), (int)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 50), (int)new Color(47, 47, 47, 255).getRGB());
            this.drawAltFace(this.getTarget(), TargetHudTest.getScaledResolution().func_78326_a() / 2 - 12, TargetHudTest.getScaledResolution().func_78328_b() / 2 + 1 + 50, 24, 24);
            TargetHudTest.mc.field_71466_p.func_175063_a(this.getTarget().func_70005_c_(), (float)(TargetHudTest.getScaledResolution().func_78326_a() / 2 - TargetHudTest.mc.field_71466_p.func_78256_a(this.getTarget().func_70005_c_()) / 2), (float)(TargetHudTest.getScaledResolution().func_78328_b() / 2 + 27 + 50), -1);
            GlStateManager.func_179094_E();
            GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
            TargetHudTest.mc.field_71466_p.func_175063_a("hp: " + Math.floor((double)this.getTarget().func_110143_aJ() + Math.floor(this.getTarget().func_110139_bj())), (float)(TargetHudTest.getScaledResolution().func_78326_a() - TargetHudTest.mc.field_71466_p.func_78256_a("hp: " + Math.floor((double)this.getTarget().func_110143_aJ() + Math.floor(this.getTarget().func_110139_bj()))) / 2), (float)(TargetHudTest.getScaledResolution().func_78328_b() + 75 + 100), -1);
            GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
            GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179121_F();
            TargetHudTest.drawRect(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 48, TargetHudTest.getScaledResolution().func_78328_b() / 2 + 45 + 50, (this.getTarget().func_110143_aJ() > 20.0f ? 20.0f : this.getTarget().func_110143_aJ()) / 2.0f * 9.6f, 3.0f, new Color(255, 255, 255, 255).getRGB());
            TargetHudTest.drawRect(TargetHudTest.getScaledResolution().func_78326_a() / 2 - 47, TargetHudTest.getScaledResolution().func_78328_b() / 2 + 46 + 50, (this.getTarget().func_110143_aJ() > 20.0f ? 20.0f : this.getTarget().func_110143_aJ()) / 2.0f * 9.4f, 1.0f, this.getHealthColor(this.getTarget()));
        }
    }

    private EntityLivingBase getTarget() {
        double minVal = Double.POSITIVE_INFINITY;
        Entity bestEntity = null;
        for (Entity e : TargetHudTest.mc.field_71441_e.field_72996_f) {
            double val = this.getSortingWeight(e);
            if (!this.isValidEntity(e) || !(val < minVal)) continue;
            minVal = val;
            bestEntity = e;
        }
        return (EntityLivingBase)bestEntity;
    }

    private double getSortingWeight(Entity e) {
        return e instanceof EntityLivingBase ? (double)(((EntityLivingBase)e).func_110143_aJ() + ((EntityLivingBase)e).func_110139_bj()) : Double.POSITIVE_INFINITY;
    }

    private boolean isValidEntity(Entity entity) {
        return entity instanceof EntityLivingBase && entity.func_145782_y() != -1488 && entity != TargetHudTest.mc.field_71439_g && entity.func_70089_S() && TargetHudTest.mc.field_71439_g.func_70068_e(entity) <= 2500.0 && entity instanceof EntityPlayer;
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(mc);
    }

    private int getHealthColor(EntityLivingBase player) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(player.func_110143_aJ(), player.func_110138_aP()) / player.func_110138_aP()) / 3.0f, 1.0f, 0.8f) | 0xFF000000;
    }

    private void drawAltFace(EntityLivingBase target, int x, int y, int w, int h) {
        try {
            ResourceLocation skin = ((AbstractClientPlayer)target).func_110306_p();
            mc.func_110434_K().func_110577_a(skin);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            float fw = 192.0f;
            float fh = 192.0f;
            float u = 24.0f;
            float v = 24.0f;
            Gui.func_146110_a((int)x, (int)y, (float)u, (float)v, (int)w, (int)h, (float)fw, (float)fh);
            GL11.glDisable((int)3042);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawRect(float x, float y, float w, float h, int color) {
        float lvt_5_2_;
        float p_drawRect_2_ = x + w;
        float p_drawRect_3_ = y + h;
        if (x < p_drawRect_2_) {
            lvt_5_2_ = x;
            x = p_drawRect_2_;
            p_drawRect_2_ = lvt_5_2_;
        }
        if (y < p_drawRect_3_) {
            lvt_5_2_ = y;
            y = p_drawRect_3_;
            p_drawRect_3_ = lvt_5_2_;
        }
        float lvt_5_3_ = (float)(color >> 24 & 0xFF) / 255.0f;
        float lvt_6_1_ = (float)(color >> 16 & 0xFF) / 255.0f;
        float lvt_7_1_ = (float)(color >> 8 & 0xFF) / 255.0f;
        float lvt_8_1_ = (float)(color & 0xFF) / 255.0f;
        Tessellator lvt_9_1_ = Tessellator.func_178181_a();
        BufferBuilder lvt_10_1_ = lvt_9_1_.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c((float)lvt_6_1_, (float)lvt_7_1_, (float)lvt_8_1_, (float)lvt_5_3_);
        lvt_10_1_.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        lvt_10_1_.func_181662_b((double)x, (double)p_drawRect_3_, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)p_drawRect_3_, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)y, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
        lvt_9_1_.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
}

