//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BlockssUtils {
    public static final List blackList;
    public static final List shulkerList;
    static Minecraft mc;

    public static IBlockState getState(BlockPos pos) {
        return BlockssUtils.mc.world.getBlockState(pos);
    }

    public static boolean checkForNeighbours(BlockPos blockPos) {
        if (!BlockssUtils.hasNeighbour(blockPos)) {
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = blockPos.offset(side);
                if (!BlockssUtils.hasNeighbour(neighbour)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if (Wrapper.getWorld().getBlockState(neighbour).getMaterial().isReplaceable()) continue;
            return true;
        }
        return false;
    }

    public static Block getBlock(BlockPos pos) {
        return BlockssUtils.getState(pos).getBlock();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockssUtils.getBlock(pos).canCollideCheck(BlockssUtils.getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlockssUtils.getNeededRotations2(vec);
        BlockssUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlockssUtils.mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = BlockssUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlockssUtils.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - BlockssUtils.mc.player.rotationYaw)), BlockssUtils.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - BlockssUtils.mc.player.rotationPitch))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(BlockssUtils.mc.player.posX, BlockssUtils.mc.player.posY + (double)BlockssUtils.mc.player.getEyeHeight(), BlockssUtils.mc.player.posZ);
    }

    public static List<BlockPos> getCircle(BlockPos loc, int y, float r, boolean hollow) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0f) * (r - 1.0f)))) {
                    BlockPos l = new BlockPos(x, y, z);
                    circleblocks.add(l);
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static EnumFacing getPlaceableSide(BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState blockState;
            BlockPos neighbour = pos.offset(side);
            if (!BlockssUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockssUtils.mc.world.getBlockState(neighbour), false) || (blockState = BlockssUtils.mc.world.getBlockState(neighbour)).getMaterial().isReplaceable()) continue;
            return side;
        }
        return null;
    }

    static {
        mc = Minecraft.getMinecraft();
        blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }
}

