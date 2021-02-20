/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(name="EyeFinder", description="Draw lines from entity's heads to where they are looking", category=Module.Category.HIDDEN)
public class EyeFinder
extends Module {
    private Setting<Boolean> players = this.register(Settings.b("Players", true));
    private Setting<Boolean> friends = this.register(Settings.b("Friends", false));
    private Setting<Boolean> mobs = this.register(Settings.b("Mobs", false));
    private Setting<Boolean> animals = this.register(Settings.b("Animals", false));
    private Setting<Integer> renderAlphaTracer = this.register(Settings.integerBuilder("Render Alpha").withMinimum(0).withValue(128).withMaximum(255).build());
    private Setting<Boolean> block = this.register(Settings.b("Block", true));
    private Setting<Integer> renderAlphaBlock = this.register(Settings.integerBuilder("Render Alpha").withMinimum(0).withValue(128).withMaximum(255).build());

    @Override
    public void onWorldRender(RenderEvent event) {
        EyeFinder.mc.field_71441_e.field_72996_f.stream().filter(EntityUtil::isLiving).filter(entity -> EyeFinder.mc.field_71439_g != entity).map(entity -> (EntityLivingBase)entity).filter(entityLivingBase -> !entityLivingBase.field_70128_L).filter(entity -> this.players.getValue() != false && entity instanceof EntityPlayer || (EntityUtil.isPassive((Entity)entity) ? this.animals.getValue() : this.mobs.getValue()) != false).forEach(this::drawRender);
    }

    private void drawRender(EntityLivingBase entity) {
        RayTraceResult result = entity.func_174822_a(6.0, Minecraft.func_71410_x().func_184121_ak());
        if (result == null) {
            return;
        }
        int red = 104;
        int green = 12;
        int blue = 35;
        if (entity instanceof EntityPlayer && Friends.isFriend(entity.func_70005_c_())) {
            if (!this.friends.getValue().booleanValue()) {
                return;
            }
            red = 81;
            green = 12;
            blue = 104;
        }
        Vec3d eyes = entity.func_174824_e(Minecraft.func_71410_x().func_184121_ak());
        GlStateManager.func_179126_j();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        double posx = eyes.field_72450_a - EyeFinder.mc.func_175598_ae().field_78725_b;
        double posy = eyes.field_72448_b - EyeFinder.mc.func_175598_ae().field_78726_c;
        double posz = eyes.field_72449_c - EyeFinder.mc.func_175598_ae().field_78723_d;
        double posx2 = result.field_72307_f.field_72450_a - EyeFinder.mc.func_175598_ae().field_78725_b;
        double posy2 = result.field_72307_f.field_72448_b - EyeFinder.mc.func_175598_ae().field_78726_c;
        double posz2 = result.field_72307_f.field_72449_c - EyeFinder.mc.func_175598_ae().field_78723_d;
        GL11.glColor4f((float)((float)red / 255.0f), (float)((float)green / 255.0f), (float)((float)blue / 255.0f), (float)((float)this.renderAlphaTracer.getValue().intValue() / 255.0f));
        GlStateManager.func_187441_d((float)1.5f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glEnd();
        if (this.block.getValue().booleanValue() && result.field_72313_a == RayTraceResult.Type.BLOCK) {
            KamiTessellator.prepare(7);
            GL11.glEnable((int)2929);
            BlockPos b = result.func_178782_a();
            float x = b.field_177962_a;
            float y = b.field_177960_b;
            float z = b.field_177961_c;
            KamiTessellator.drawBox(KamiTessellator.getBufferBuilder(), x, y, z, 1.01f, 1.01f, 1.01f, red, green, blue, this.renderAlphaBlock.getValue(), 63);
            KamiTessellator.release();
        }
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
    }
}

