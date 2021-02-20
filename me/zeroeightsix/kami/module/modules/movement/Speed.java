//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.PlayerMoveEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.MotionUtils;
import net.minecraft.entity.EntityLivingBase;

@Module.Info(name="Speed", description="Modify player speed on ground.", category=Module.Category.MOVEMENT)
public class Speed
extends Module {
    private Setting<Float> speed = this.register(Settings.floatBuilder("Speed").withMinimum(Float.valueOf(0.0f)).withValue(Float.valueOf(1.4f)).withMaximum(Float.valueOf(10.0f)).build());
    private Setting<speedmethods> speedmode = this.register(Settings.e("Mode", speedmethods.NORMAL));
    boolean gamer;
    @EventHandler
    private Listener<PlayerMoveEvent> moveListener = new Listener<PlayerMoveEvent>(event -> {
        if (this.speedmode.getValue().equals((Object)speedmethods.IDK)) {
            if (!(Speed.mc.player.isSneaking() || Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.movementInput.moveForward == 0.0f && Speed.mc.player.movementInput.moveStrafe == 0.0f)) {
                MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, EntityUtil.getMaxSpeed());
                if (this.gamer) {
                    Speed.mc.gameSettings.viewBobbing = false;
                }
            } else {
                MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed());
                if (!(Speed.mc.player.isSneaking() || Speed.mc.player.isInWater() || Speed.mc.player.isInLava() || Speed.mc.player.movementInput.moveForward == 0.0f && Speed.mc.player.movementInput.moveStrafe == 0.0f)) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, EntityUtil.getMaxSpeed());
                    if (this.gamer) {
                        Speed.mc.gameSettings.viewBobbing = true;
                    }
                }
            }
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        if (Speed.mc.gameSettings.viewBobbing) {
            this.gamer = true;
        }
        KamiMod.EVENT_BUS.subscribe((Object)this);
    }

    @Override
    protected void onDisable() {
        if (this.speedmode.getValue().equals((Object)speedmethods.IDK)) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
            if (this.gamer) {
                Speed.mc.gameSettings.viewBobbing = true;
            }
            MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed());
        }
        this.gamer = false;
    }

    @Override
    public void onUpdate() {
        if (this.speed.getValue().equals((Object)speedmethods.NORMAL) && (Speed.mc.player.moveForward != 0.0f || Speed.mc.player.moveStrafing != 0.0f) && !Speed.mc.player.isSneaking() && Speed.mc.player.onGround) {
            Speed.mc.player.jump();
            Speed.mc.player.motionX *= (double)this.speed.getValue().floatValue();
            Speed.mc.player.motionY *= 0.4;
            Speed.mc.player.motionZ *= (double)this.speed.getValue().floatValue();
        }
    }

    @Override
    public String getHudInfo() {
        if (this.speedmode.getValue().equals((Object)speedmethods.IDK)) {
            return "IDK";
        }
        return null;
    }

    private static enum speedmethods {
        NORMAL,
        IDK;

    }
}

