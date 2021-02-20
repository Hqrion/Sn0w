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
    static Minecraft mc = Minecraft.func_71410_x();

    public static boolean isEntitiesEmpty(BlockPos pos) {
        List entities = BlocksUtils.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.func_176734_d();
            if (!BlocksUtils.canBeClicked(neighbor)) continue;
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
            if (rotate) {
                BlocksUtils.faceVectorPacketInstant(hitVec);
            }
            BlocksUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlocksUtils.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            BlocksUtils.processRightClickBlock(neighbor, side2, hitVec);
            BlocksUtils.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            BlocksUtils.mc.field_71467_ac = 0;
            BlocksUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlocksUtils.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            return true;
        }
        return false;
    }

    private static PlayerControllerMP getPlayerController() {
        return BlocksUtils.mc.field_71442_b;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        BlocksUtils.getPlayerController().func_187099_a(BlocksUtils.mc.field_71439_g, BlocksUtils.mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static IBlockState getState(BlockPos pos) {
        return BlocksUtils.mc.field_71441_e.func_180495_p(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return BlocksUtils.getState(pos).func_177230_c();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlocksUtils.getBlock(pos).func_176209_a(BlocksUtils.getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlocksUtils.getNeededRotations2(vec);
        BlocksUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlocksUtils.mc.field_71439_g.field_70122_E));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = BlocksUtils.getEyesPos();
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlocksUtils.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g((float)(yaw - BlocksUtils.mc.field_71439_g.field_70177_z)), BlocksUtils.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g((float)(pitch - BlocksUtils.mc.field_71439_g.field_70125_A))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(BlocksUtils.mc.field_71439_g.field_70165_t, BlocksUtils.mc.field_71439_g.field_70163_u + (double)BlocksUtils.mc.field_71439_g.func_70047_e(), BlocksUtils.mc.field_71439_g.field_70161_v);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(BlocksUtils.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return BlocksUtils.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
}

