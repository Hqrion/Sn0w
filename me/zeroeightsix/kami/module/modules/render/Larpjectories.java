//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Cylinder
 */
package me.zeroeightsix.kami.module.modules.render;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@Module.Info(name="Trajectories2", category=Module.Category.RENDER)
public class Larpjectories
extends Module {
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(182).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(26).build());
    CFontRenderer cFontRenderer;

    @Override
    public void onWorldRender(RenderEvent event) {
        float pow;
        if (Larpjectories.mc.world == null || Larpjectories.mc.player == null) {
            return;
        }
        double renderPosX = Larpjectories.mc.player.lastTickPosX + (Larpjectories.mc.player.posX - Larpjectories.mc.player.lastTickPosX) * (double)event.getPartialTicks();
        double renderPosY = Larpjectories.mc.player.lastTickPosY + (Larpjectories.mc.player.posY - Larpjectories.mc.player.lastTickPosY) * (double)event.getPartialTicks();
        double renderPosZ = Larpjectories.mc.player.lastTickPosZ + (Larpjectories.mc.player.posZ - Larpjectories.mc.player.lastTickPosZ) * (double)event.getPartialTicks();
        Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(Larpjectories.mc.gameSettings.thirdPersonView == 0 && (Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow || Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFishingRod || Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEnderPearl || Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemEgg || Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSnowball || Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle))) {
            return;
        }
        GL11.glPushMatrix();
        Item item = Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
        double posX = renderPosX - (double)(MathHelper.cos((float)(Larpjectories.mc.player.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        double posY = renderPosY + (double)Larpjectories.mc.player.getEyeHeight() - 0.1000000014901161;
        double posZ = renderPosZ - (double)(MathHelper.sin((float)(Larpjectories.mc.player.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        double motionX = (double)(-MathHelper.sin((float)(Larpjectories.mc.player.rotationYaw / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(Larpjectories.mc.player.rotationPitch / 180.0f * (float)Math.PI))) * (item instanceof ItemBow ? 1.0 : 0.4);
        double motionY = (double)(-MathHelper.sin((float)(Larpjectories.mc.player.rotationPitch / 180.0f * (float)Math.PI))) * (item instanceof ItemBow ? 1.0 : 0.4);
        double motionZ = (double)(MathHelper.cos((float)(Larpjectories.mc.player.rotationYaw / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(Larpjectories.mc.player.rotationPitch / 180.0f * (float)Math.PI))) * (item instanceof ItemBow ? 1.0 : 0.4);
        int var6 = 72000 - Larpjectories.mc.player.getItemInUseCount();
        float power = (float)var6 / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;
        if (power > 1.0f) {
            power = 1.0f;
        }
        float distance = MathHelper.sqrt((double)(motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= (double)distance;
        motionY /= (double)distance;
        motionZ /= (double)distance;
        float f = item instanceof ItemBow ? power * 2.0f : (item instanceof ItemFishingRod ? 1.25f : (pow = Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.9f : 1.0f));
        motionX *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75f : 1.5f)));
        motionY *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75f : 1.5f)));
        motionZ *= (double)(pow * (item instanceof ItemFishingRod ? 0.75f : (Larpjectories.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE ? 0.75f : 1.5f)));
        this.enableGL3D(2.0f);
        if (power > 0.6f) {
            GlStateManager.color((float)((float)this.red.getValue().intValue() / 255.0f), (float)((float)this.green.getValue().intValue() / 255.0f), (float)((float)this.blue.getValue().intValue() / 255.0f), (float)1.0f);
        } else {
            GlStateManager.color((float)((float)this.red.getValue().intValue() / 255.0f), (float)((float)this.green.getValue().intValue() / 255.0f), (float)((float)this.blue.getValue().intValue() / 255.0f), (float)1.0f);
        }
        GL11.glEnable((int)2848);
        float size = (float)(item instanceof ItemBow ? 0.3 : 0.25);
        boolean hasLanded = false;
        Entity landingOnEntity = null;
        RayTraceResult landingPosition = null;
        while (!hasLanded && posY > 0.0) {
            Vec3d present = new Vec3d(posX, posY, posZ);
            Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
            RayTraceResult possibleLandingStrip = Larpjectories.mc.world.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != RayTraceResult.Type.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }
            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - (double)size, posY - (double)size, posZ - (double)size, posX + (double)size, posY + (double)size, posZ + (double)size);
            ArrayList entities = this.getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (Object entity : entities) {
                Entity boundingBox = (Entity)entity;
                if (!boundingBox.canBeCollidedWith() || boundingBox == Larpjectories.mc.player) continue;
                float var7 = 0.3f;
                AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand((double)0.3f, (double)0.3f, (double)0.3f);
                RayTraceResult possibleEntityLanding = var8.calculateIntercept(present, future);
                if (possibleEntityLanding == null) continue;
                hasLanded = true;
                landingOnEntity = boundingBox;
                landingPosition = possibleEntityLanding;
            }
            if (landingOnEntity != null) {
                GlStateManager.color((float)((float)this.red.getValue().intValue() / 255.0f), (float)((float)this.green.getValue().intValue() / 255.0f), (float)((float)this.blue.getValue().intValue() / 255.0f), (float)1.0f);
            }
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            float motionAdjustment = 0.99f;
            motionX *= (double)0.99f;
            motionY *= (double)0.99f;
            motionZ *= (double)0.99f;
            motionY -= item instanceof ItemBow ? 0.05 : 0.03;
        }
        if (landingPosition != null && landingPosition.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.translate((double)(posX - renderPosX), (double)(posY - renderPosY), (double)(posZ - renderPosZ));
            int side = landingPosition.sideHit.getIndex();
            if (side == 2) {
                GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            } else if (side == 3) {
                GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            } else if (side == 4) {
                GlStateManager.rotate((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            } else if (side == 5) {
                GlStateManager.rotate((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            Cylinder c = new Cylinder();
            GlStateManager.rotate((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            c.setDrawStyle(100011);
            if (landingOnEntity != null) {
                GlStateManager.color((float)((float)this.red.getValue().intValue() / 255.0f), (float)((float)this.green.getValue().intValue() / 255.0f), (float)((float)this.blue.getValue().intValue() / 255.0f), (float)1.0f);
                GL11.glLineWidth((float)2.5f);
                c.draw(0.6f, 0.3f, 0.0f, 4, 1);
                GL11.glLineWidth((float)0.1f);
                GlStateManager.color((float)((float)this.red.getValue().intValue() / 255.0f), (float)((float)this.green.getValue().intValue() / 255.0f), (float)((float)this.blue.getValue().intValue() / 255.0f), (float)1.0f);
            }
            c.draw(0.6f, 0.3f, 0.0f, 4, 1);
        }
        this.disableGL3D();
        GL11.glPopMatrix();
    }

    public void enableGL3D(float lineWidth) {
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2884);
        Larpjectories.mc.entityRenderer.disableLightmap();
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glLineWidth((float)lineWidth);
    }

    public void disableGL3D() {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDepthMask((boolean)true);
        GL11.glCullFace((int)1029);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    private ArrayList getEntitiesWithinAABB(AxisAlignedBB bb) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor((double)((bb.minX - 2.0) / 16.0));
        int chunkMaxX = MathHelper.floor((double)((bb.maxX + 2.0) / 16.0));
        int chunkMinZ = MathHelper.floor((double)((bb.minZ - 2.0) / 16.0));
        int chunkMaxZ = MathHelper.floor((double)((bb.maxZ + 2.0) / 16.0));
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (Larpjectories.mc.world.getChunkProvider().getLoadedChunk(x, z) == null) continue;
                Larpjectories.mc.world.getChunk(x, z).getEntitiesWithinAABBForEntity((Entity)Larpjectories.mc.player, bb, list, (Predicate)null);
            }
        }
        return list;
    }
}

