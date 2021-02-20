/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.util;

import java.awt.Color;
import me.zeroeightsix.kami.module.modules.render.Nametags3;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class KamiTessellator
extends Tessellator {
    public static KamiTessellator INSTANCE = new KamiTessellator();
    private Color lol;
    private static final Minecraft mc = Minecraft.func_71410_x();

    public KamiTessellator() {
        super(0x200000);
    }

    public static void prepare(int mode) {
        KamiTessellator.prepareGL();
        KamiTessellator.begin(mode);
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

    public static void drawNametag(Entity entity, String[] text, Color color, int type2) {
        Vec3d pos = EntityUtil.getInterpolatedPos(entity, Minecraft.func_71410_x().func_184121_ak());
        KamiTessellator.drawNametag(pos.field_72450_a, pos.field_72448_b + (double)entity.field_70131_O, pos.field_72449_c, text, color, type2);
    }

    public static void drawNametag(double x, double y, double z, String[] text, Color color, int type2) {
        double dist = KamiTessellator.mc.field_71439_g.func_70011_f(x, y, z);
        double scale = 1.0;
        double offset = 0.0;
        int start = 0;
        switch (type2) {
            case 0: {
                scale = dist / 20.0 * Math.pow(1.2589254, 0.1 / (dist < 25.0 ? 0.5 : 2.0));
                scale = Math.min(Math.max(scale, 0.5), 5.0);
                offset = scale > 2.0 ? scale / 2.0 : scale;
                scale /= 40.0;
                start = 10;
                break;
            }
            case 1: {
                scale = (double)(-((int)dist)) / 6.0;
                if (scale < 1.0) {
                    scale = 1.0;
                }
                scale *= 0.02666666666666667;
                break;
            }
            case 2: {
                scale = 0.0018 + 0.003 * dist;
                if (dist <= 8.0) {
                    scale = 0.0245;
                }
                start = -8;
            }
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)(x - KamiTessellator.mc.func_175598_ae().field_78730_l), (double)(y + offset - KamiTessellator.mc.func_175598_ae().field_78731_m), (double)(z - KamiTessellator.mc.func_175598_ae().field_78728_n));
        GlStateManager.func_179114_b((float)(-KamiTessellator.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)KamiTessellator.mc.func_175598_ae().field_78732_j, (float)(KamiTessellator.mc.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.func_179139_a((double)(-scale), (double)(-scale), (double)scale);
        if (type2 == 2) {
            double width = 0.0;
            Color bcolor = new Color(0, 0, 0, 51);
            if (Nametags3.CustomColor.getValue().booleanValue()) {
                bcolor = new Color(Nametags3.red.getValue(), Nametags3.green.getValue(), Nametags3.blue.getValue(), Nametags3.alpha.getValue());
            }
            for (int i = 0; i < text.length; ++i) {
                double w = KamiTessellator.mc.field_71466_p.func_78256_a(text[i]) / 2;
                if (!(w > width)) continue;
                width = w;
            }
            KamiTessellator.drawBorderedRect(-width - 1.0, -KamiTessellator.mc.field_71466_p.field_78288_b, width + 2.0, 1.0, 1.8f, new Color(0.0f, 0.0f, 0.0f, 0.6f), bcolor);
        }
        GlStateManager.func_179098_w();
        for (int i = 0; i < text.length; ++i) {
            KamiTessellator.mc.field_71466_p.func_175063_a(text[i], (float)(-KamiTessellator.mc.field_71466_p.func_78256_a(text[i]) / 2), (float)(i * (KamiTessellator.mc.field_71466_p.field_78288_b + 1) + start), color.getRGB());
        }
        GlStateManager.func_179090_x();
        if (type2 != 2) {
            GlStateManager.func_179121_F();
        }
    }

    public static void prepareTags() {
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179103_j((int)7425);
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179141_d();
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
    }

    public static void begin(int mode) {
        INSTANCE.func_178180_c().func_181668_a(mode, DefaultVertexFormats.field_181706_f);
    }

    public static void release() {
        KamiTessellator.render();
        KamiTessellator.releaseGL();
    }

    public static void render() {
        INSTANCE.func_78381_a();
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

    public static void drawBox(AxisAlignedBB bb, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), bb, r, g, b, a, sides);
    }

    public static void drawBox(BlockPos blockPos, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(blockPos, r, g, b, a, sides);
    }

    public static void drawHalfBox(BlockPos blockPos, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawHalfBox(blockPos, r, g, b, a, sides);
    }

    public static void drawHalfBox(float x, float y, float z, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), x, y, z, 1.0f, 0.5f, 1.0f, r, g, b, a, sides);
    }

    private static void drawBorderedRect(double x, double y, double x1, double y1, float lineWidth, Color inside, Color border) {
        KamiTessellator.prepareTags();
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179131_c((float)((float)inside.getRed() / 255.0f), (float)((float)inside.getGreen() / 255.0f), (float)((float)inside.getBlue() / 255.0f), (float)((float)inside.getAlpha() / 255.0f));
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b(x, y1, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x1, y1, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x1, y, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x, y, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179131_c((float)((float)border.getRed() / 255.0f), (float)((float)border.getGreen() / 255.0f), (float)((float)border.getBlue() / 255.0f), (float)((float)border.getAlpha() / 255.0f));
        GlStateManager.func_187441_d((float)lineWidth);
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b(x, y, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x, y1, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x1, y1, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x1, y, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(x, y, 0.0).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawBox(float x, float y, float z, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawHalfBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p(), 1.0f, 0.5f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBox(Vec3d vec3d, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), (float)vec3d.field_72450_a, (float)vec3d.field_72448_b, (float)vec3d.field_72449_c, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static BufferBuilder getBufferBuilder() {
        return INSTANCE.func_178180_c();
    }

    public static void drawBox(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 1) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 2) != 0) {
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 4) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 8) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x10) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x20) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
    }

    public static void drawBox(BufferBuilder buffer, AxisAlignedBB bb, int r, int g, int b, int a, int sides) {
        if ((sides & 1) != 0) {
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 2) != 0) {
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 4) != 0) {
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 8) != 0) {
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x10) != 0) {
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x20) != 0) {
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
        }
    }

    public static void drawSmallBox(Vec3d vec3d, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.func_178180_c(), (float)vec3d.field_72450_a, (float)vec3d.field_72448_b, (float)vec3d.field_72449_c, 0.3f, 0.3f, 0.3f, r, g, b, a, sides);
    }

    public static void drawLines(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 0x11) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x12) != 0) {
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x21) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x22) != 0) {
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 5) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 6) != 0) {
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 9) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0xA) != 0) {
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x14) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x24) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x18) != 0) {
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x28) != 0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, int r, int g, int b, int alpha) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static void drawBoundingBoxBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.func_71410_x();
        double x = (double)bp.field_177962_a - mc.func_175598_ae().field_78730_l;
        double y = (double)bp.field_177960_b - mc.func_175598_ae().field_78731_m;
        double z = (double)bp.field_177961_c - mc.func_175598_ae().field_78728_n;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static void drawBoundingBoxBottomBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.func_71410_x();
        double x = (double)bp.field_177962_a - mc.func_175598_ae().field_78730_l;
        double y = (double)bp.field_177960_b - mc.func_175598_ae().field_78731_m;
        double z = (double)bp.field_177961_c - mc.func_175598_ae().field_78728_n;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable((int)2848);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    public static void drawBoxBottom(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
        buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
        buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
    }

    public static void drawBoxBottom(BlockPos blockPos, int argb) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBoxBottom(blockPos, r, g, b, a);
    }

    public static void drawBoxBottom(float x, float y, float z, int argb) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBoxBottom(INSTANCE.func_178180_c(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }

    public static void drawBoxBottom(BlockPos blockPos, int r, int g, int b, int a) {
        KamiTessellator.drawBoxBottom(INSTANCE.func_178180_c(), blockPos.field_177962_a, blockPos.field_177960_b, blockPos.field_177961_c, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }
}

