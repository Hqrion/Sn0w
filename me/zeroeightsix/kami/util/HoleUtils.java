//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class HoleUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isBreakable(BlockPos pos) {
        return HoleUtils.mc.world.getBlockState(pos).getBlock().getBlockHardness(HoleUtils.mc.world.getBlockState(pos), (World)HoleUtils.mc.world, pos) != -1.0f;
    }

    public static boolean placeBlock(BlockPos pos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (HoleUtils.mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) || HoleUtils.isIntercepted(pos)) continue;
            Vec3d vec = new Vec3d((double)pos.getX() + 0.5 + (double)enumFacing.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)enumFacing.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)enumFacing.getZOffset() * 0.5);
            float[] old = new float[]{HoleUtils.mc.player.rotationYaw, HoleUtils.mc.player.rotationPitch};
            HoleUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.z - HoleUtils.mc.player.posZ, vec.x - HoleUtils.mc.player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (HoleUtils.mc.player.posY + (double)HoleUtils.mc.player.getEyeHeight()), Math.sqrt((vec.x - HoleUtils.mc.player.posX) * (vec.x - HoleUtils.mc.player.posX) + (vec.z - HoleUtils.mc.player.posZ) * (vec.z - HoleUtils.mc.player.posZ))))), HoleUtils.mc.player.onGround));
            HoleUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HoleUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            HoleUtils.mc.playerController.processRightClickBlock(HoleUtils.mc.player, HoleUtils.mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            HoleUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
            HoleUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HoleUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            HoleUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(old[0], old[1], HoleUtils.mc.player.onGround));
            return true;
        }
        return false;
    }

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : HoleUtils.mc.world.loadedEntityList) {
            if (!new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    public static boolean isInHole(Entity entity) {
        return HoleUtils.isHole(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static void setTPS(double tps) {
        HoleUtils.mc.timer.tickLength = (float)(1000.0 / tps);
    }

    public static boolean isObsidianHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }
}

