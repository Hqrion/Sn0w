//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityMinecart
 *  net.minecraft.entity.passive.EntityLlama
 *  net.minecraft.util.MovementInput
 */
package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.MovementInput;

@Module.Info(name="GMEntitySpeed", category=Module.Category.HIDDEN, description="Godmode EntitySpeed")
public class GMEntitySpeed
extends Module {
    private Setting<Double> gmentityspeed = this.register(Settings.doubleBuilder("Speed").withRange(0.1, 10.0).withValue(1.0).build());

    private static void speedEntity(Entity entity, Double speed) {
        if (entity instanceof EntityLlama) {
            entity.rotationYaw = GMEntitySpeed.mc.player.rotationYaw;
            ((EntityLlama)entity).rotationYawHead = GMEntitySpeed.mc.player.rotationYawHead;
        }
        MovementInput movementInput = GMEntitySpeed.mc.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = GMEntitySpeed.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            entity.motionX = 0.0;
            entity.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            entity.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            entity.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
            if (entity instanceof EntityMinecart) {
                EntityMinecart em = (EntityMinecart)entity;
                em.setVelocity(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)), em.motionY, forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
            }
        }
    }

    @Override
    public void onUpdate() {
        try {
            if (GMEntitySpeed.mc.player.getRidingEntity() != null) {
                GMEntitySpeed.speedEntity(GMEntitySpeed.mc.player.getRidingEntity(), this.gmentityspeed.getValue());
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: Dude we kinda have a problem here:");
            e.printStackTrace();
        }
    }
}

