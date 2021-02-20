//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class WorldUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static List<Block> emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE);
    public static List<Block> rightclickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);

    public static void openBlock(BlockPos pos) {
        EnumFacing[] facings;
        for (EnumFacing f : facings = EnumFacing.values()) {
            Block neighborBlock = WorldUtils.mc.world.getBlockState(pos.offset(f)).getBlock();
            if (!emptyBlocks.contains(neighborBlock)) continue;
            WorldUtils.mc.playerController.processRightClickBlock(WorldUtils.mc.player, WorldUtils.mc.world, pos, f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            return;
        }
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack) {
        EnumFacing[] facings;
        if (!WorldUtils.isBlockEmpty(pos)) {
            return false;
        }
        if (slot != WorldUtils.mc.player.inventory.currentItem) {
            WorldUtils.mc.player.inventory.currentItem = slot;
        }
        for (EnumFacing f : facings = EnumFacing.values()) {
            Block neighborBlock = WorldUtils.mc.world.getBlockState(pos.offset(f)).getBlock();
            Vec3d vec = new Vec3d((double)pos.getX() + 0.5 + (double)f.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)f.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)f.getZOffset() * 0.5);
            if (emptyBlocks.contains(neighborBlock) || !(WorldUtils.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25)) continue;
            float[] rot = new float[]{WorldUtils.mc.player.rotationYaw, WorldUtils.mc.player.rotationPitch};
            if (rotate) {
                WorldUtils.rotatePacket(vec.x, vec.y, vec.z);
            }
            if (rightclickableBlocks.contains(neighborBlock)) {
                WorldUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WorldUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            WorldUtils.mc.playerController.processRightClickBlock(WorldUtils.mc.player, WorldUtils.mc.world, pos.offset(f), f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            if (rightclickableBlocks.contains(neighborBlock)) {
                WorldUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WorldUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (rotateBack) {
                WorldUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], WorldUtils.mc.player.onGround));
            }
            return true;
        }
        return false;
    }

    public static boolean isBlockEmpty(BlockPos pos) {
        Entity e;
        if (!emptyBlocks.contains(WorldUtils.mc.world.getBlockState(pos).getBlock())) {
            return false;
        }
        AxisAlignedBB box = new AxisAlignedBB(pos);
        Iterator entityIter = WorldUtils.mc.world.loadedEntityList.iterator();
        do {
            if (entityIter.hasNext()) continue;
            return true;
        } while (!((e = (Entity)entityIter.next()) instanceof EntityLivingBase) || !box.intersects(e.getEntityBoundingBox()));
        return false;
    }

    public static boolean canPlaceBlock(BlockPos pos) {
        EnumFacing[] facings;
        if (!WorldUtils.isBlockEmpty(pos)) {
            return false;
        }
        for (EnumFacing f : facings = EnumFacing.values()) {
            if (emptyBlocks.contains(WorldUtils.mc.world.getBlockState(pos.offset(f)).getBlock())) continue;
            Vec3d vec3d = new Vec3d((double)pos.getX() + 0.5 + (double)f.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)f.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)f.getZOffset() * 0.5);
            if (!(WorldUtils.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec3d) <= 4.25)) continue;
            return true;
        }
        return false;
    }

    public static EnumFacing getClosestFacing(BlockPos pos) {
        return EnumFacing.DOWN;
    }

    public static void rotateClient(double x, double y, double z) {
        double diffX = x - WorldUtils.mc.player.posX;
        double diffY = y - (WorldUtils.mc.player.posY + (double)WorldUtils.mc.player.getEyeHeight());
        double diffZ = z - WorldUtils.mc.player.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WorldUtils.mc.player.rotationYaw += MathHelper.wrapDegrees((float)(yaw - WorldUtils.mc.player.rotationYaw));
        WorldUtils.mc.player.rotationPitch += MathHelper.wrapDegrees((float)(pitch - WorldUtils.mc.player.rotationPitch));
    }

    public static void rotatePacket(double x, double y, double z) {
        double diffX = x - WorldUtils.mc.player.posX;
        double diffY = y - (WorldUtils.mc.player.posY + (double)WorldUtils.mc.player.getEyeHeight());
        double diffZ = z - WorldUtils.mc.player.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WorldUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, WorldUtils.mc.player.onGround));
    }
}

