//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
    private static final Minecraft mc = Minecraft.getMinecraft();

    public KamiTessellator() {
        super(0x200000);
    }

    public static void prepare(int mode) {
        KamiTessellator.prepareGL();
        KamiTessellator.begin(mode);
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

    public static void drawNametag(Entity entity, String[] text, Color color, int type2) {
        Vec3d pos = EntityUtil.getInterpolatedPos(entity, Minecraft.getMinecraft().getRenderPartialTicks());
        KamiTessellator.drawNametag(pos.x, pos.y + (double)entity.height, pos.z, text, color, type2);
    }

    public static void drawNametag(double x, double y, double z, String[] text, Color color, int type2) {
        double dist = KamiTessellator.mc.player.getDistance(x, y, z);
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
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)(x - KamiTessellator.mc.getRenderManager().viewerPosX), (double)(y + offset - KamiTessellator.mc.getRenderManager().viewerPosY), (double)(z - KamiTessellator.mc.getRenderManager().viewerPosZ));
        GlStateManager.rotate((float)(-KamiTessellator.mc.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)KamiTessellator.mc.getRenderManager().playerViewX, (float)(KamiTessellator.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((double)(-scale), (double)(-scale), (double)scale);
        if (type2 == 2) {
            double width = 0.0;
            Color bcolor = new Color(0, 0, 0, 51);
            if (Nametags3.CustomColor.getValue().booleanValue()) {
                bcolor = new Color(Nametags3.red.getValue(), Nametags3.green.getValue(), Nametags3.blue.getValue(), Nametags3.alpha.getValue());
            }
            for (int i = 0; i < text.length; ++i) {
                double w = KamiTessellator.mc.fontRenderer.getStringWidth(text[i]) / 2;
                if (!(w > width)) continue;
                width = w;
            }
            KamiTessellator.drawBorderedRect(-width - 1.0, -KamiTessellator.mc.fontRenderer.FONT_HEIGHT, width + 2.0, 1.0, 1.8f, new Color(0.0f, 0.0f, 0.0f, 0.6f), bcolor);
        }
        GlStateManager.enableTexture2D();
        for (int i = 0; i < text.length; ++i) {
            KamiTessellator.mc.fontRenderer.drawStringWithShadow(text[i], (float)(-KamiTessellator.mc.fontRenderer.getStringWidth(text[i]) / 2), (float)(i * (KamiTessellator.mc.fontRenderer.FONT_HEIGHT + 1) + start), color.getRGB());
        }
        GlStateManager.disableTexture2D();
        if (type2 != 2) {
            GlStateManager.popMatrix();
        }
    }

    public static void prepareTags() {
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
    }

    public static void begin(int mode) {
        INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void release() {
        KamiTessellator.render();
        KamiTessellator.releaseGL();
    }

    public static void render() {
        INSTANCE.draw();
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

    public static void drawBox(AxisAlignedBB bb, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(INSTANCE.getBuffer(), bb, r, g, b, a, sides);
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
        KamiTessellator.drawBox(INSTANCE.getBuffer(), x, y, z, 1.0f, 0.5f, 1.0f, r, g, b, a, sides);
    }

    private static void drawBorderedRect(double x, double y, double x1, double y1, float lineWidth, Color inside, Color border) {
        KamiTessellator.prepareTags();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color((float)((float)inside.getRed() / 255.0f), (float)((float)inside.getGreen() / 255.0f), (float)((float)inside.getBlue() / 255.0f), (float)((float)inside.getAlpha() / 255.0f));
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, y1, 0.0).endVertex();
        bufferbuilder.pos(x1, y1, 0.0).endVertex();
        bufferbuilder.pos(x1, y, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color((float)((float)border.getRed() / 255.0f), (float)((float)border.getGreen() / 255.0f), (float)((float)border.getBlue() / 255.0f), (float)((float)border.getAlpha() / 255.0f));
        GlStateManager.glLineWidth((float)lineWidth);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, y, 0.0).endVertex();
        bufferbuilder.pos(x, y1, 0.0).endVertex();
        bufferbuilder.pos(x1, y1, 0.0).endVertex();
        bufferbuilder.pos(x1, y, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
    }

    public static void drawBox(float x, float y, float z, int argb, int sides) {
        int a = argb >>> 24 & 0xFF;
        int r = argb >>> 16 & 0xFF;
        int g = argb >>> 8 & 0xFF;
        int b = argb & 0xFF;
        KamiTessellator.drawBox(INSTANCE.getBuffer(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static void drawHalfBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 0.5f, 1.0f, r, g, b, a, sides);
    }

    public static void drawBox(Vec3d vec3d, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.getBuffer(), (float)vec3d.x, (float)vec3d.y, (float)vec3d.z, 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }

    public static BufferBuilder getBufferBuilder() {
        return INSTANCE.getBuffer();
    }

    public static void drawBox(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 1) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 2) != 0) {
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 4) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 8) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x10) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x20) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
    }

    public static void drawBox(BufferBuilder buffer, AxisAlignedBB bb, int r, int g, int b, int a, int sides) {
        if ((sides & 1) != 0) {
            buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        }
        if ((sides & 2) != 0) {
            buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        }
        if ((sides & 4) != 0) {
            buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        }
        if ((sides & 8) != 0) {
            buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x10) != 0) {
            buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x20) != 0) {
            buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
            buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        }
    }

    public static void drawSmallBox(Vec3d vec3d, int r, int g, int b, int a, int sides) {
        KamiTessellator.drawBox(INSTANCE.getBuffer(), (float)vec3d.x, (float)vec3d.y, (float)vec3d.z, 0.3f, 0.3f, 0.3f, r, g, b, a, sides);
    }

    public static void drawLines(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        if ((sides & 0x11) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x12) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x21) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x22) != 0) {
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 5) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 6) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 9) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0xA) != 0) {
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x14) != 0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x24) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x18) != 0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x28) != 0) {
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBoxBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.getMinecraft();
        double x = (double)bp.x - mc.getRenderManager().viewerPosX;
        double y = (double)bp.y - mc.getRenderManager().viewerPosY;
        double z = (double)bp.z - mc.getRenderManager().viewerPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBoxBottomBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Minecraft mc = Minecraft.getMinecraft();
        double x = (double)bp.x - mc.getRenderManager().viewerPosX;
        double y = (double)bp.y - mc.getRenderManager().viewerPosY;
        double z = (double)bp.z - mc.getRenderManager().viewerPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoxBottom(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
        buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
        buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
        buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
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
        KamiTessellator.drawBoxBottom(INSTANCE.getBuffer(), x, y, z, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }

    public static void drawBoxBottom(BlockPos blockPos, int r, int g, int b, int a) {
        KamiTessellator.drawBoxBottom(INSTANCE.getBuffer(), blockPos.x, blockPos.y, blockPos.z, 1.0f, 1.0f, 1.0f, r, g, b, a);
    }
}

