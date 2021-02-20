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
        if (entity.func_184586_b(EnumHand.MAIN_HAND).func_190926_b()) {
            return ThrowingType.NONE;
        }
        ItemStack itemStack = entity.func_184586_b(EnumHand.MAIN_HAND);
        Item item = itemStack.func_77973_b();
        if (item instanceof ItemPotion) {
            if (itemStack.func_77973_b() instanceof ItemSplashPotion) {
                return ThrowingType.POTION;
            }
        } else {
            if (item instanceof ItemBow && entity.func_184587_cr()) {
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
        double posX = TrajectoryCalculator.interpolate(entity.field_70165_t, entity.field_70142_S) - Wrapper.getMinecraft().field_175616_W.field_78725_b;
        double posY = TrajectoryCalculator.interpolate(entity.field_70163_u, entity.field_70137_T) - Wrapper.getMinecraft().field_175616_W.field_78726_c;
        double posZ = TrajectoryCalculator.interpolate(entity.field_70161_v, entity.field_70136_U) - Wrapper.getMinecraft().field_175616_W.field_78723_d;
        return new double[]{posX, posY, posZ};
    }

    public static double interpolate(double now, double then) {
        return then + (now - then) * (double)Wrapper.getMinecraft().func_184121_ak();
    }

    public static Vec3d mult(Vec3d factor, float multiplier) {
        return new Vec3d(factor.field_72450_a * (double)multiplier, factor.field_72448_b * (double)multiplier, factor.field_72449_c * (double)multiplier);
    }

    public static Vec3d div(Vec3d factor, float divisor) {
        return new Vec3d(factor.field_72450_a / (double)divisor, factor.field_72448_b / (double)divisor, factor.field_72449_c / (double)divisor);
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
            this.setLocationAndAngles(ipos[0] + Wrapper.getMinecraft().func_175598_ae().field_78725_b, ipos[1] + (double)this.shooter.func_70047_e() + Wrapper.getMinecraft().func_175598_ae().field_78726_c, ipos[2] + Wrapper.getMinecraft().func_175598_ae().field_78723_d, this.shooter.field_70177_z, this.shooter.field_70125_A);
            Vec3d startingOffset = new Vec3d((double)(MathHelper.func_76134_b((float)(this.yaw / 180.0f * (float)Math.PI)) * 0.16f), 0.1, (double)(MathHelper.func_76126_a((float)(this.yaw / 180.0f * (float)Math.PI)) * 0.16f));
            this.position = this.position.func_178788_d(startingOffset);
            this.setPosition(this.position);
            this.motion = new Vec3d((double)(-MathHelper.func_76126_a((float)(this.yaw / 180.0f * (float)Math.PI)) * MathHelper.func_76134_b((float)(this.pitch / 180.0f * (float)Math.PI))), (double)(-MathHelper.func_76126_a((float)(this.pitch / 180.0f * (float)Math.PI))), (double)(MathHelper.func_76134_b((float)(this.yaw / 180.0f * (float)Math.PI)) * MathHelper.func_76134_b((float)(this.pitch / 180.0f * (float)Math.PI))));
            this.setThrowableHeading(this.motion, this.getInitialVelocity());
        }

        public void onUpdate() {
            Vec3d prediction = this.position.func_178787_e(this.motion);
            RayTraceResult blockCollision = this.shooter.func_130014_f_().func_147447_a(this.position, prediction, false, true, false);
            if (blockCollision != null) {
                prediction = blockCollision.field_72307_f;
            }
            this.onCollideWithEntity(prediction, blockCollision);
            if (this.target != null) {
                this.collided = true;
                this.setPosition(this.target.field_72307_f);
                return;
            }
            if (this.position.field_72448_b <= 0.0) {
                this.collided = true;
                return;
            }
            this.position = this.position.func_178787_e(this.motion);
            float motionModifier = 0.99f;
            if (this.shooter.func_130014_f_().func_72875_a(this.boundingBox, Material.field_151586_h)) {
                motionModifier = this.throwingType == ThrowingType.BOW ? 0.6f : 0.8f;
            }
            this.motion = TrajectoryCalculator.mult(this.motion, motionModifier);
            this.motion = this.motion.func_178786_a(0.0, (double)this.getGravityVelocity(), 0.0);
            this.setPosition(this.position);
        }

        private void onCollideWithEntity(Vec3d prediction, RayTraceResult blockCollision) {
            Entity collidingEntity = null;
            double currentDistance = 0.0;
            List collisionEntities = this.shooter.field_70170_p.func_72839_b((Entity)this.shooter, this.boundingBox.func_72321_a(this.motion.field_72450_a, this.motion.field_72448_b, this.motion.field_72449_c).func_72321_a(1.0, 1.0, 1.0));
            for (Entity entity : collisionEntities) {
                double distanceTo;
                if (!entity.func_70067_L() && entity != this.shooter) continue;
                float collisionSize = entity.func_70111_Y();
                AxisAlignedBB expandedBox = entity.func_174813_aQ().func_72321_a((double)collisionSize, (double)collisionSize, (double)collisionSize);
                RayTraceResult objectPosition = expandedBox.func_72327_a(this.position, prediction);
                if (objectPosition == null || !((distanceTo = this.position.func_72438_d(objectPosition.field_72307_f)) < currentDistance) && currentDistance != 0.0) continue;
                collidingEntity = entity;
                currentDistance = distanceTo;
            }
            this.target = collidingEntity != null ? new RayTraceResult(collidingEntity) : blockCollision;
        }

        private float getInitialVelocity() {
            Item item = this.shooter.func_184586_b(EnumHand.MAIN_HAND).func_77973_b();
            switch (this.throwingType) {
                case BOW: {
                    ItemBow bow = (ItemBow)item;
                    int useDuration = bow.func_77626_a(this.shooter.func_184586_b(EnumHand.MAIN_HAND)) - this.shooter.func_184605_cv();
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
            this.position = new Vec3d(position.field_72450_a, position.field_72448_b, position.field_72449_c);
            double entitySize = (this.throwingType == ThrowingType.BOW ? 0.5 : 0.25) / 2.0;
            this.boundingBox = new AxisAlignedBB(position.field_72450_a - entitySize, position.field_72448_b - entitySize, position.field_72449_c - entitySize, position.field_72450_a + entitySize, position.field_72448_b + entitySize, position.field_72449_c + entitySize);
        }

        private void setThrowableHeading(Vec3d motion, float velocity) {
            this.motion = TrajectoryCalculator.div(motion, (float)motion.func_72433_c());
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

