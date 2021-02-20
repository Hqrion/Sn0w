//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
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

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
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

public class BlocksUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isEntitiesEmpty(BlockPos pos) {
        List entities = BlocksUtils.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlocksUtils.canBeClicked(neighbor)) continue;
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (rotate) {
                BlocksUtils.faceVectorPacketInstant(hitVec);
            }
            BlocksUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlocksUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlocksUtils.processRightClickBlock(neighbor, side2, hitVec);
            BlocksUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
            BlocksUtils.mc.rightClickDelayTimer = 0;
            BlocksUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlocksUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            return true;
        }
        return false;
    }

    private static PlayerControllerMP getPlayerController() {
        return BlocksUtils.mc.playerController;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        BlocksUtils.getPlayerController().processRightClickBlock(BlocksUtils.mc.player, BlocksUtils.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static IBlockState getState(BlockPos pos) {
        return BlocksUtils.mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return BlocksUtils.getState(pos).getBlock();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlocksUtils.getBlock(pos).canCollideCheck(BlocksUtils.getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlocksUtils.getNeededRotations2(vec);
        BlocksUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlocksUtils.mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = BlocksUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlocksUtils.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - BlocksUtils.mc.player.rotationYaw)), BlocksUtils.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - BlocksUtils.mc.player.rotationPitch))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(BlocksUtils.mc.player.posX, BlocksUtils.mc.player.posY + (double)BlocksUtils.mc.player.getEyeHeight(), BlocksUtils.mc.player.posZ);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(BlocksUtils.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return BlocksUtils.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
}

