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
        Entity getRenderViewEntity = mc.func_175606_aa();
        frustrum.func_78547_a(Objects.requireNonNull(getRenderViewEntity).field_70165_t, getRenderViewEntity.field_70163_u, getRenderViewEntity.field_70161_v);
        return frustrum.func_78546_a(axisAlignedBB);
    }

    public static void drawGradientFilledBox(BlockPos blockPos, Color color, Color color2) {
        IBlockState getBlockState = AltTessalator.mc.field_71441_e.func_180495_p(blockPos);
        Vec3d interpolateEntity = EntityUtil.getInterpolatedAmount((Entity)AltTessalator.mc.field_71439_g, mc.func_184121_ak());
        AltTessalator.drawGradientFilledBox(getBlockState.func_185918_c((World)AltTessalator.mc.field_71441_e, blockPos).func_186662_g((double)0.002f).func_72317_d(-interpolateEntity.field_72450_a, -interpolateEntity.field_72448_b, -interpolateEntity.field_72449_c), color, color2);
    }

    public static void drawGradientFilledBox(AxisAlignedBB axisAlignedBB, Color color, Color color2) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        float n = (float)color2.getAlpha() / 255.0f;
        float n2 = (float)color2.getRed() / 255.0f;
        float n3 = (float)color2.getGreen() / 255.0f;
        float n4 = (float)color2.getBlue() / 255.0f;
        float n5 = (float)color.getAlpha() / 255.0f;
        float n6 = (float)color.getRed() / 255.0f;
        float n7 = (float)color.getGreen() / 255.0f;
        float n8 = (float)color.getBlue() / 255.0f;
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n).func_181675_d();
        getInstance.func_78381_a();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static int generateRainbowFadingColor(int n, boolean b) {
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((float)(System.nanoTime() + (b ? 200000000L : 20000000L) * (long)n) / 4.0E9f % 1.0f, 0.95f, 0.95f)), 16);
    }

    public static void drawBlockOutline(AxisAlignedBB axisAlignedBB, Color color, float n) {
        float n2 = (float)color.getRed() / 255.0f;
        float n3 = (float)color.getGreen() / 255.0f;
        float n4 = (float)color.getBlue() / 255.0f;
        float n5 = (float)color.getAlpha() / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
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
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.func_71410_x();
        double n6 = (double)blockPos.func_177958_n() - getMinecraft.func_175598_ae().field_78730_l;
        double n7 = (double)blockPos.func_177956_o() - getMinecraft.func_175598_ae().field_78731_m;
        double n8 = (double)blockPos.func_177952_p() - getMinecraft.func_175598_ae().field_78728_n;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 + 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static void drawLine(float n, float n2, float n3, float n4, float n5, int n6) {
        float n7 = (float)(n6 >> 16 & 0xFF) / 255.0f;
        float n8 = (float)(n6 >> 8 & 0xFF) / 255.0f;
        float n9 = (float)(n6 & 0xFF) / 255.0f;
        float n10 = (float)(n6 >> 24 & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        GL11.glLineWidth((float)n5);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b((double)n, (double)n2, 0.0).func_181666_a(n7, n8, n9, n10).func_181675_d();
        getBuffer.func_181662_b((double)n3, (double)n4, 0.0).func_181666_a(n7, n8, n9, n10).func_181675_d();
        getInstance.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
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
        GL11.glTranslated((double)(n - AltTessalator.mc.func_175598_ae().field_78725_b), (double)(n2 - AltTessalator.mc.func_175598_ae().field_78726_c), (double)(n3 - AltTessalator.mc.func_175598_ae().field_78723_d));
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
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 2) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 4) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 8) != 0) {
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 0x10) != 0) {
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 0x20) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 + n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
    }

    public static void drawFullBox(AxisAlignedBB axisAlignedBB, BlockPos blockPos, float n, int n2, int n3) {
        AltTessalator.drawFullBox(axisAlignedBB, blockPos, n, n2 >>> 16 & 0xFF, n2 >>> 8 & 0xFF, n2 & 0xFF, n2 >>> 24 & 0xFF, n3);
    }

    public static void begin(int n) {
        INSTANCE.func_178180_c().func_181668_a(n, DefaultVertexFormats.field_181706_f);
    }

    public static void render() {
        INSTANCE.func_78381_a();
    }

    public static void drawBoxxx(AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB == null) {
            return;
        }
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72337_e), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
        GlStateManager.func_187447_r((int)7);
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72339_c));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72340_a), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187435_e((float)((float)axisAlignedBB.field_72336_d), (float)((float)axisAlignedBB.field_72338_b), (float)((float)axisAlignedBB.field_72334_f));
        GlStateManager.func_187437_J();
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
        GlStateManager.func_179089_o();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179126_j();
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawBox2(BlockPos blockPos, int n, int n2) {
        AltTessalator.drawDownBox(blockPos, n >>> 16 & 0xFF, n >>> 8 & 0xFF, n & 0xFF, n >>> 24 & 0xFF, n2);
    }

    public static void drawBox(AxisAlignedBB axisAlignedBB, int n, int n2) {
        AltTessalator.drawBox(INSTANCE.func_178180_c(), axisAlignedBB, n >>> 16 & 0xFF, n >>> 8 & 0xFF, n & 0xFF, n >>> 24 & 0xFF, n2);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return AltTessalator.isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak;
    }

    public static void color(int n) {
        GL11.glColor4f((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
    }

    public static void prepareGL() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_187441_d((float)1.5f);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179141_d();
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void prepare(int n) {
        AltTessalator.prepareGL();
        AltTessalator.begin(n);
    }

    public static void drawGradientBlockOutline(BlockPos blockPos, Color color, Color color2, float n) {
        IBlockState getBlockState = AltTessalator.mc.field_71441_e.func_180495_p(blockPos);
        Vec3d interpolateEntity = EntityUtil.getInterpolatedAmount((Entity)AltTessalator.mc.field_71439_g, mc.func_184121_ak());
        AltTessalator.drawGradientBlockOutline(getBlockState.func_185918_c((World)AltTessalator.mc.field_71441_e, blockPos).func_186662_g((double)0.002f).func_72317_d(-interpolateEntity.field_72450_a, -interpolateEntity.field_72448_b, -interpolateEntity.field_72449_c), color, color2, n);
    }

    public static void drawOutlinedBox(AxisAlignedBB axisAlignedBB) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72338_b, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72336_d, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72334_f);
        GL11.glVertex3d((double)axisAlignedBB.field_72340_a, (double)axisAlignedBB.field_72337_e, (double)axisAlignedBB.field_72339_c);
        GL11.glEnd();
    }

    public static int getRainbow(int n, int n2, float s, float b) {
        return Color.getHSBColor((float)((System.currentTimeMillis() + (long)n2) % (long)n) / (float)n, s, b).getRGB();
    }

    public static void glBillboardDistanceScaled(float n, float n2, float n3, EntityPlayer entityPlayer, float n4) {
        AltTessalator.glBillboard(n, n2, n3);
        float n5 = (float)((int)entityPlayer.func_70011_f((double)n, (double)n2, (double)n3)) / 2.0f / (2.0f + (2.0f - n4));
        if (n5 < 1.0f) {
            n5 = 1.0f;
        }
        GlStateManager.func_179152_a((float)n5, (float)n5, (float)n5);
    }

    public static void drawBoundingBox(AxisAlignedBB axisAlignedBB, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_187441_d((float)n);
        BufferBuilder getBuffer = INSTANCE.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        AltTessalator.render();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static void glBillboard(float n, float n2, float n3) {
        float n4 = 0.02666667f;
        GlStateManager.func_179137_b((double)((double)n - Minecraft.func_71410_x().func_175598_ae().field_78725_b), (double)((double)n2 - Minecraft.func_71410_x().func_175598_ae().field_78726_c), (double)((double)n3 - Minecraft.func_71410_x().func_175598_ae().field_78723_d));
        GlStateManager.func_187432_a((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(-Minecraft.func_71410_x().field_71439_g.field_70177_z), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)Minecraft.func_71410_x().field_71439_g.field_70125_A, (float)(Minecraft.func_71410_x().field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)-0.02666667f, (float)-0.02666667f, (float)0.02666667f);
    }

    public static void drawDownBox(BlockPos blockPos, int n, int n2, int n3, int n4, int n5) {
        AltTessalator.drawDownBox2(INSTANCE.func_178180_c(), blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), 1.0f, 1.0f, 1.0f, n, n2, n3, n4, n5);
    }

    public static void drawDownBox2(BufferBuilder bufferBuilder, float n, float n2, float n3, float n4, float n5, float n6, int n7, int n8, int n9, int n10, int n11) {
        if ((n11 & 1) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 2) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 4) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 8) != 0) {
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 0x10) != 0) {
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)n, (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
        if ((n11 & 0x20) != 0) {
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)n2, (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)n3).func_181669_b(n7, n8, n9, n10).func_181675_d();
            bufferBuilder.func_181662_b((double)(n + n4), (double)(n2 - n5), (double)(n3 + n6)).func_181669_b(n7, n8, n9, n10).func_181675_d();
        }
    }

    public static void drawBox(BufferBuilder bufferBuilder, AxisAlignedBB axisAlignedBB, int n, int n2, int n3, int n4, int n5) {
        if ((n5 & 1) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
        }
        if ((n5 & 2) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
        }
        if ((n5 & 4) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
        }
        if ((n5 & 8) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
        }
        if ((n5 & 0x10) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
        }
        if ((n5 & 0x20) != 0) {
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n, n2, n3, n4).func_181675_d();
            bufferBuilder.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n, n2, n3, n4).func_181675_d();
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
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c((float)n9, (float)n10, (float)n11, (float)n8);
        getBuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        getBuffer.func_181662_b(n, n4, 0.0).func_181675_d();
        getBuffer.func_181662_b(n3, n4, 0.0).func_181675_d();
        getBuffer.func_181662_b(n3, n2, 0.0).func_181675_d();
        getBuffer.func_181662_b(n, n2, 0.0).func_181675_d();
        getInstance.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawBoundingBox(AxisAlignedBB axisAlignedBB, float n, int n2) {
        AltTessalator.drawBoundingBox(axisAlignedBB, n, n2 >>> 16 & 0xFF, n2 >>> 8 & 0xFF, n2 & 0xFF, n2 >>> 24 & 0xFF);
    }

    public static void drawBoundingBoxBlockPos2(BlockPos blockPos, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.func_71410_x();
        double n6 = (double)blockPos.func_177958_n() - getMinecraft.func_175598_ae().field_78730_l;
        double n7 = (double)blockPos.func_177956_o() - getMinecraft.func_175598_ae().field_78731_m;
        double n8 = (double)blockPos.func_177952_p() - getMinecraft.func_175598_ae().field_78728_n;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 - 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        getBuffer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
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
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c((float)n11, (float)n12, (float)n13, (float)n10);
        getBuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        getBuffer.func_181662_b((double)n, (double)n7, 0.0).func_181675_d();
        getBuffer.func_181662_b((double)n6, (double)n7, 0.0).func_181675_d();
        getBuffer.func_181662_b((double)n6, (double)n2, 0.0).func_181675_d();
        getBuffer.func_181662_b((double)n, (double)n2, 0.0).func_181675_d();
        getInstance.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawBoundingBoxBlockPos(BlockPos blockPos, float n, int n2, int n3, int n4, int n5) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Minecraft getMinecraft = Minecraft.func_71410_x();
        double n6 = (double)blockPos.func_177958_n() - getMinecraft.func_175598_ae().field_78730_l;
        double n7 = (double)blockPos.func_177956_o() - getMinecraft.func_175598_ae().field_78731_m;
        double n8 = (double)blockPos.func_177952_p() - getMinecraft.func_175598_ae().field_78728_n;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n6, n7, n8, n6 + 1.0, n7 + 1.0, n8 + 1.0);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        getBuffer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181669_b(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
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
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)n);
        Tessellator getInstance = Tessellator.func_178181_a();
        BufferBuilder getBuffer = getInstance.func_178180_c();
        getBuffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(n6, n7, n8, n9).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getBuffer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(n2, n3, n4, n5).func_181675_d();
        getInstance.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
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
        AltTessalator.drawBox(INSTANCE.func_178180_c(), blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), 1.0f, 1.0f, 1.0f, n, n2, n3, n4, n5);
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

