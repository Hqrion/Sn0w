//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.item.EntityMinecartChest
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityDispenser
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityHopper
 *  net.minecraft.tileentity.TileEntityShulkerBox
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.ArrayList;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.KamiTessellator;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="StorageESP", description="Draws nice little lines around storage items", category=Module.Category.RENDER)
public class StorageESP
extends Module {
    private int getTileEntityColor(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityDispenser || tileEntity instanceof TileEntityShulkerBox) {
            return ColourUtils.Colors.ORANGE;
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return ColourUtils.Colors.PURPLE;
        }
        if (tileEntity instanceof TileEntityHopper) {
            return ColourUtils.Colors.DARK_RED;
        }
        return -1;
    }

    private int getEntityColor(Entity entity) {
        if (entity instanceof EntityMinecartChest) {
            return ColourUtils.Colors.ORANGE;
        }
        if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof ItemShulkerBox) {
            return ColourUtils.Colors.YELLOW;
        }
        return -1;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        int color;
        BlockPos pos;
        ArrayList<Triplet<BlockPos, Integer, Integer>> a = new ArrayList<Triplet<BlockPos, Integer, Integer>>();
        GlStateManager.pushMatrix();
        for (TileEntity tileEntity : Wrapper.getWorld().loadedTileEntityList) {
            pos = tileEntity.getPos();
            color = this.getTileEntityColor(tileEntity);
            int side = 63;
            if (tileEntity instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest)tileEntity;
                if (chest.adjacentChestZNeg != null) {
                    side = ~(side & 4);
                }
                if (chest.adjacentChestXPos != null) {
                    side = ~(side & 0x20);
                }
                if (chest.adjacentChestZPos != null) {
                    side = ~(side & 8);
                }
                if (chest.adjacentChestXNeg != null) {
                    side = ~(side & 0x10);
                }
            }
            if (color == -1) continue;
            a.add(new Triplet<BlockPos, Integer, Integer>(pos, color, side));
        }
        for (Entity entity : Wrapper.getWorld().loadedEntityList) {
            pos = entity.getPosition();
            color = this.getEntityColor(entity);
            if (color == -1) continue;
            a.add(new Triplet<BlockPos, Integer, Integer>(entity instanceof EntityItemFrame ? pos.add(0, -1, 0) : pos, color, 63));
        }
        KamiTessellator.prepare(7);
        for (Triplet triplet : a) {
            KamiTessellator.drawBox((BlockPos)triplet.getFirst(), this.changeAlpha((Integer)triplet.getSecond(), 100), (int)((Integer)triplet.getThird()));
        }
        KamiTessellator.release();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
    }

    int changeAlpha(int origColor, int userInputedAlpha) {
        return userInputedAlpha << 24 | (origColor &= 0xFFFFFF);
    }

    public class Triplet<T, U, V> {
        private final T first;
        private final U second;
        private final V third;

        public Triplet(T first, U second, V third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public T getFirst() {
            return this.first;
        }

        public U getSecond() {
            return this.second;
        }

        public V getThird() {
            return this.third;
        }
    }
}

