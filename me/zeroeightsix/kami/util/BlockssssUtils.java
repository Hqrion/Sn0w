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
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
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
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockssssUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static List<Block> emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE);
    public static List<Block> rightclickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);

    public static boolean canSeeBlock(BlockPos p_Pos) {
        return BlockssssUtils.mc.player != null && BlockssssUtils.mc.world.rayTraceBlocks(new Vec3d(BlockssssUtils.mc.player.posX, BlockssssUtils.mc.player.posY + (double)BlockssssUtils.mc.player.getEyeHeight(), BlockssssUtils.mc.player.posZ), new Vec3d((double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ()), false, true, false) == null;
    }

    public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand) {
        RayTraceResult result = BlockssssUtils.mc.world.rayTraceBlocks(new Vec3d(BlockssssUtils.mc.player.posX, BlockssssUtils.mc.player.posY + (double)BlockssssUtils.mc.player.getEyeHeight(), BlockssssUtils.mc.player.posZ), new Vec3d((double)pos.getX() + 0.5, (double)pos.getY() - 0.5, (double)pos.getZ() + 0.5));
        EnumFacing facing = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
        BlockssssUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }

    public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
        return !shouldCheck || BlockssssUtils.mc.world.rayTraceBlocks(new Vec3d(BlockssssUtils.mc.player.posX, BlockssssUtils.mc.player.posY + (double)BlockssssUtils.mc.player.getEyeHeight(), BlockssssUtils.mc.player.posZ), new Vec3d((double)pos.getX(), (double)((float)pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }

    public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck) {
        return BlockssssUtils.rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }

    public static void openBlock(BlockPos pos) {
        EnumFacing[] facings;
        for (EnumFacing f : facings = EnumFacing.values()) {
            Block neighborBlock = BlockssssUtils.mc.world.getBlockState(pos.offset(f)).getBlock();
            if (!emptyBlocks.contains(neighborBlock)) continue;
            BlockssssUtils.mc.playerController.processRightClickBlock(BlockssssUtils.mc.player, BlockssssUtils.mc.world, pos, f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            return;
        }
    }

    public static void swingArm(String setting) {
        if (setting == "Mainhand" || setting == "Both") {
            BlockssssUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (setting == "Offhand" || setting == "Both") {
            BlockssssUtils.mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack, boolean swing) {
        if (BlockssssUtils.isBlockEmpty(pos)) {
            EnumFacing[] facings;
            int old_slot = -1;
            if (slot != BlockssssUtils.mc.player.inventory.currentItem) {
                old_slot = BlockssssUtils.mc.player.inventory.currentItem;
                BlockssssUtils.mc.player.inventory.currentItem = slot;
            }
            for (EnumFacing f : facings = EnumFacing.values()) {
                Block neighborBlock = BlockssssUtils.mc.world.getBlockState(pos.offset(f)).getBlock();
                Vec3d vec = new Vec3d((double)pos.getX() + 0.5 + (double)f.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)f.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)f.getZOffset() * 0.5);
                if (emptyBlocks.contains(neighborBlock) || !(BlockssssUtils.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25)) continue;
                float[] rot = new float[]{BlockssssUtils.mc.player.rotationYaw, BlockssssUtils.mc.player.rotationPitch};
                if (rotate) {
                    BlockssssUtils.rotatePacket(vec.x, vec.y, vec.z);
                }
                if (rightclickableBlocks.contains(neighborBlock)) {
                    BlockssssUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockssssUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                BlockssssUtils.mc.playerController.processRightClickBlock(BlockssssUtils.mc.player, BlockssssUtils.mc.world, pos.offset(f), f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                if (rightclickableBlocks.contains(neighborBlock)) {
                    BlockssssUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockssssUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (rotateBack) {
                    BlockssssUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], BlockssssUtils.mc.player.onGround));
                }
                if (swing) {
                    BlockssssUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (old_slot != -1) {
                    BlockssssUtils.mc.player.inventory.currentItem = old_slot;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockEmpty(BlockPos pos) {
        try {
            if (emptyBlocks.contains(BlockssssUtils.mc.world.getBlockState(pos).getBlock())) {
                Entity e;
                AxisAlignedBB box = new AxisAlignedBB(pos);
                Iterator entityIter = BlockssssUtils.mc.world.loadedEntityList.iterator();
                do {
                    if (entityIter.hasNext()) continue;
                    return true;
                } while (!((e = (Entity)entityIter.next()) instanceof EntityLivingBase) || !box.intersects(e.getEntityBoundingBox()));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static boolean canPlaceBlock(BlockPos pos) {
        if (BlockssssUtils.isBlockEmpty(pos)) {
            EnumFacing[] facings;
            for (EnumFacing f : facings = EnumFacing.values()) {
                if (emptyBlocks.contains(BlockssssUtils.mc.world.getBlockState(pos.offset(f)).getBlock())) continue;
                Vec3d vec3d = new Vec3d((double)pos.getX() + 0.5 + (double)f.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)f.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)f.getZOffset() * 0.5);
                if (!(BlockssssUtils.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec3d) <= 4.25)) continue;
                return true;
            }
        }
        return false;
    }

    public static void rotatePacket(double x, double y, double z) {
        double diffX = x - BlockssssUtils.mc.player.posX;
        double diffY = y - (BlockssssUtils.mc.player.posY + (double)BlockssssUtils.mc.player.getEyeHeight());
        double diffZ = z - BlockssssUtils.mc.player.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        BlockssssUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, BlockssssUtils.mc.player.onGround));
    }
}

