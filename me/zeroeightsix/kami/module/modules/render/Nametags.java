//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(name="NametagsOLD", description="Draws descriptive nametags above entities", category=Module.Category.RENDER)
public class Nametags
extends Module {
    private Setting<Boolean> players = this.register(Settings.b("Players", true));
    private Setting<Boolean> animals = this.register(Settings.b("Animals", false));
    private Setting<Boolean> mobs = this.register(Settings.b("Mobs", false));
    private Setting<Double> range = this.register(Settings.d("Range", 200.0));
    private Setting<Float> scale = this.register(Settings.floatBuilder("Scale").withMinimum(Float.valueOf(0.5f)).withMaximum(Float.valueOf(10.0f)).withValue(Float.valueOf(1.0f)).build());
    private Setting<Boolean> health = this.register(Settings.b("Health", true));
    RenderItem itemRenderer = mc.getRenderItem();

    @Override
    public void onWorldRender(RenderEvent event) {
        if (Nametags.mc.getRenderManager().options == null) {
            return;
        }
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        Minecraft.getMinecraft().world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity)).filter(entity -> entity instanceof EntityPlayer ? this.players.getValue().booleanValue() && Nametags.mc.player != entity : (EntityUtil.isPassive(entity) ? this.animals.getValue().booleanValue() : this.mobs.getValue().booleanValue())).filter(entity -> (double)Nametags.mc.player.getDistance(entity) < this.range.getValue()).sorted(Comparator.comparing(entity -> Float.valueOf(-Nametags.mc.player.getDistance(entity)))).forEach(this::drawNametag);
        GlStateManager.disableTexture2D();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private void drawNametag(Entity entityIn) {
        GlStateManager.pushMatrix();
        Vec3d interp = EntityUtil.getInterpolatedRenderPos(entityIn, mc.getRenderPartialTicks());
        float yAdd = entityIn.height + 0.5f - (entityIn.isSneaking() ? 0.25f : 0.0f);
        double x = interp.x;
        double y = interp.y + (double)yAdd;
        double z = interp.z;
        float viewerYaw = Nametags.mc.getRenderManager().playerViewY;
        float viewerPitch = Nametags.mc.getRenderManager().playerViewX;
        boolean isThirdPersonFrontal = Nametags.mc.getRenderManager().options.thirdPersonView == 2;
        GlStateManager.translate((double)x, (double)y, (double)z);
        GlStateManager.rotate((float)(-viewerYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch), (float)1.0f, (float)0.0f, (float)0.0f);
        float f = Nametags.mc.player.getDistance(entityIn);
        float m = f / 8.0f * (float)Math.pow(1.258925437927246, this.scale.getValue().floatValue());
        GlStateManager.scale((float)m, (float)m, (float)m);
        FontRenderer fontRendererIn = Nametags.mc.fontRenderer;
        GlStateManager.scale((float)-0.025f, (float)-0.025f, (float)0.025f);
        String str = entityIn.getName() + (this.health.getValue() != false ? " " + Command.SECTIONSIGN() + "c" + Math.round(((EntityLivingBase)entityIn).getHealth() + (entityIn instanceof EntityPlayer ? ((EntityPlayer)entityIn).getAbsorptionAmount() : 0.0f)) : "");
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GL11.glTranslatef((float)0.0f, (float)-20.0f, (float)0.0f);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferbuilder.pos((double)(-i - 1), 19.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferbuilder.pos((double)(i + 1), 19.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferbuilder.pos((double)(i + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        tessellator.draw();
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), 8.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferbuilder.pos((double)(-i - 1), 19.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferbuilder.pos((double)(i + 1), 19.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferbuilder.pos((double)(i + 1), 8.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        fontRendererIn.drawString(str, -i, 10, entityIn instanceof EntityPlayer ? (Friends.isFriend(entityIn.getName()) ? 0x11EE11 : 0xFFFFFF) : 0xFFFFFF);
        GlStateManager.glNormal3f((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)20.0f, (float)0.0f);
        GlStateManager.scale((float)-40.0f, (float)-40.0f, (float)40.0f);
        ArrayList equipment = new ArrayList();
        entityIn.getHeldEquipment().forEach(itemStack -> {
            if (itemStack != null) {
                equipment.add(itemStack);
            }
        });
        ArrayList armour = new ArrayList();
        entityIn.getArmorInventoryList().forEach(itemStack -> {
            if (itemStack != null) {
                armour.add(itemStack);
            }
        });
        Collections.reverse(armour);
        equipment.addAll(armour);
        if (equipment.size() == 0) {
            GlStateManager.popMatrix();
            return;
        }
        Collection a = equipment.stream().filter(itemStack -> !itemStack.isEmpty()).collect(Collectors.toList());
        GlStateManager.translate((double)((float)(a.size() - 1) / 2.0f * 0.5f), (double)0.6, (double)0.0);
        a.forEach(itemStack -> {
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.scale((double)0.5, (double)0.5, (double)0.0);
            GlStateManager.disableLighting();
            this.itemRenderer.zLevel = -5.0f;
            this.itemRenderer.renderItem(itemStack, itemStack.getItem() == Items.SHIELD ? ItemCameraTransforms.TransformType.FIXED : ItemCameraTransforms.TransformType.NONE);
            this.itemRenderer.zLevel = 0.0f;
            GlStateManager.scale((float)2.0f, (float)2.0f, (float)0.0f);
            GlStateManager.popAttrib();
            GlStateManager.translate((float)-0.5f, (float)0.0f, (float)0.0f);
        });
        GlStateManager.popMatrix();
    }
}

