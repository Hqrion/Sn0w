/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

@Module.Info(name="FastFall", description="", category=Module.Category.MOVEMENT)
public class FastFall
extends Module {
    private Setting<Boolean> twoBlock = this.register(Settings.b("TwoBlock", false));
    private Setting<Boolean> onlyIntoHoles = this.register(Settings.b("OnlyIntoHoles", false));
    boolean jumping = false;
    boolean falling = false;

    @Override
    public void onUpdate() {
        if (FastFall.mc.field_71441_e == null) {
            return;
        }
        if (FastFall.mc.field_71474_y.field_74314_A.func_151470_d()) {
            this.jumping = true;
        }
        if (this.jumping && FastFall.mc.field_71439_g.field_70122_E) {
            this.jumping = false;
        }
        if (this.jumping) {
            return;
        }
        boolean hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.field_71439_g, FastFall.mc.field_71439_g.func_174791_d().func_72441_c(0.0, -1.0, 0.0));
        boolean hullCollidesWithBlockHalf = this.hullCollidesWithBlock((Entity)FastFall.mc.field_71439_g, FastFall.mc.field_71439_g.func_174791_d().func_72441_c(0.0, -0.5, 0.0));
        if (hullCollidesWithBlockHalf) {
            return;
        }
        if (this.twoBlock.getValue().booleanValue() && !hullCollidesWithBlock) {
            hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.field_71439_g, FastFall.mc.field_71439_g.func_174791_d().func_72441_c(0.0, -2.0, 0.0));
        }
        if (!hullCollidesWithBlock && !FastFall.mc.field_71439_g.field_70122_E) {
            this.falling = true;
        }
        if (this.falling && FastFall.mc.field_71439_g.field_70122_E) {
            this.falling = false;
        }
        if (this.falling) {
            return;
        }
        AxisAlignedBB bb = FastFall.mc.field_71439_g.func_174813_aQ();
        for (int x = MathHelper.func_76128_c((double)bb.field_72340_a); x < MathHelper.func_76128_c((double)(bb.field_72336_d + 1.0)); ++x) {
            for (int z = MathHelper.func_76128_c((double)bb.field_72339_c); z < MathHelper.func_76128_c((double)(bb.field_72334_f + 1.0)); ++z) {
                Block block = FastFall.mc.field_71441_e.func_180495_p(new BlockPos((double)x, bb.field_72337_e - 2.0, (double)z)).func_177230_c();
                if (block instanceof BlockAir) continue;
                return;
            }
        }
        if (!hullCollidesWithBlock) {
            return;
        }
        if (FastFall.mc.field_71439_g.field_70122_E || FastFall.mc.field_71439_g.field_70134_J || FastFall.mc.field_71439_g.func_70617_f_() || FastFall.mc.field_71439_g.func_184613_cA() || FastFall.mc.field_71439_g.field_71075_bZ.field_75100_b || !FastFall.mc.field_71439_g.func_70055_a(Material.field_151579_a)) {
            return;
        }
        FastFall.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(FastFall.mc.field_71439_g.field_70165_t, FastFall.mc.field_71439_g.field_70163_u - 0.92, FastFall.mc.field_71439_g.field_70161_v, true));
        FastFall.mc.field_71439_g.func_70107_b(FastFall.mc.field_71439_g.field_70165_t, FastFall.mc.field_71439_g.field_70163_u - 0.92, FastFall.mc.field_71439_g.field_70161_v);
    }

    private boolean hullCollidesWithBlock(Entity entity, Vec3d nextPosition) {
        boolean atleastOne = false;
        AxisAlignedBB boundingBox = entity.func_174813_aQ();
        Vec3d[] boundingBoxCorners = new Vec3d[]{new Vec3d(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c), new Vec3d(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f), new Vec3d(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c), new Vec3d(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f)};
        Vec3d entityPosition = entity.func_174791_d();
        for (Vec3d entityBoxCorner : boundingBoxCorners) {
            Vec3d nextBoxCorner = entityBoxCorner.func_178788_d(entityPosition).func_178787_e(nextPosition);
            RayTraceResult rayTraceResult = entity.field_70170_p.func_147447_a(entityBoxCorner, nextBoxCorner, true, false, true);
            if (rayTraceResult == null) continue;
            if (!this.isAHole(new BlockPos(nextBoxCorner).func_177982_a(0, 1, 0)) && this.onlyIntoHoles.getValue().booleanValue()) {
                return false;
            }
            if (rayTraceResult.field_72313_a != RayTraceResult.Type.BLOCK) continue;
            atleastOne = true;
        }
        return atleastOne;
    }

    private boolean isAHole(BlockPos pos) {
        BlockPos bottom = pos.func_177982_a(0, -1, 0);
        BlockPos side1 = pos.func_177982_a(1, 0, 0);
        BlockPos side2 = pos.func_177982_a(-1, 0, 0);
        BlockPos side3 = pos.func_177982_a(0, 0, 1);
        BlockPos side4 = pos.func_177982_a(0, 0, -1);
        return !(FastFall.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a || FastFall.mc.field_71441_e.func_180495_p(bottom).func_177230_c() != Blocks.field_150357_h && FastFall.mc.field_71441_e.func_180495_p(side1).func_177230_c() != Blocks.field_150343_Z || FastFall.mc.field_71441_e.func_180495_p(side1).func_177230_c() != Blocks.field_150357_h && FastFall.mc.field_71441_e.func_180495_p(side1).func_177230_c() != Blocks.field_150343_Z || FastFall.mc.field_71441_e.func_180495_p(side2).func_177230_c() != Blocks.field_150357_h && FastFall.mc.field_71441_e.func_180495_p(side2).func_177230_c() != Blocks.field_150343_Z || FastFall.mc.field_71441_e.func_180495_p(side3).func_177230_c() != Blocks.field_150357_h && FastFall.mc.field_71441_e.func_180495_p(side3).func_177230_c() != Blocks.field_150343_Z || FastFall.mc.field_71441_e.func_180495_p(side4).func_177230_c() != Blocks.field_150357_h && FastFall.mc.field_71441_e.func_180495_p(side4).func_177230_c() != Blocks.field_150343_Z);
    }
}

