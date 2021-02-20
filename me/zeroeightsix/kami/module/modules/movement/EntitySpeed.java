//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (EntitySpeed.mc.world != null && EntitySpeed.mc.player.getRidingEntity() != null) {
            Entity riding = EntitySpeed.mc.player.getRidingEntity();
            if (riding instanceof EntityPig || riding instanceof AbstractHorse) {
                this.steerEntity(riding);
            } else if (riding instanceof EntityBoat) {
                this.steerBoat(this.getBoat());
            }
        }
    }

    private void steerEntity(Entity entity) {
        if (!this.flight.getValue().booleanValue()) {
            entity.motionY = -0.4;
        }
        if (this.flight.getValue().booleanValue()) {
            if (EntitySpeed.mc.gameSettings.keyBindJump.isKeyDown()) {
                entity.motionY = this.speed.getValue().floatValue();
            } else if (EntitySpeed.mc.gameSettings.keyBindForward.isKeyDown() || EntitySpeed.mc.gameSettings.keyBindBack.isKeyDown()) {
                entity.motionY = this.wobble.getValue() != false ? Math.sin(EntitySpeed.mc.player.ticksExisted) : 0.0;
            }
        }
        this.moveForward(entity, (double)this.speed.getValue().floatValue() * 3.8);
        if (entity instanceof EntityHorse) {
            entity.rotationYaw = EntitySpeed.mc.player.rotationYaw;
        }
    }

    private void steerBoat(EntityBoat boat) {
        int angle;
        if (boat == null) {
            return;
        }
        boolean forward = EntitySpeed.mc.gameSettings.keyBindForward.isKeyDown();
        boolean left = EntitySpeed.mc.gameSettings.keyBindLeft.isKeyDown();
        boolean right = EntitySpeed.mc.gameSettings.keyBindRight.isKeyDown();
        boolean back = EntitySpeed.mc.gameSettings.keyBindBack.isKeyDown();
        if (!forward || !back) {
            boat.motionY = 0.0;
        }
        if (EntitySpeed.mc.gameSettings.keyBindJump.isKeyDown()) {
            boat.motionY += (double)(this.speed.getValue().floatValue() / 2.0f);
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
        float yaw = EntitySpeed.mc.player.rotationYaw + (float)angle;
        boat.motionX = EntityUtil.getRelativeX(yaw) * (double)this.speed.getValue().floatValue();
        boat.motionZ = EntityUtil.getRelativeZ(yaw) * (double)this.speed.getValue().floatValue();
    }

    @Override
    public void onRender() {
        EntityBoat boat = this.getBoat();
        if (boat == null) {
            return;
        }
        boat.rotationYaw = EntitySpeed.mc.player.rotationYaw;
        boat.updateInputs(false, false, false, false);
    }

    private EntityBoat getBoat() {
        if (EntitySpeed.mc.player.getRidingEntity() != null && EntitySpeed.mc.player.getRidingEntity() instanceof EntityBoat) {
            return (EntityBoat)EntitySpeed.mc.player.getRidingEntity();
        }
        return null;
    }

    private void moveForward(Entity entity, double speed) {
        if (entity != null) {
            MovementInput movementInput = EntitySpeed.mc.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            boolean movingForward = forward != 0.0;
            boolean movingStrafe = strafe != 0.0;
            float yaw = EntitySpeed.mc.player.rotationYaw;
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
        entity.motionX = motX;
        entity.motionZ = motZ;
    }

    private boolean isBorderingChunk(Entity entity, double motX, double motZ) {
        return this.antiStuck.getValue() != false && EntitySpeed.mc.world.getChunk((int)(entity.posX + motX) >> 4, (int)(entity.posZ + motZ) >> 4) instanceof EmptyChunk;
    }

    public static float getOpacity() {
        return opacity.getValue().floatValue();
    }
}

