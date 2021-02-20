//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (FastFall.mc.world == null) {
            return;
        }
        if (FastFall.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.jumping = true;
        }
        if (this.jumping && FastFall.mc.player.onGround) {
            this.jumping = false;
        }
        if (this.jumping) {
            return;
        }
        boolean hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -1.0, 0.0));
        boolean hullCollidesWithBlockHalf = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -0.5, 0.0));
        if (hullCollidesWithBlockHalf) {
            return;
        }
        if (this.twoBlock.getValue().booleanValue() && !hullCollidesWithBlock) {
            hullCollidesWithBlock = this.hullCollidesWithBlock((Entity)FastFall.mc.player, FastFall.mc.player.getPositionVector().add(0.0, -2.0, 0.0));
        }
        if (!hullCollidesWithBlock && !FastFall.mc.player.onGround) {
            this.falling = true;
        }
        if (this.falling && FastFall.mc.player.onGround) {
            this.falling = false;
        }
        if (this.falling) {
            return;
        }
        AxisAlignedBB bb = FastFall.mc.player.getEntityBoundingBox();
        for (int x = MathHelper.floor((double)bb.minX); x < MathHelper.floor((double)(bb.maxX + 1.0)); ++x) {
            for (int z = MathHelper.floor((double)bb.minZ); z < MathHelper.floor((double)(bb.maxZ + 1.0)); ++z) {
                Block block = FastFall.mc.world.getBlockState(new BlockPos((double)x, bb.maxY - 2.0, (double)z)).getBlock();
                if (block instanceof BlockAir) continue;
                return;
            }
        }
        if (!hullCollidesWithBlock) {
            return;
        }
        if (FastFall.mc.player.onGround || FastFall.mc.player.isInWeb || FastFall.mc.player.isOnLadder() || FastFall.mc.player.isElytraFlying() || FastFall.mc.player.capabilities.isFlying || !FastFall.mc.player.isInsideOfMaterial(Material.AIR)) {
            return;
        }
        FastFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(FastFall.mc.player.posX, FastFall.mc.player.posY - 0.92, FastFall.mc.player.posZ, true));
        FastFall.mc.player.setPosition(FastFall.mc.player.posX, FastFall.mc.player.posY - 0.92, FastFall.mc.player.posZ);
    }

    private boolean hullCollidesWithBlock(Entity entity, Vec3d nextPosition) {
        boolean atleastOne = false;
        AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        Vec3d[] boundingBoxCorners = new Vec3d[]{new Vec3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ), new Vec3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ), new Vec3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ), new Vec3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ)};
        Vec3d entityPosition = entity.getPositionVector();
        for (Vec3d entityBoxCorner : boundingBoxCorners) {
            Vec3d nextBoxCorner = entityBoxCorner.subtract(entityPosition).add(nextPosition);
            RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(entityBoxCorner, nextBoxCorner, true, false, true);
            if (rayTraceResult == null) continue;
            if (!this.isAHole(new BlockPos(nextBoxCorner).add(0, 1, 0)) && this.onlyIntoHoles.getValue().booleanValue()) {
                return false;
            }
            if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
            atleastOne = true;
        }
        return atleastOne;
    }

    private boolean isAHole(BlockPos pos) {
        BlockPos bottom = pos.add(0, -1, 0);
        BlockPos side1 = pos.add(1, 0, 0);
        BlockPos side2 = pos.add(-1, 0, 0);
        BlockPos side3 = pos.add(0, 0, 1);
        BlockPos side4 = pos.add(0, 0, -1);
        return !(FastFall.mc.world.getBlockState(pos).getBlock() != Blocks.AIR || FastFall.mc.world.getBlockState(bottom).getBlock() != Blocks.BEDROCK && FastFall.mc.world.getBlockState(side1).getBlock() != Blocks.OBSIDIAN || FastFall.mc.world.getBlockState(side1).getBlock() != Blocks.BEDROCK && FastFall.mc.world.getBlockState(side1).getBlock() != Blocks.OBSIDIAN || FastFall.mc.world.getBlockState(side2).getBlock() != Blocks.BEDROCK && FastFall.mc.world.getBlockState(side2).getBlock() != Blocks.OBSIDIAN || FastFall.mc.world.getBlockState(side3).getBlock() != Blocks.BEDROCK && FastFall.mc.world.getBlockState(side3).getBlock() != Blocks.OBSIDIAN || FastFall.mc.world.getBlockState(side4).getBlock() != Blocks.BEDROCK && FastFall.mc.world.getBlockState(side4).getBlock() != Blocks.OBSIDIAN);
    }
}

