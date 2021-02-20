//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
            Gui.drawRect((int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 14), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 25 + 50), (int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 + 14), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 - 1 + 50), (int)new Color(2, 2, 2, 255).getRGB());
            Gui.drawRect((int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 52), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 23 + 50), (int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 + 52), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 52 + 50), (int)new Color(30, 30, 30, 255).getRGB());
            Gui.drawRect((int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 51), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 24 + 50), (int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 + 51), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 51 + 50), (int)new Color(35, 35, 35, 40).getRGB());
            Gui.drawRect((int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 50), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 25 + 50), (int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 + 50), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 50 + 50), (int)new Color(14, 14, 14, 255).getRGB());
            Gui.drawRect((int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 13), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 24 + 50), (int)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 + 13), (int)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 50), (int)new Color(47, 47, 47, 255).getRGB());
            this.drawAltFace(this.getTarget(), TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 12, TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 1 + 50, 24, 24);
            TargetHudTest.mc.fontRenderer.drawStringWithShadow(this.getTarget().getName(), (float)(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - TargetHudTest.mc.fontRenderer.getStringWidth(this.getTarget().getName()) / 2), (float)(TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 27 + 50), -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
            TargetHudTest.mc.fontRenderer.drawStringWithShadow("hp: " + Math.floor((double)this.getTarget().getHealth() + Math.floor(this.getTarget().getAbsorptionAmount())), (float)(TargetHudTest.getScaledResolution().getScaledWidth() - TargetHudTest.mc.fontRenderer.getStringWidth("hp: " + Math.floor((double)this.getTarget().getHealth() + Math.floor(this.getTarget().getAbsorptionAmount()))) / 2), (float)(TargetHudTest.getScaledResolution().getScaledHeight() + 75 + 100), -1);
            GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
            GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
            TargetHudTest.drawRect(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 48, TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 45 + 50, (this.getTarget().getHealth() > 20.0f ? 20.0f : this.getTarget().getHealth()) / 2.0f * 9.6f, 3.0f, new Color(255, 255, 255, 255).getRGB());
            TargetHudTest.drawRect(TargetHudTest.getScaledResolution().getScaledWidth() / 2 - 47, TargetHudTest.getScaledResolution().getScaledHeight() / 2 + 46 + 50, (this.getTarget().getHealth() > 20.0f ? 20.0f : this.getTarget().getHealth()) / 2.0f * 9.4f, 1.0f, this.getHealthColor(this.getTarget()));
        }
    }

    private EntityLivingBase getTarget() {
        double minVal = Double.POSITIVE_INFINITY;
        Entity bestEntity = null;
        for (Entity e : TargetHudTest.mc.world.loadedEntityList) {
            double val = this.getSortingWeight(e);
            if (!this.isValidEntity(e) || !(val < minVal)) continue;
            minVal = val;
            bestEntity = e;
        }
        return (EntityLivingBase)bestEntity;
    }

    private double getSortingWeight(Entity e) {
        return e instanceof EntityLivingBase ? (double)(((EntityLivingBase)e).getHealth() + ((EntityLivingBase)e).getAbsorptionAmount()) : Double.POSITIVE_INFINITY;
    }

    private boolean isValidEntity(Entity entity) {
        return entity instanceof EntityLivingBase && entity.getEntityId() != -1488 && entity != TargetHudTest.mc.player && entity.isEntityAlive() && TargetHudTest.mc.player.getDistanceSq(entity) <= 2500.0 && entity instanceof EntityPlayer;
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(mc);
    }

    private int getHealthColor(EntityLivingBase player) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(player.getHealth(), player.getMaxHealth()) / player.getMaxHealth()) / 3.0f, 1.0f, 0.8f) | 0xFF000000;
    }

    private void drawAltFace(EntityLivingBase target, int x, int y, int w, int h) {
        try {
            ResourceLocation skin = ((AbstractClientPlayer)target).getLocationSkin();
            mc.getTextureManager().bindTexture(skin);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            float fw = 192.0f;
            float fh = 192.0f;
            float u = 24.0f;
            float v = 24.0f;
            Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, (float)u, (float)v, (int)w, (int)h, (float)fw, (float)fh);
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
        Tessellator lvt_9_1_ = Tessellator.getInstance();
        BufferBuilder lvt_10_1_ = lvt_9_1_.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)lvt_6_1_, (float)lvt_7_1_, (float)lvt_8_1_, (float)lvt_5_3_);
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION);
        lvt_10_1_.pos((double)x, (double)p_drawRect_3_, 0.0).endVertex();
        lvt_10_1_.pos((double)p_drawRect_2_, (double)p_drawRect_3_, 0.0).endVertex();
        lvt_10_1_.pos((double)p_drawRect_2_, (double)y, 0.0).endVertex();
        lvt_10_1_.pos((double)x, (double)y, 0.0).endVertex();
        lvt_9_1_.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

