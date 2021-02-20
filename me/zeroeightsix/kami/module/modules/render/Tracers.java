//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        return then + (now - then) * (double)mc.getRenderPartialTicks();
    }

    public static double[] interpolate(Entity entity) {
        double posX = Tracers.interpolate(entity.posX, entity.lastTickPosX) - Tracers.mc.getRenderManager().renderPosX;
        double posY = Tracers.interpolate(entity.posY, entity.lastTickPosY) - Tracers.mc.getRenderManager().renderPosY;
        double posZ = Tracers.interpolate(entity.posZ, entity.lastTickPosZ) - Tracers.mc.getRenderManager().renderPosZ;
        return new double[]{posX, posY, posZ};
    }

    public static void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
        double[] xyz = Tracers.interpolate(e);
        Tracers.drawLine(xyz[0], xyz[1], xyz[2], e.height, red, green, blue, opacity);
    }

    public static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));
        Tracers.drawLineFromPosToPos(eyes.x, eyes.y + (double)Tracers.mc.player.getEyeHeight(), eyes.z, posx, posy, posz, up, red, green, blue, opacity);
    }

    public static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        Tracers.mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
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
        GlStateManager.enableLighting();
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity)).filter(entity -> entity instanceof EntityPlayer ? this.players.getValue().booleanValue() && Tracers.mc.player != entity : (EntityUtil.isPassive(entity) ? this.animals.getValue().booleanValue() : this.mobs.getValue().booleanValue())).filter(entity -> (double)Tracers.mc.player.getDistance(entity) < this.range.getValue()).forEach(entity -> {
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
        GlStateManager.popMatrix();
    }

    @Override
    public void onUpdate() {
        this.cycler.next();
    }

    private void drawRainbowToEntity(Entity entity, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));
        double[] xyz = Tracers.interpolate(entity);
        double posx = xyz[0];
        double posy = xyz[1];
        double posz = xyz[2];
        double posx2 = eyes.x;
        double posy2 = eyes.y + (double)Tracers.mc.player.getEyeHeight();
        double posz2 = eyes.z;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        this.cycler.reset();
        this.cycler.setNext(opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        Tracers.mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
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
        GlStateManager.enableLighting();
    }

    private int getColour(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return Friends.isFriend(entity.getName()) ? Integer.MIN_VALUE : ColourUtils.Colors.WHITE;
        }
        if (EntityUtil.isPassive(entity)) {
            return ColourUtils.Colors.GREEN;
        }
        return ColourUtils.Colors.RED;
    }
}

