/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.HueCycler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(name="Tracers", description="Draws lines to other living entities", category=Module.Category.RENDER)
public class Tracers
extends Module {
    HueCycler cycler = new HueCycler(3600);
    private Setting<Boolean> players = this.register(Settings.b("Players", true));
    private Setting<Boolean> friends = this.register(Settings.b("Friends", true));
    private Setting<Boolean> animals = this.register(Settings.b("Animals", false));
    private Setting<Boolean> mobs = this.register(Settings.b("Mobs", false));
    private Setting<Double> range = this.register(Settings.d("Range", 200.0));
    private float opacity = 1.0f;

    public static double interpolate(double now, double then) {
        return then + (now - then) * (double)mc.func_184121_ak();
    }

    public static double[] interpolate(Entity entity) {
        double posX = Tracers.interpolate(entity.field_70165_t, entity.field_70142_S) - Tracers.mc.func_175598_ae().field_78725_b;
        double posY = Tracers.interpolate(entity.field_70163_u, entity.field_70137_T) - Tracers.mc.func_175598_ae().field_78726_c;
        double posZ = Tracers.interpolate(entity.field_70161_v, entity.field_70136_U) - Tracers.mc.func_175598_ae().field_78723_d;
        return new double[]{posX, posY, posZ};
    }

    public static void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
        double[] xyz = Tracers.interpolate(e);
        Tracers.drawLine(xyz[0], xyz[1], xyz[2], e.field_70131_O, red, green, blue, opacity);
    }

    public static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Minecraft.func_71410_x().field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians(Minecraft.func_71410_x().field_71439_g.field_70177_z)));
        Tracers.drawLineFromPosToPos(eyes.field_72450_a, eyes.field_72448_b + (double)Tracers.mc.field_71439_g.func_70047_e(), eyes.field_72449_c, posx, posy, posz, up, red, green, blue, opacity);
    }

    public static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.func_179140_f();
        GL11.glLoadIdentity();
        Tracers.mc.field_71460_t.func_78467_g(mc.func_184121_ak());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)(posy2 + up), (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.func_179145_e();
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        GlStateManager.func_179094_E();
        Minecraft.func_71410_x().field_71441_e.field_72996_f.stream().filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity)).filter(entity -> entity instanceof EntityPlayer ? this.players.getValue().booleanValue() && Tracers.mc.field_71439_g != entity : (EntityUtil.isPassive(entity) ? this.animals.getValue().booleanValue() : this.mobs.getValue().booleanValue())).filter(entity -> (double)Tracers.mc.field_71439_g.func_70032_d(entity) < this.range.getValue()).forEach(entity -> {
            int colour2 = this.getColour((Entity)entity);
            if (colour2 == Integer.MIN_VALUE) {
                if (!this.friends.getValue().booleanValue()) {
                    return;
                }
                colour2 = this.cycler.current();
            }
            float r = (float)(colour2 >>> 16 & 0xFF) / 255.0f;
            float g = (float)(colour2 >>> 8 & 0xFF) / 255.0f;
            float b = (float)(colour2 & 0xFF) / 255.0f;
            Tracers.drawLineToEntity(entity, r, g, b, this.opacity);
        });
        GlStateManager.func_179121_F();
    }

    @Override
    public void onUpdate() {
        this.cycler.next();
    }

    private void drawRainbowToEntity(Entity entity, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).func_178789_a(-((float)Math.toRadians(Minecraft.func_71410_x().field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians(Minecraft.func_71410_x().field_71439_g.field_70177_z)));
        double[] xyz = Tracers.interpolate(entity);
        double posx = xyz[0];
        double posy = xyz[1];
        double posz = xyz[2];
        double posx2 = eyes.field_72450_a;
        double posy2 = eyes.field_72448_b + (double)Tracers.mc.field_71439_g.func_70047_e();
        double posz2 = eyes.field_72449_c;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        this.cycler.reset();
        this.cycler.setNext(opacity);
        GlStateManager.func_179140_f();
        GL11.glLoadIdentity();
        Tracers.mc.field_71460_t.func_78467_g(mc.func_184121_ak());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        this.cycler.setNext(opacity);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.func_179145_e();
    }

    private int getColour(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return Friends.isFriend(entity.func_70005_c_()) ? Integer.MIN_VALUE : ColourUtils.Colors.WHITE;
        }
        if (EntityUtil.isPassive(entity)) {
            return ColourUtils.Colors.GREEN;
        }
        return ColourUtils.Colors.RED;
    }
}

