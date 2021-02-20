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
    private static final Minecraft mc = Minecraft.func_71410_x();

    public static boolean isBreakable(BlockPos pos) {
        return HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176195_g(HoleUtils.mc.field_71441_e.func_180495_p(pos), (World)HoleUtils.mc.field_71441_e, pos) != -1.0f;
    }

    public static boolean placeBlock(BlockPos pos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177972_a(enumFacing)).func_177230_c().equals(Blocks.field_150350_a) || HoleUtils.isIntercepted(pos)) continue;
            Vec3d vec = new Vec3d((double)pos.func_177958_n() + 0.5 + (double)enumFacing.func_82601_c() * 0.5, (double)pos.func_177956_o() + 0.5 + (double)enumFacing.func_96559_d() * 0.5, (double)pos.func_177952_p() + 0.5 + (double)enumFacing.func_82599_e() * 0.5);
            float[] old = new float[]{HoleUtils.mc.field_71439_g.field_70177_z, HoleUtils.mc.field_71439_g.field_70125_A};
            HoleUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.field_72449_c - HoleUtils.mc.field_71439_g.field_70161_v, vec.field_72450_a - HoleUtils.mc.field_71439_g.field_70165_t)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.field_72448_b - (HoleUtils.mc.field_71439_g.field_70163_u + (double)HoleUtils.mc.field_71439_g.func_70047_e()), Math.sqrt((vec.field_72450_a - HoleUtils.mc.field_71439_g.field_70165_t) * (vec.field_72450_a - HoleUtils.mc.field_71439_g.field_70165_t) + (vec.field_72449_c - HoleUtils.mc.field_71439_g.field_70161_v) * (vec.field_72449_c - HoleUtils.mc.field_71439_g.field_70161_v))))), HoleUtils.mc.field_71439_g.field_70122_E));
            HoleUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)HoleUtils.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            HoleUtils.mc.field_71442_b.func_187099_a(HoleUtils.mc.field_71439_g, HoleUtils.mc.field_71441_e, pos.func_177972_a(enumFacing), enumFacing.func_176734_d(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            HoleUtils.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            HoleUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)HoleUtils.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            HoleUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(old[0], old[1], HoleUtils.mc.field_71439_g.field_70122_E));
            return true;
        }
        return false;
    }

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : HoleUtils.mc.field_71441_e.field_72996_f) {
            if (!new AxisAlignedBB(pos).func_72326_a(entity.func_174813_aQ())) continue;
            return true;
        }
        return false;
    }

    public static boolean isInHole(Entity entity) {
        return HoleUtils.isHole(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
    }

    public static void setTPS(double tps) {
        HoleUtils.mc.field_71428_T.field_194149_e = (float)(1000.0 / tps);
    }

    public static boolean isObsidianHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 3, 0)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b()}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150343_Z) continue;
            return false;
        }
        return true;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b()}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150357_h) continue;
            return false;
        }
        return true;
    }

    public static boolean isHole(BlockPos blockPos) {
        if (!(HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b()}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && (iBlockState.func_177230_c() == Blocks.field_150357_h || iBlockState.func_177230_c() == Blocks.field_150343_Z)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(2, 0, 0), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(1, 0, -1), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && (iBlockState.func_177230_c() == Blocks.field_150357_h || iBlockState.func_177230_c() == Blocks.field_150343_Z)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 1)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(0, 0, 2), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(-1, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(1, 0, 0), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && (iBlockState.func_177230_c() == Blocks.field_150357_h || iBlockState.func_177230_c() == Blocks.field_150343_Z)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(2, 0, 0), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(1, 0, -1), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150357_h) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 1)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(0, 0, 2), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(-1, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(1, 0, 0), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150357_h) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleX(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(2, 0, 0), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(1, 0, -1), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(1, -1, 0)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150343_Z) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleZ(BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 1)).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 1)).func_177230_c().equals(Blocks.field_150350_a)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.func_177982_a(0, 0, 2), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(-1, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(1, 0, 0), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(0, -1, 1)}) {
            IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() != Blocks.field_150350_a && iBlockState.func_177230_c() == Blocks.field_150343_Z) continue;
            return false;
        }
        return true;
    }
}

