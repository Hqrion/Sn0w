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
    RenderItem itemRenderer = mc.func_175599_af();

    @Override
    public void onWorldRender(RenderEvent event) {
        if (Nametags.mc.func_175598_ae().field_78733_k == null) {
            return;
        }
        GlStateManager.func_179098_w();
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        Minecraft.func_71410_x().field_71441_e.field_72996_f.stream().filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity)).filter(entity -> entity instanceof EntityPlayer ? this.players.getValue().booleanValue() && Nametags.mc.field_71439_g != entity : (EntityUtil.isPassive(entity) ? this.animals.getValue().booleanValue() : this.mobs.getValue().booleanValue())).filter(entity -> (double)Nametags.mc.field_71439_g.func_70032_d(entity) < this.range.getValue()).sorted(Comparator.comparing(entity -> Float.valueOf(-Nametags.mc.field_71439_g.func_70032_d(entity)))).forEach(this::drawNametag);
        GlStateManager.func_179090_x();
        RenderHelper.func_74518_a();
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
    }

    private void drawNametag(Entity entityIn) {
        GlStateManager.func_179094_E();
        Vec3d interp = EntityUtil.getInterpolatedRenderPos(entityIn, mc.func_184121_ak());
        float yAdd = entityIn.field_70131_O + 0.5f - (entityIn.func_70093_af() ? 0.25f : 0.0f);
        double x = interp.field_72450_a;
        double y = interp.field_72448_b + (double)yAdd;
        double z = interp.field_72449_c;
        float viewerYaw = Nametags.mc.func_175598_ae().field_78735_i;
        float viewerPitch = Nametags.mc.func_175598_ae().field_78732_j;
        boolean isThirdPersonFrontal = Nametags.mc.func_175598_ae().field_78733_k.field_74320_O == 2;
        GlStateManager.func_179137_b((double)x, (double)y, (double)z);
        GlStateManager.func_179114_b((float)(-viewerYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch), (float)1.0f, (float)0.0f, (float)0.0f);
        float f = Nametags.mc.field_71439_g.func_70032_d(entityIn);
        float m = f / 8.0f * (float)Math.pow(1.258925437927246, this.scale.getValue().floatValue());
        GlStateManager.func_179152_a((float)m, (float)m, (float)m);
        FontRenderer fontRendererIn = Nametags.mc.field_71466_p;
        GlStateManager.func_179152_a((float)-0.025f, (float)-0.025f, (float)0.025f);
        String str = entityIn.func_70005_c_() + (this.health.getValue() != false ? " " + Command.SECTIONSIGN() + "c" + Math.round(((EntityLivingBase)entityIn).func_110143_aJ() + (entityIn instanceof EntityPlayer ? ((EntityPlayer)entityIn).func_110139_bj() : 0.0f)) : "");
        int i = fontRendererIn.func_78256_a(str) / 2;
        GlStateManager.func_179147_l();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179090_x();
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GL11.glTranslatef((float)0.0f, (float)-20.0f, (float)0.0f);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)(-i - 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.5f).func_181675_d();
        bufferbuilder.func_181662_b((double)(-i - 1), 19.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.5f).func_181675_d();
        bufferbuilder.func_181662_b((double)(i + 1), 19.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.5f).func_181675_d();
        bufferbuilder.func_181662_b((double)(i + 1), 8.0, 0.0).func_181666_a(0.0f, 0.0f, 0.0f, 0.5f).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(2, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)(-i - 1), 8.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, 0.1f).func_181675_d();
        bufferbuilder.func_181662_b((double)(-i - 1), 19.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, 0.1f).func_181675_d();
        bufferbuilder.func_181662_b((double)(i + 1), 19.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, 0.1f).func_181675_d();
        bufferbuilder.func_181662_b((double)(i + 1), 8.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, 0.1f).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_187432_a((float)0.0f, (float)1.0f, (float)0.0f);
        fontRendererIn.func_78276_b(str, -i, 10, entityIn instanceof EntityPlayer ? (Friends.isFriend(entityIn.func_70005_c_()) ? 0x11EE11 : 0xFFFFFF) : 0xFFFFFF);
        GlStateManager.func_187432_a((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)20.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)-40.0f, (float)-40.0f, (float)40.0f);
        ArrayList equipment = new ArrayList();
        entityIn.func_184214_aD().forEach(itemStack -> {
            if (itemStack != null) {
                equipment.add(itemStack);
            }
        });
        ArrayList armour = new ArrayList();
        entityIn.func_184193_aE().forEach(itemStack -> {
            if (itemStack != null) {
                armour.add(itemStack);
            }
        });
        Collections.reverse(armour);
        equipment.addAll(armour);
        if (equipment.size() == 0) {
            GlStateManager.func_179121_F();
            return;
        }
        Collection a = equipment.stream().filter(itemStack -> !itemStack.func_190926_b()).collect(Collectors.toList());
        GlStateManager.func_179137_b((double)((float)(a.size() - 1) / 2.0f * 0.5f), (double)0.6, (double)0.0);
        a.forEach(itemStack -> {
            GlStateManager.func_179123_a();
            RenderHelper.func_74519_b();
            GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.0);
            GlStateManager.func_179140_f();
            this.itemRenderer.field_77023_b = -5.0f;
            this.itemRenderer.func_181564_a(itemStack, itemStack.func_77973_b() == Items.field_185159_cQ ? ItemCameraTransforms.TransformType.FIXED : ItemCameraTransforms.TransformType.NONE);
            this.itemRenderer.field_77023_b = 0.0f;
            GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)0.0f);
            GlStateManager.func_179099_b();
            GlStateManager.func_179109_b((float)-0.5f, (float)0.0f, (float)0.0f);
        });
        GlStateManager.func_179121_F();
    }
}

