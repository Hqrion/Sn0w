/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.text.TextFormatting
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.awt.Font;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

@Module.Info(name="Rusher Hack Nametages", category=Module.Category.RENDER)
public class RHackNametags
extends Module {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    private Setting<Boolean> Armor = this.register(Settings.b("Armor", false));

    @Override
    public void onWorldRender(RenderEvent event) {
        for (EntityPlayer p : RHackNametags.mc.field_71441_e.field_73010_i) {
            if (p == mc.func_175606_aa() || !p.func_70089_S()) continue;
            double pX = p.field_70142_S + (p.field_70165_t - p.field_70142_S) * (double)RHackNametags.mc.field_71428_T.field_194147_b - RHackNametags.mc.func_175598_ae().field_78725_b;
            double pY = p.field_70137_T + (p.field_70163_u - p.field_70137_T) * (double)RHackNametags.mc.field_71428_T.field_194147_b - RHackNametags.mc.func_175598_ae().field_78726_c;
            double pZ = p.field_70136_U + (p.field_70161_v - p.field_70136_U) * (double)RHackNametags.mc.field_71428_T.field_194147_b - RHackNametags.mc.func_175598_ae().field_78723_d;
            if (p.func_70005_c_().startsWith("Body #")) continue;
            this.renderNametag(p, pX, pY, pZ);
        }
    }

    private void renderNametag(EntityPlayer player, double x, double y, double z) {
        boolean l4 = false;
        GL11.glPushMatrix();
        String name = player.func_70005_c_() + "\u00a7a " + MathHelper.func_76123_f((float)(player.func_110143_aJ() + player.func_110139_bj()));
        name = name.replace(".0", "");
        float distance = RHackNametags.mc.field_71439_g.func_70032_d((Entity)player);
        float var15 = (distance / 5.0f <= 2.0f ? 2.0f : distance / 5.0f) * 2.5f;
        float var14 = 0.016666668f * this.getNametagSize((EntityLivingBase)player);
        GL11.glTranslated((double)((float)x), (double)((double)((float)y) + 2.5), (double)((float)z));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-RHackNametags.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)RHackNametags.mc.func_175598_ae().field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-var14), (float)(-var14), (float)var14);
        GlStateManager.func_179140_f();
        GlStateManager.func_179132_a((boolean)false);
        GL11.glDisable((int)2929);
        int width = this.cFontRenderer.getStringWidth(name) / 2;
        this.drawBorderedRect(-width - 2, 10.0, width + 1, 20.0, 0.0, Friends.isFriend(name) ? new Color(0, 130, 130).getRGB() : 8617341, -1);
        this.cFontRenderer.drawString(name, -width, 11.0f, -1);
        int xOffset = 0;
        for (ItemStack armourStack : player.field_71071_by.field_70460_b) {
            if (armourStack == null) continue;
            xOffset -= 8;
        }
        if (player.func_184614_ca() != null) {
            ItemStack renderStack = player.func_184614_ca().func_77946_l();
            this.renderItem(player, renderStack, xOffset -= 8, -10);
            xOffset += 16;
        }
        for (int index = 3; index >= 0; --index) {
            ItemStack armourStack = (ItemStack)player.field_71071_by.field_70460_b.get(index);
            if (armourStack == null) continue;
            ItemStack renderStack1 = armourStack.func_77946_l();
            this.renderItem(player, renderStack1, xOffset, -10);
            xOffset += 16;
        }
        if (player.func_184592_cb() != null) {
            ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
            this.renderItem(player, renderOffhand, xOffset += 0, -10);
            xOffset += 8;
        }
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private float getNametagSize(EntityLivingBase player) {
        ScaledResolution scaledRes = new ScaledResolution(mc);
        double twoDscale = (double)scaledRes.func_78325_e() / Math.pow(scaledRes.func_78325_e(), 2.0);
        return (float)twoDscale + RHackNametags.mc.field_71439_g.func_70032_d((Entity)player) / 7.0f;
    }

    public void drawBorderRect(float left, float top, float right, float bottom, int bcolor, int icolor, float f) {
        this.drawGuiRect(left + f, top + f, right - f, bottom - f, icolor);
        this.drawGuiRect(left, top, left + f, bottom, bcolor);
        this.drawGuiRect(left + f, top, right, top + f, bcolor);
        this.drawGuiRect(left + f, bottom - f, right, bottom, bcolor);
        this.drawGuiRect(right - f, top + f, right, bottom - f, bcolor);
    }

    private void drawBorderedRect(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        this.enableGL2D();
        this.fakeGuiRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        this.fakeGuiRect(x + width, y, x1 - width, y + width, borderColor);
        this.fakeGuiRect(x, y, x + width, y1, borderColor);
        this.fakeGuiRect(x1 - width, y, x1, y1, borderColor);
        this.fakeGuiRect(x + width, y1 - width, x1 - width, y1, borderColor);
        this.disableGL2D();
    }

    private void renderItem(EntityPlayer player, ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.func_179086_m((int)256);
        GlStateManager.func_179097_i();
        GlStateManager.func_179126_j();
        RenderHelper.func_74519_b();
        RHackNametags.mc.func_175599_af().field_77023_b = -100.0f;
        GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)0.01f);
        mc.func_175599_af().func_180450_b(stack, x, y / 2 - 12);
        mc.func_175599_af().func_175030_a(RHackNametags.mc.field_71466_p, stack, x, y / 2 - 12);
        RHackNametags.mc.func_175599_af().field_77023_b = 0.0f;
        GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.func_74518_a();
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179140_f();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.func_179097_i();
        this.renderEnchantText(player, stack, x, y - 18);
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(EntityPlayer player, ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;
        if (stack.func_77973_b() instanceof ItemArmor || stack.func_77973_b() instanceof ItemSword || stack.func_77973_b() instanceof ItemTool) {
            this.cFontRenderer.drawStringWithShadow(stack.func_77958_k() - stack.func_77952_i() + "\u00a74", x * 2 + 8, y + 26, -1);
        }
        NBTTagList enchants = stack.func_77986_q();
        for (int index = 0; index < enchants.func_74745_c(); ++index) {
            short id = enchants.func_150305_b(index).func_74765_d("id");
            short level = enchants.func_150305_b(index).func_74765_d("lvl");
            Enchantment enc = Enchantment.func_185262_c((int)id);
            if (enc == null) continue;
            String encName = enc.func_190936_d() ? TextFormatting.RED + enc.func_77316_c((int)level).substring(11).substring(0, 1).toLowerCase() : enc.func_77316_c((int)level).substring(0, 1).toLowerCase();
            encName = encName + level;
            GL11.glPushMatrix();
            GL11.glScalef((float)0.9f, (float)0.9f, (float)0.0f);
            this.cFontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount, -1);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            encY += 8;
            yCount -= 10;
        }
    }

    private void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    private void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    private void drawGuiRect(double x1, double y1, double x2, double y2, int color) {
        float red = (float)(color >> 24 & 0xFF) / 255.0f;
        float green = (float)(color >> 16 & 0xFF) / 255.0f;
        float blue = (float)(color >> 8 & 0xFF) / 255.0f;
        float alpha = (float)(color & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)green, (float)blue, (float)alpha, (float)red);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x2, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private void fakeGuiRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b(left, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, top, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
}

