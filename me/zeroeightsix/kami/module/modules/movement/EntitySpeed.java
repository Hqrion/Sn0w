/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.chunk.EmptyChunk
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.MovementInput;
import net.minecraft.world.chunk.EmptyChunk;

@Module.Info(name="EntitySpeed", category=Module.Category.HIDDEN, description="Abuse client-sided movement to shape sound barrier breaking rideables")
public class EntitySpeed
extends Module {
    private Setting<Float> speed = this.register(Settings.f("Speed", 1.0f));
    private Setting<Boolean> antiStuck = this.register(Settings.b("AntiStuck"));
    private Setting<Boolean> flight = this.register(Settings.b("Flight", false));
    private Setting<Boolean> wobble = this.register(Settings.booleanBuilder("Wobble").withValue(true).withVisibility(b -> this.flight.getValue()).build());
    private static Setting<Float> opacity = Settings.f("Boat opacity", 0.5f);

    public EntitySpeed() {
        this.register(opacity);
    }

    @Override
    public void onUpdate() {
        if (EntitySpeed.mc.field_71441_e != null && EntitySpeed.mc.field_71439_g.func_184187_bx() != null) {
            Entity riding = EntitySpeed.mc.field_71439_g.func_184187_bx();
            if (riding instanceof EntityPig || riding instanceof AbstractHorse) {
                this.steerEntity(riding);
            } else if (riding instanceof EntityBoat) {
                this.steerBoat(this.getBoat());
            }
        }
    }

    private void steerEntity(Entity entity) {
        if (!this.flight.getValue().booleanValue()) {
            entity.field_70181_x = -0.4;
        }
        if (this.flight.getValue().booleanValue()) {
            if (EntitySpeed.mc.field_71474_y.field_74314_A.func_151470_d()) {
                entity.field_70181_x = this.speed.getValue().floatValue();
            } else if (EntitySpeed.mc.field_71474_y.field_74351_w.func_151470_d() || EntitySpeed.mc.field_71474_y.field_74368_y.func_151470_d()) {
                entity.field_70181_x = this.wobble.getValue() != false ? Math.sin(EntitySpeed.mc.field_71439_g.field_70173_aa) : 0.0;
            }
        }
        this.moveForward(entity, (double)this.speed.getValue().floatValue() * 3.8);
        if (entity instanceof EntityHorse) {
            entity.field_70177_z = EntitySpeed.mc.field_71439_g.field_70177_z;
        }
    }

    private void steerBoat(EntityBoat boat) {
        int angle;
        if (boat == null) {
            return;
        }
        boolean forward = EntitySpeed.mc.field_71474_y.field_74351_w.func_151470_d();
        boolean left = EntitySpeed.mc.field_71474_y.field_74370_x.func_151470_d();
        boolean right = EntitySpeed.mc.field_71474_y.field_74366_z.func_151470_d();
        boolean back = EntitySpeed.mc.field_71474_y.field_74368_y.func_151470_d();
        if (!forward || !back) {
            boat.field_70181_x = 0.0;
        }
        if (EntitySpeed.mc.field_71474_y.field_74314_A.func_151470_d()) {
            boat.field_70181_x += (double)(this.speed.getValue().floatValue() / 2.0f);
        }
        if (!(forward || left || right || back)) {
            return;
        }
        if (left && right) {
            angle = forward ? 0 : (back ? 180 : -1);
        } else if (forward && back) {
            angle = left ? -90 : (right ? 90 : -1);
        } else {
            int n = left ? -90 : (angle = right ? 90 : 0);
            if (forward) {
                angle /= 2;
            } else if (back) {
                angle = 180 - angle / 2;
            }
        }
        if (angle == -1) {
            return;
        }
        float yaw = EntitySpeed.mc.field_71439_g.field_70177_z + (float)angle;
        boat.field_70159_w = EntityUtil.getRelativeX(yaw) * (double)this.speed.getValue().floatValue();
        boat.field_70179_y = EntityUtil.getRelativeZ(yaw) * (double)this.speed.getValue().floatValue();
    }

    @Override
    public void onRender() {
        EntityBoat boat = this.getBoat();
        if (boat == null) {
            return;
        }
        boat.field_70177_z = EntitySpeed.mc.field_71439_g.field_70177_z;
        boat.func_184442_a(false, false, false, false);
    }

    private EntityBoat getBoat() {
        if (EntitySpeed.mc.field_71439_g.func_184187_bx() != null && EntitySpeed.mc.field_71439_g.func_184187_bx() instanceof EntityBoat) {
            return (EntityBoat)EntitySpeed.mc.field_71439_g.func_184187_bx();
        }
        return null;
    }

    private void moveForward(Entity entity, double speed) {
        if (entity != null) {
            MovementInput movementInput = EntitySpeed.mc.field_71439_g.field_71158_b;
            double forward = movementInput.field_192832_b;
            double strafe = movementInput.field_78902_a;
            boolean movingForward = forward != 0.0;
            boolean movingStrafe = strafe != 0.0;
            float yaw = EntitySpeed.mc.field_71439_g.field_70177_z;
            if (!movingForward && !movingStrafe) {
                this.setEntitySpeed(entity, 0.0, 0.0);
            } else {
                double motZ;
                double motX;
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += (float)(forward > 0.0 ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += (float)(forward > 0.0 ? 45 : -45);
                    }
                    strafe = 0.0;
                    forward = forward > 0.0 ? 1.0 : -1.0;
                }
                if (this.isBorderingChunk(entity, motX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)), motZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)))) {
                    motZ = 0.0;
                    motX = 0.0;
                }
                this.setEntitySpeed(entity, motX, motZ);
            }
        }
    }

    private void setEntitySpeed(Entity entity, double motX, double motZ) {
        entity.field_70159_w = motX;
        entity.field_70179_y = motZ;
    }

    private boolean isBorderingChunk(Entity entity, double motX, double motZ) {
        return this.antiStuck.getValue() != false && EntitySpeed.mc.field_71441_e.func_72964_e((int)(entity.field_70165_t + motX) >> 4, (int)(entity.field_70161_v + motZ) >> 4) instanceof EmptyChunk;
    }

    public static float getOpacity() {
        return opacity.getValue().floatValue();
    }
}

