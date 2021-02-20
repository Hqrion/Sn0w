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
            entity.field_70177_z = GMEntitySpeed.mc.field_71439_g.field_70177_z;
            ((EntityLlama)entity).field_70759_as = GMEntitySpeed.mc.field_71439_g.field_70759_as;
        }
        MovementInput movementInput = GMEntitySpeed.mc.field_71439_g.field_71158_b;
        double forward = movementInput.field_192832_b;
        double strafe = movementInput.field_78902_a;
        float yaw = GMEntitySpeed.mc.field_71439_g.field_70177_z;
        if (forward == 0.0 && strafe == 0.0) {
            entity.field_70159_w = 0.0;
            entity.field_70179_y = 0.0;
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
            entity.field_70159_w = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            entity.field_70179_y = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
            if (entity instanceof EntityMinecart) {
                EntityMinecart em = (EntityMinecart)entity;
                em.func_70016_h(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)), em.field_70181_x, forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
            }
        }
    }

    @Override
    public void onUpdate() {
        try {
            if (GMEntitySpeed.mc.field_71439_g.func_184187_bx() != null) {
                GMEntitySpeed.speedEntity(GMEntitySpeed.mc.field_71439_g.func_184187_bx(), this.gmentityspeed.getValue());
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: Dude we kinda have a problem here:");
            e.printStackTrace();
        }
    }
}

