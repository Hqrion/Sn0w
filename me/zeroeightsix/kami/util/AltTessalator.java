//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Sphere
 */
package me.zeroeightsix.kami.util;

import java.awt.Color;
import java.util.Objects;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class AltTessalator
extends Tessellator {
    private static final Frustum frustrum = new Frustum();
    private static final Minecraft mc;
    public static AltTessalator INSTANCE;

    public AltTessalator() {
        super(0x200000);
    }

    public static boolean isInViewFrustrum(AxisAlignedBB axisAlignedBB) {
        Entity getRenderViewEntity = mc.getRenderViewEntity();
        frustrum.setPosition(Objects.requireNonNull(getRenderViewEntity).posX, getRenderViewEntity.posY, getRenderViewEntity.posZ);
        return frustrum.isBoundingBoxInFrustum(axisAlignedBB);
    }

    public static void drawGradientFilledBox(BlockPos blockPos, Color color, Color color2) {
        IBlockState getBlockState = AltTessalator.mc.world.getBlockState(blockPos);
        Vec3d interpolateEntity = EntityUtil.getInterpolatedAmount((Entity)AltTessalator.mc.player, mc.getRenderPartialTicks());
        AltTessalator.drawGradientFilledBox(getBlockState.getSelectedBoundingBox((World)AltTessalator.mc.world, blockPos).grow((double)0.002f).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z), color, color2);
    }

    public static void drawGradientFilledBox(AxisAlignedBB axisAlignedBB, Color color, Color color2) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        float n = (float)color2.getAlpha() / 255.0f;
        float n2 = (float)color2.getRed() / 255.0f;
        float n3 = (float)color2.getGreen() / 255.0f;
        float n4 = (float)color2.getBlue() / 255.0f;
        float n5 = (float)color.getAlpha() / 255.0f;
        float n6 = (float)color.getRed() / 255.0f;
        float n7 = (float)color.getGreen() / 255.0f;
        float n8 = (float)color.getBlue() / 255.0f;
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n).endVertex();
        getInstance.draw();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static int generateRainbowFadingColor(int n, boolean b) {
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((float)(System.nanoTime() + (b ? 200000000L : 20000000L) * (long)n) / 4.0E9f % 1.0f, 0.95f, 0.95f)), 16);
    }

    public static void drawBlockOutline(AxisAlignedBB axisAlignedBB, Color color, float n) {
        float n2 = (float)color.getRed() / 255.0f;
        float n3 = (float)color.getGreen() / 255.0f;
        float n4 = (float)color.getBlue() / 255.0f;
        float n5 = (float)color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void drawFullBox(AxisAlignedBB axisAlignedBB, BlockPos blockPos, float n, int n2, int n3, int n4, int n5, int n6) {
        AltTessalator.prepare(7);
        AltTessalator.drawBox(blockPos, n2, n3, n4, n5, 63);
        AltTessalator.release();
        AltTessalator.drawBoundingBox(axisAlignedBB, n, n2, n3, n4, n6);
    }

    public static void drawBoundingBoxBottom2(BlockPos blockPos, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.getMinecraft();
        double n6 = (double)blockPos.getX() - getMinecraft.getRenderManager().viewerPosX;
        double n7 = (double)blockPos.getY() - getMinecraft.getRenderManager().viewerPosY;
        double n8 = (double)blockPos.getZ() - getMinecraft.getRenderManager().viewerPosZ;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 + 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawLine(float n, float n2, float n3, float n4, float n5, int n6) {
        float n7 = (float)(n6 >> 16 & 0xFF) / 255.0f;
        float n8 = (float)(n6 >> 8 & 0xFF) / 255.0f;
        float n9 = (float)(n6 & 0xFF) / 255.0f;
        float n10 = (float)(n6 >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GL11.glLineWidth((float)n5);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos((double)n, (double)n2, 0.0).color(n7, n8, n9, n10).endVertex();
        getBuffer.pos((double)n3, (double)n4, 0.0).color(n7, n8, n9, n10).endVertex();
        getInstance.draw();
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawSphere(double n, double n2, double n3, float n4, int n5, int n6) {
        Sphere sphere = new Sphere();
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.2f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        sphere.setDrawStyle(100013);
        GL11.glTranslated((double)(n - AltTessalator.mc.getRenderManager().renderPosX), (double)(n2 - AltTessalator.mc.getRenderManager().renderPosY), (double)(n3 - AltTessalator.mc.getRenderManager().renderPosZ));
        sphere.draw(n4, n5, n6);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawBox(BlockPos blockPos, int n, int n2) {
        AltTessalator.drawBox(blockPos, n >>> 16 & 0xFF, n >>> 8 & 0xFF, n & 0xFF, n >>> 24 & 0xFF, n2);
    }

    public static void drawBox(BufferBuilder bufferBuilder, float n, float n2, float n3, float n4, float n5, float n6, int n7, int n8, int n9, int n10, int n11) {
        if ((n11 & 1) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 2) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 4) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 8) != 0) {
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 0x10) != 0) {
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 0x20) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
    }

    public static void drawFullBox(AxisAlignedBB axisAlignedBB, BlockPos blockPos, float n, int n2, int n3) {
        AltTessalator.drawFullBox(axisAlignedBB, blockPos, n, n2 >>> 16 & 0xFF, n2 >>> 8 & 0xFF, n2 & 0xFF, n2 >>> 24 & 0xFF, n3);
    }

    public static void begin(int n) {
        INSTANCE.getBuffer().begin(n, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void render() {
        INSTANCE.draw();
    }

    public static void drawBoxxx(AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB == null) {
            return;
        }
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.maxY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.minZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.minX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glVertex3f((float)((float)axisAlignedBB.maxX), (float)((float)axisAlignedBB.minY), (float)((float)axisAlignedBB.maxZ));
        GlStateManager.glEnd();
    }

    public static void drawESPOutline(AxisAlignedBB axisAlignedBB, float n, float n2, float n3, float n4, float n5) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)n5);
        GL11.glColor4f((float)(n / 255.0f), (float)(n2 / 255.0f), (float)(n3 / 255.0f), (float)(n4 / 255.0f));
        AltTessalator.drawOutlinedBox(axisAlignedBB);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawBox2(BlockPos blockPos, int n, int n2) {
        AltTessalator.drawDownBox(blockPos, n >>> 16 & 0xFF, n >>> 8 & 0xFF, n & 0xFF, n >>> 24 & 0xFF, n2);
    }

    public static void drawBox(AxisAlignedBB axisAlignedBB, int n, int n2) {
        AltTessalator.drawBox(INSTANCE.getBuffer(), axisAlignedBB, n >>> 16 & 0xFF, n >>> 8 & 0xFF, n & 0xFF, n >>> 24 & 0xFF, n2);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return AltTessalator.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static void color(int n) {
        GL11.glColor4f((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
    }

    public static void prepareGL() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void prepare(int n) {
        AltTessalator.prepareGL();
        AltTessalator.begin(n);
    }

    public static void drawGradientBlockOutline(BlockPos blockPos, Color color, Color color2, float n) {
        IBlockState getBlockState = AltTessalator.mc.world.getBlockState(blockPos);
        Vec3d interpolateEntity = EntityUtil.getInterpolatedAmount((Entity)AltTessalator.mc.player, mc.getRenderPartialTicks());
        AltTessalator.drawGradientBlockOutline(getBlockState.getSelectedBoundingBox((World)AltTessalator.mc.world, blockPos).grow((double)0.002f).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z), color, color2, n);
    }

    public static void drawOutlinedBox(AxisAlignedBB axisAlignedBB) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glEnd();
    }

    public static int getRainbow(int n, int n2, float s, float b) {
        return Color.getHSBColor((float)((System.currentTimeMillis() + (long)n2) % (long)n) / (float)n, s, b).getRGB();
    }

    public static void glBillboardDistanceScaled(float n, float n2, float n3, EntityPlayer entityPlayer, float n4) {
        AltTessalator.glBillboard(n, n2, n3);
        float n5 = (float)((int)entityPlayer.getDistance((double)n, (double)n2, (double)n3)) / 2.0f / (2.0f + (2.0f - n4));
        if (n5 < 1.0f) {
            n5 = 1.0f;
        }
        GlStateManager.scale((float)n5, (float)n5, (float)n5);
    }

    public static void drawBoundingBox(AxisAlignedBB axisAlignedBB, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.glLineWidth((float)n);
        BufferBuilder getBuffer = INSTANCE.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        AltTessalator.render();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void glBillboard(float n, float n2, float n3) {
        float n4 = 0.02666667f;
        GlStateManager.translate((double)((double)n - Minecraft.getMinecraft().getRenderManager().renderPosX), (double)((double)n2 - Minecraft.getMinecraft().getRenderManager().renderPosY), (double)((double)n3 - Minecraft.getMinecraft().getRenderManager().renderPosZ));
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-Minecraft.getMinecraft().player.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)Minecraft.getMinecraft().player.rotationPitch, (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)-0.02666667f, (float)-0.02666667f, (float)0.02666667f);
    }

    public static void drawDownBox(BlockPos blockPos, int n, int n2, int n3, int n4, int n5) {
        AltTessalator.drawDownBox2(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f, 1.0f, n, n2, n3, n4, n5);
    }

    public static void drawDownBox2(BufferBuilder bufferBuilder, float n, float n2, float n3, float n4, float n5, float n6, int n7, int n8, int n9, int n10, int n11) {
        if ((n11 & 1) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 2) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 4) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 8) != 0) {
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 0x10) != 0) {
            bufferBuilder.pos((double)n, (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)n, (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
        }
        if ((n11 & 0x20) != 0) {
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)n2, (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)n3).color(n7, n8, n9, n10).endVertex();
            bufferBuilder.pos((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).color(n7, n8, n9, n10).endVertex();
        }
    }

    public static void drawBox(BufferBuilder bufferBuilder, AxisAlignedBB axisAlignedBB, int n, int n2, int n3, int n4, int n5) {
        if ((n5 & 1) != 0) {
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        }
        if ((n5 & 2) != 0) {
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        }
        if ((n5 & 4) != 0) {
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        }
        if ((n5 & 8) != 0) {
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        }
        if ((n5 & 0x10) != 0) {
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        }
        if ((n5 & 0x20) != 0) {
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
            bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        }
    }

    public static void fakeGuiRect(double n, double n2, double n3, double n4, int n5) {
        if (n < n3) {
            double n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        float n8 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float n9 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float n10 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float n11 = (float)(n5 & 0xFF) / 255.0f;
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)n9, (float)n10, (float)n11, (float)n8);
        getBuffer.begin(7, DefaultVertexFormats.POSITION);
        getBuffer.pos(n, n4, 0.0).endVertex();
        getBuffer.pos(n3, n4, 0.0).endVertex();
        getBuffer.pos(n3, n2, 0.0).endVertex();
        getBuffer.pos(n, n2, 0.0).endVertex();
        getInstance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBoundingBox(AxisAlignedBB axisAlignedBB, float n, int n2) {
        AltTessalator.drawBoundingBox(axisAlignedBB, n, n2 >>> 16 & 0xFF, n2 >>> 8 & 0xFF, n2 & 0xFF, n2 >>> 24 & 0xFF);
    }

    public static void drawBoundingBoxBlockPos2(BlockPos blockPos, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.getMinecraft();
        double n6 = (double)blockPos.getX() - getMinecraft.getRenderManager().viewerPosX;
        double n7 = (double)blockPos.getY() - getMinecraft.getRenderManager().viewerPosY;
        double n8 = (double)blockPos.getZ() - getMinecraft.getRenderManager().viewerPosZ;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 - 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        getBuffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRect(float n, float n2, float n3, float n4, int n5) {
        float n6 = n + n3;
        float n7 = n2 + n4;
        if (n < n6) {
            float n8 = n;
            n = n6;
            n6 = n8;
        }
        if (n2 < n7) {
            float n9 = n2;
            n2 = n7;
            n7 = n9;
        }
        float n10 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float n11 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float n12 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float n13 = (float)(n5 & 0xFF) / 255.0f;
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)n11, (float)n12, (float)n13, (float)n10);
        getBuffer.begin(7, DefaultVertexFormats.POSITION);
        getBuffer.pos((double)n, (double)n7, 0.0).endVertex();
        getBuffer.pos((double)n6, (double)n7, 0.0).endVertex();
        getBuffer.pos((double)n6, (double)n2, 0.0).endVertex();
        getBuffer.pos((double)n, (double)n2, 0.0).endVertex();
        getInstance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBoundingBoxBlockPos(BlockPos blockPos, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.getMinecraft();
        double n6 = (double)blockPos.getX() - getMinecraft.getRenderManager().viewerPosX;
        double n7 = (double)blockPos.getY() - getMinecraft.getRenderManager().viewerPosY;
        double n8 = (double)blockPos.getZ() - getMinecraft.getRenderManager().viewerPosZ;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 + 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        getBuffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawESP(AxisAlignedBB axisAlignedBB, float n, float n2, float n3, float n4) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)(n / 255.0f), (float)(n2 / 255.0f), (float)(n3 / 255.0f), (float)(n4 / 255.0f));
        AltTessalator.drawBoxxx(axisAlignedBB);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void drawGradientBlockOutline(AxisAlignedBB axisAlignedBB, Color color, Color color2, float n) {
        float n2 = (float)color.getRed() / 255.0f;
        float n3 = (float)color.getGreen() / 255.0f;
        float n4 = (float)color.getBlue() / 255.0f;
        float n5 = (float)color.getAlpha() / 255.0f;
        float n6 = (float)color2.getRed() / 255.0f;
        float n7 = (float)color2.getGreen() / 255.0f;
        float n8 = (float)color2.getBlue() / 255.0f;
        float n9 = (float)color2.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Tessellator getInstance = Tessellator.getInstance();
        BufferBuilder getBuffer = getInstance.getBuffer();
        getBuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n6, n7, n8, n9).endVertex();
        getBuffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getBuffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n2, n3, n4, n5).endVertex();
        getInstance.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBorderedRect(double n, double n2, double n3, double n4, double n5, int n6, int n7) {
        AltTessalator.enableGL2D();
        AltTessalator.fakeGuiRect(n + n5, n2 + n5, n3 - n5, n4 - n5, n6);
        AltTessalator.fakeGuiRect(n + n5, n2, n3 - n5, n2 + n5, n7);
        AltTessalator.fakeGuiRect(n, n2, n + n5, n4, n7);
        AltTessalator.fakeGuiRect(n3 - n5, n2, n3, n4, n7);
        AltTessalator.fakeGuiRect(n + n5, n4 - n5, n3 - n5, n4, n7);
        AltTessalator.disableGL2D();
    }

    public static void drawBox(BlockPos blockPos, int n, int n2, int n3, int n4, int n5) {
        AltTessalator.drawBox(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f, 1.0f, n, n2, n3, n4, n5);
    }

    public static void release() {
        AltTessalator.render();
        AltTessalator.releaseGL();
    }

    static {
        INSTANCE = new AltTessalator();
        mc = Wrapper.getMinecraft();
    }
}

