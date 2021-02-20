//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        for (EntityPlayer p : RHackNametags.mc.world.playerEntities) {
            if (p == mc.getRenderViewEntity() || !p.isEntityAlive()) continue;
            double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)RHackNametags.mc.timer.renderPartialTicks - RHackNametags.mc.getRenderManager().renderPosX;
            double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)RHackNametags.mc.timer.renderPartialTicks - RHackNametags.mc.getRenderManager().renderPosY;
            double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)RHackNametags.mc.timer.renderPartialTicks - RHackNametags.mc.getRenderManager().renderPosZ;
            if (p.getName().startsWith("Body #")) continue;
            this.renderNametag(p, pX, pY, pZ);
        }
    }

    private void renderNametag(EntityPlayer player, double x, double y, double z) {
        boolean l4 = false;
        GL11.glPushMatrix();
        String name = player.getName() + "\u00a7a " + MathHelper.ceil((float)(player.getHealth() + player.getAbsorptionAmount()));
        name = name.replace(".0", "");
        float distance = RHackNametags.mc.player.getDistance((Entity)player);
        float var15 = (distance / 5.0f <= 2.0f ? 2.0f : distance / 5.0f) * 2.5f;
        float var14 = 0.016666668f * this.getNametagSize((EntityLivingBase)player);
        GL11.glTranslated((double)((float)x), (double)((double)((float)y) + 2.5), (double)((float)z));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-RHackNametags.mc.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)RHackNametags.mc.getRenderManager().playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-var14), (float)(-var14), (float)var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask((boolean)false);
        GL11.glDisable((int)2929);
        int width = this.cFontRenderer.getStringWidth(name) / 2;
        this.drawBorderedRect(-width - 2, 10.0, width + 1, 20.0, 0.0, Friends.isFriend(name) ? new Color(0, 130, 130).getRGB() : 8617341, -1);
        this.cFontRenderer.drawString(name, -width, 11.0f, -1);
        int xOffset = 0;
        for (ItemStack armourStack : player.inventory.armorInventory) {
            if (armourStack == null) continue;
            xOffset -= 8;
        }
        if (player.getHeldItemMainhand() != null) {
            ItemStack renderStack = player.getHeldItemMainhand().copy();
            this.renderItem(player, renderStack, xOffset -= 8, -10);
            xOffset += 16;
        }
        for (int index = 3; index >= 0; --index) {
            ItemStack armourStack = (ItemStack)player.inventory.armorInventory.get(index);
            if (armourStack == null) continue;
            ItemStack renderStack1 = armourStack.copy();
            this.renderItem(player, renderStack1, xOffset, -10);
            xOffset += 16;
        }
        if (player.getHeldItemOffhand() != null) {
            ItemStack renderOffhand = player.getHeldItemOffhand().copy();
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
        double twoDscale = (double)scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
        return (float)twoDscale + RHackNametags.mc.player.getDistance((Entity)player) / 7.0f;
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
        GlStateManager.clear((int)256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        RHackNametags.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y / 2 - 12);
        mc.getRenderItem().renderItemOverlays(RHackNametags.mc.fontRenderer, stack, x, y / 2 - 12);
        RHackNametags.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.disableDepth();
        this.renderEnchantText(player, stack, x, y - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(EntityPlayer player, ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
            this.cFontRenderer.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() + "\u00a74", x * 2 + 8, y + 26, -1);
        }
        NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID((int)id);
            if (enc == null) continue;
            String encName = enc.isCurse() ? TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase() : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
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
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)f, (float)f1, (float)f2, (float)f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

