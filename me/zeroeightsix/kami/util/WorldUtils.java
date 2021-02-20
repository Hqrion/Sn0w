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
    private static final Minecraft mc = Minecraft.func_71410_x();
    public static List<Block> emptyBlocks = Arrays.asList(Blocks.field_150350_a, Blocks.field_150356_k, Blocks.field_150353_l, Blocks.field_150358_i, Blocks.field_150355_j, Blocks.field_150395_bd, Blocks.field_150431_aC, Blocks.field_150329_H, Blocks.field_150480_ab);
    public static List<Block> rightclickableBlocks = Arrays.asList(Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150477_bB, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA, Blocks.field_150467_bQ, Blocks.field_150471_bO, Blocks.field_150430_aB, Blocks.field_150441_bU, Blocks.field_150413_aR, Blocks.field_150416_aS, Blocks.field_150455_bV, Blocks.field_180390_bo, Blocks.field_180391_bp, Blocks.field_180392_bq, Blocks.field_180386_br, Blocks.field_180385_bs, Blocks.field_180387_bt, Blocks.field_150382_bo, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150421_aI, Blocks.field_150461_bJ, Blocks.field_150324_C, Blocks.field_150460_al, Blocks.field_180413_ao, Blocks.field_180414_ap, Blocks.field_180412_aq, Blocks.field_180411_ar, Blocks.field_180410_as, Blocks.field_180409_at, Blocks.field_150414_aQ, Blocks.field_150381_bn, Blocks.field_150380_bt, Blocks.field_150438_bZ, Blocks.field_185776_dc, Blocks.field_150483_bI, Blocks.field_185777_dd, Blocks.field_150462_ai);

    public static void openBlock(BlockPos pos) {
        EnumFacing[] facings;
        for (EnumFacing f : facings = EnumFacing.values()) {
            Block neighborBlock = WorldUtils.mc.field_71441_e.func_180495_p(pos.func_177972_a(f)).func_177230_c();
            if (!emptyBlocks.contains(neighborBlock)) continue;
            WorldUtils.mc.field_71442_b.func_187099_a(WorldUtils.mc.field_71439_g, WorldUtils.mc.field_71441_e, pos, f.func_176734_d(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            return;
        }
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack) {
        EnumFacing[] facings;
        if (!WorldUtils.isBlockEmpty(pos)) {
            return false;
        }
        if (slot != WorldUtils.mc.field_71439_g.field_71071_by.field_70461_c) {
            WorldUtils.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        }
        for (EnumFacing f : facings = EnumFacing.values()) {
            Block neighborBlock = WorldUtils.mc.field_71441_e.func_180495_p(pos.func_177972_a(f)).func_177230_c();
            Vec3d vec = new Vec3d((double)pos.func_177958_n() + 0.5 + (double)f.func_82601_c() * 0.5, (double)pos.func_177956_o() + 0.5 + (double)f.func_96559_d() * 0.5, (double)pos.func_177952_p() + 0.5 + (double)f.func_82599_e() * 0.5);
            if (emptyBlocks.contains(neighborBlock) || !(WorldUtils.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(vec) <= 4.25)) continue;
            float[] rot = new float[]{WorldUtils.mc.field_71439_g.field_70177_z, WorldUtils.mc.field_71439_g.field_70125_A};
            if (rotate) {
                WorldUtils.rotatePacket(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
            }
            if (rightclickableBlocks.contains(neighborBlock)) {
                WorldUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WorldUtils.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            }
            WorldUtils.mc.field_71442_b.func_187099_a(WorldUtils.mc.field_71439_g, WorldUtils.mc.field_71441_e, pos.func_177972_a(f), f.func_176734_d(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            if (rightclickableBlocks.contains(neighborBlock)) {
                WorldUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)WorldUtils.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (rotateBack) {
                WorldUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], WorldUtils.mc.field_71439_g.field_70122_E));
            }
            return true;
        }
        return false;
    }

    public static boolean isBlockEmpty(BlockPos pos) {
        Entity e;
        if (!emptyBlocks.contains(WorldUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
            return false;
        }
        AxisAlignedBB box = new AxisAlignedBB(pos);
        Iterator entityIter = WorldUtils.mc.field_71441_e.field_72996_f.iterator();
        do {
            if (entityIter.hasNext()) continue;
            return true;
        } while (!((e = (Entity)entityIter.next()) instanceof EntityLivingBase) || !box.func_72326_a(e.func_174813_aQ()));
        return false;
    }

    public static boolean canPlaceBlock(BlockPos pos) {
        EnumFacing[] facings;
        if (!WorldUtils.isBlockEmpty(pos)) {
            return false;
        }
        for (EnumFacing f : facings = EnumFacing.values()) {
            if (emptyBlocks.contains(WorldUtils.mc.field_71441_e.func_180495_p(pos.func_177972_a(f)).func_177230_c())) continue;
            Vec3d vec3d = new Vec3d((double)pos.func_177958_n() + 0.5 + (double)f.func_82601_c() * 0.5, (double)pos.func_177956_o() + 0.5 + (double)f.func_96559_d() * 0.5, (double)pos.func_177952_p() + 0.5 + (double)f.func_82599_e() * 0.5);
            if (!(WorldUtils.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(vec3d) <= 4.25)) continue;
            return true;
        }
        return false;
    }

    public static EnumFacing getClosestFacing(BlockPos pos) {
        return EnumFacing.DOWN;
    }

    public static void rotateClient(double x, double y, double z) {
        double diffX = x - WorldUtils.mc.field_71439_g.field_70165_t;
        double diffY = y - (WorldUtils.mc.field_71439_g.field_70163_u + (double)WorldUtils.mc.field_71439_g.func_70047_e());
        double diffZ = z - WorldUtils.mc.field_71439_g.field_70161_v;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WorldUtils.mc.field_71439_g.field_70177_z += MathHelper.func_76142_g((float)(yaw - WorldUtils.mc.field_71439_g.field_70177_z));
        WorldUtils.mc.field_71439_g.field_70125_A += MathHelper.func_76142_g((float)(pitch - WorldUtils.mc.field_71439_g.field_70125_A));
    }

    public static void rotatePacket(double x, double y, double z) {
        double diffX = x - WorldUtils.mc.field_71439_g.field_70165_t;
        double diffY = y - (WorldUtils.mc.field_71439_g.field_70163_u + (double)WorldUtils.mc.field_71439_g.func_70047_e());
        double diffZ = z - WorldUtils.mc.field_71439_g.field_70161_v;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WorldUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(yaw, pitch, WorldUtils.mc.field_71439_g.field_70122_E));
    }
}

