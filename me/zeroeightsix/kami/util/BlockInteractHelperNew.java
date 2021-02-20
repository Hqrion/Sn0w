//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BlockInteractHelperNew {
    public static final List<Block> blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
    public static final List<Block> shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static PlaceResult place(BlockPos pos, float p_Distance, boolean p_Rotate, boolean p_UseSlabRule) {
        IBlockState l_State = BlockInteractHelperNew.mc.world.getBlockState(pos);
        boolean l_Replaceable = l_State.getMaterial().isReplaceable();
        boolean l_IsSlabAtBlock = l_State.getBlock() instanceof BlockSlab;
        if (!l_Replaceable && !l_IsSlabAtBlock) {
            return PlaceResult.NotReplaceable;
        }
        if (!BlockInteractHelperNew.checkForNeighbours(pos)) {
            return PlaceResult.Neighbors;
        }
        if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.isFullCube()) {
            return PlaceResult.CantPlace;
        }
        Vec3d eyesPos = new Vec3d(BlockInteractHelperNew.mc.player.posX, BlockInteractHelperNew.mc.player.posY + (double)BlockInteractHelperNew.mc.player.getEyeHeight(), BlockInteractHelperNew.mc.player.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            EnumActionResult l_Result2;
            Vec3d hitVec;
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractHelperNew.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractHelperNew.mc.world.getBlockState(neighbor), false) || !(eyesPos.distanceTo(hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5))) <= (double)p_Distance)) continue;
            Block neighborPos = BlockInteractHelperNew.mc.world.getBlockState(neighbor).getBlock();
            boolean activated = neighborPos.onBlockActivated((World)BlockInteractHelperNew.mc.world, pos, BlockInteractHelperNew.mc.world.getBlockState(pos), (EntityPlayer)BlockInteractHelperNew.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (blackList.contains(neighborPos) || shulkerList.contains(neighborPos) || activated) {
                BlockInteractHelperNew.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractHelperNew.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            if (p_Rotate) {
                BlockInteractHelperNew.faceVectorPacketInstant(hitVec);
            }
            if ((l_Result2 = BlockInteractHelperNew.mc.playerController.processRightClickBlock(BlockInteractHelperNew.mc.player, BlockInteractHelperNew.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND)) == EnumActionResult.FAIL) continue;
            BlockInteractHelperNew.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (activated) {
                BlockInteractHelperNew.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractHelperNew.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            return PlaceResult.Placed;
        }
        return PlaceResult.CantPlace;
    }

    public static ValidResult valid(BlockPos pos) {
        if (!BlockInteractHelperNew.mc.world.checkNoEntityCollision(new AxisAlignedBB(pos))) {
            return ValidResult.NoEntityCollision;
        }
        if (!BlockInteractHelperNew.checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        IBlockState l_State = BlockInteractHelperNew.mc.world.getBlockState(pos);
        if (l_State.getBlock() == Blocks.AIR) {
            BlockPos[] l_Blocks;
            for (BlockPos l_Pos : l_Blocks = new BlockPos[]{pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down()}) {
                IBlockState l_State2 = BlockInteractHelperNew.mc.world.getBlockState(l_Pos);
                if (l_State2.getBlock() == Blocks.AIR) continue;
                for (EnumFacing side : EnumFacing.values()) {
                    BlockPos neighbor = pos.offset(side);
                    if (!BlockInteractHelperNew.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractHelperNew.mc.world.getBlockState(neighbor), false)) continue;
                    return ValidResult.Ok;
                }
            }
            return ValidResult.NoNeighbors;
        }
        return ValidResult.AlreadyBlockThere;
    }

    public static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = BlockInteractHelperNew.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlockInteractHelperNew.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - BlockInteractHelperNew.mc.player.rotationYaw)), BlockInteractHelperNew.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - BlockInteractHelperNew.mc.player.rotationPitch))};
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(BlockInteractHelperNew.mc.player.posX, BlockInteractHelperNew.mc.player.posY + (double)BlockInteractHelperNew.mc.player.getEyeHeight(), BlockInteractHelperNew.mc.player.posZ);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlockInteractHelperNew.getLegitRotations(vec);
        BlockInteractHelperNew.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlockInteractHelperNew.mc.player.onGround));
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockInteractHelperNew.getBlock(pos).canCollideCheck(BlockInteractHelperNew.getState(pos), false);
    }

    private static Block getBlock(BlockPos pos) {
        return BlockInteractHelperNew.getState(pos).getBlock();
    }

    private static IBlockState getState(BlockPos pos) {
        return BlockInteractHelperNew.mc.world.getBlockState(pos);
    }

    public static boolean checkForNeighbours(BlockPos blockPos) {
        if (!BlockInteractHelperNew.hasNeighbour(blockPos)) {
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = blockPos.offset(side);
                if (!BlockInteractHelperNew.hasNeighbour(neighbour)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if (BlockInteractHelperNew.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) continue;
            return true;
        }
        return false;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f;
                    float f2 = f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static enum PlaceResult {
        NotReplaceable,
        Neighbors,
        CantPlace,
        Placed;

    }

    public static enum ValidResult {
        NoEntityCollision,
        AlreadyBlockThere,
        NoNeighbors,
        Ok;

    }
}

