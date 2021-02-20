//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.util;

import java.util.List;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TrajectoryCalculator {
    public static ThrowingType getThrowType(EntityLivingBase entity) {
        if (entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
            return ThrowingType.NONE;
        }
        ItemStack itemStack = entity.getHeldItem(EnumHand.MAIN_HAND);
        Item item = itemStack.getItem();
        if (item instanceof ItemPotion) {
            if (itemStack.getItem() instanceof ItemSplashPotion) {
                return ThrowingType.POTION;
            }
        } else {
            if (item instanceof ItemBow && entity.isHandActive()) {
                return ThrowingType.BOW;
            }
            if (item instanceof ItemExpBottle) {
                return ThrowingType.EXPERIENCE;
            }
            if (item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl) {
                return ThrowingType.NORMAL;
            }
        }
        return ThrowingType.NONE;
    }

    public static double[] interpolate(Entity entity) {
        double posX = TrajectoryCalculator.interpolate(entity.posX, entity.lastTickPosX) - Wrapper.getMinecraft().renderManager.renderPosX;
        double posY = TrajectoryCalculator.interpolate(entity.posY, entity.lastTickPosY) - Wrapper.getMinecraft().renderManager.renderPosY;
        double posZ = TrajectoryCalculator.interpolate(entity.posZ, entity.lastTickPosZ) - Wrapper.getMinecraft().renderManager.renderPosZ;
        return new double[]{posX, posY, posZ};
    }

    public static double interpolate(double now, double then) {
        return then + (now - then) * (double)Wrapper.getMinecraft().getRenderPartialTicks();
    }

    public static Vec3d mult(Vec3d factor, float multiplier) {
        return new Vec3d(factor.x * (double)multiplier, factor.y * (double)multiplier, factor.z * (double)multiplier);
    }

    public static Vec3d div(Vec3d factor, float divisor) {
        return new Vec3d(factor.x / (double)divisor, factor.y / (double)divisor, factor.z / (double)divisor);
    }

    public static final class FlightPath {
        private EntityLivingBase shooter;
        public Vec3d position;
        private Vec3d motion;
        private float yaw;
        private float pitch;
        private AxisAlignedBB boundingBox;
        private boolean collided;
        private RayTraceResult target;
        private ThrowingType throwingType;

        public FlightPath(EntityLivingBase entityLivingBase, ThrowingType throwingType) {
            this.shooter = entityLivingBase;
            this.throwingType = throwingType;
            double[] ipos = TrajectoryCalculator.interpolate((Entity)this.shooter);
            this.setLocationAndAngles(ipos[0] + Wrapper.getMinecraft().getRenderManager().renderPosX, ipos[1] + (double)this.shooter.getEyeHeight() + Wrapper.getMinecraft().getRenderManager().renderPosY, ipos[2] + Wrapper.getMinecraft().getRenderManager().renderPosZ, this.shooter.rotationYaw, this.shooter.rotationPitch);
            Vec3d startingOffset = new Vec3d((double)(MathHelper.cos((float)(this.yaw / 180.0f * (float)Math.PI)) * 0.16f), 0.1, (double)(MathHelper.sin((float)(this.yaw / 180.0f * (float)Math.PI)) * 0.16f));
            this.position = this.position.subtract(startingOffset);
            this.setPosition(this.position);
            this.motion = new Vec3d((double)(-MathHelper.sin((float)(this.yaw / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(this.pitch / 180.0f * (float)Math.PI))), (double)(-MathHelper.sin((float)(this.pitch / 180.0f * (float)Math.PI))), (double)(MathHelper.cos((float)(this.yaw / 180.0f * (float)Math.PI)) * MathHelper.cos((float)(this.pitch / 180.0f * (float)Math.PI))));
            this.setThrowableHeading(this.motion, this.getInitialVelocity());
        }

        public void onUpdate() {
            Vec3d prediction = this.position.add(this.motion);
            RayTraceResult blockCollision = this.shooter.getEntityWorld().rayTraceBlocks(this.position, prediction, false, true, false);
            if (blockCollision != null) {
                prediction = blockCollision.hitVec;
            }
            this.onCollideWithEntity(prediction, blockCollision);
            if (this.target != null) {
                this.collided = true;
                this.setPosition(this.target.hitVec);
                return;
            }
            if (this.position.y <= 0.0) {
                this.collided = true;
                return;
            }
            this.position = this.position.add(this.motion);
            float motionModifier = 0.99f;
            if (this.shooter.getEntityWorld().isMaterialInBB(this.boundingBox, Material.WATER)) {
                motionModifier = this.throwingType == ThrowingType.BOW ? 0.6f : 0.8f;
            }
            this.motion = TrajectoryCalculator.mult(this.motion, motionModifier);
            this.motion = this.motion.subtract(0.0, (double)this.getGravityVelocity(), 0.0);
            this.setPosition(this.position);
        }

        private void onCollideWithEntity(Vec3d prediction, RayTraceResult blockCollision) {
            Entity collidingEntity = null;
            double currentDistance = 0.0;
            List collisionEntities = this.shooter.world.getEntitiesWithinAABBExcludingEntity((Entity)this.shooter, this.boundingBox.expand(this.motion.x, this.motion.y, this.motion.z).expand(1.0, 1.0, 1.0));
            for (Entity entity : collisionEntities) {
                double distanceTo;
                if (!entity.canBeCollidedWith() && entity != this.shooter) continue;
                float collisionSize = entity.getCollisionBorderSize();
                AxisAlignedBB expandedBox = entity.getEntityBoundingBox().expand((double)collisionSize, (double)collisionSize, (double)collisionSize);
                RayTraceResult objectPosition = expandedBox.calculateIntercept(this.position, prediction);
                if (objectPosition == null || !((distanceTo = this.position.distanceTo(objectPosition.hitVec)) < currentDistance) && currentDistance != 0.0) continue;
                collidingEntity = entity;
                currentDistance = distanceTo;
            }
            this.target = collidingEntity != null ? new RayTraceResult(collidingEntity) : blockCollision;
        }

        private float getInitialVelocity() {
            Item item = this.shooter.getHeldItem(EnumHand.MAIN_HAND).getItem();
            switch (this.throwingType) {
                case BOW: {
                    ItemBow bow = (ItemBow)item;
                    int useDuration = bow.getMaxItemUseDuration(this.shooter.getHeldItem(EnumHand.MAIN_HAND)) - this.shooter.getItemInUseCount();
                    float velocity = (float)useDuration / 20.0f;
                    velocity = (velocity * velocity + velocity * 2.0f) / 3.0f;
                    if (velocity > 1.0f) {
                        velocity = 1.0f;
                    }
                    return velocity * 2.0f * 1.5f;
                }
                case POTION: {
                    return 0.5f;
                }
                case EXPERIENCE: {
                    return 0.7f;
                }
                case NORMAL: {
                    return 1.5f;
                }
            }
            return 1.5f;
        }

        private float getGravityVelocity() {
            switch (this.throwingType) {
                case BOW: 
                case POTION: {
                    return 0.05f;
                }
                case EXPERIENCE: {
                    return 0.07f;
                }
                case NORMAL: {
                    return 0.03f;
                }
            }
            return 0.03f;
        }

        private void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
            this.position = new Vec3d(x, y, z);
            this.yaw = yaw;
            this.pitch = pitch;
        }

        private void setPosition(Vec3d position) {
            this.position = new Vec3d(position.x, position.y, position.z);
            double entitySize = (this.throwingType == ThrowingType.BOW ? 0.5 : 0.25) / 2.0;
            this.boundingBox = new AxisAlignedBB(position.x - entitySize, position.y - entitySize, position.z - entitySize, position.x + entitySize, position.y + entitySize, position.z + entitySize);
        }

        private void setThrowableHeading(Vec3d motion, float velocity) {
            this.motion = TrajectoryCalculator.div(motion, (float)motion.length());
            this.motion = TrajectoryCalculator.mult(this.motion, velocity);
        }

        public boolean isCollided() {
            return this.collided;
        }

        public RayTraceResult getCollidingTarget() {
            return this.target;
        }
    }

    public static enum ThrowingType {
        NONE,
        BOW,
        EXPERIENCE,
        POTION,
        NORMAL;

    }
}

