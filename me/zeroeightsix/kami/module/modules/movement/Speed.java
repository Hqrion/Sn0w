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
            if (!(Speed.mc.field_71439_g.func_70093_af() || Speed.mc.field_71439_g.func_70090_H() || Speed.mc.field_71439_g.func_180799_ab() || Speed.mc.field_71439_g.field_71158_b.field_192832_b == 0.0f && Speed.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f)) {
                MotionUtils.setSpeed((EntityLivingBase)Speed.mc.field_71439_g, EntityUtil.getMaxSpeed());
                if (this.gamer) {
                    Speed.mc.field_71474_y.field_74336_f = false;
                }
            } else {
                MotionUtils.setSpeed((EntityLivingBase)Speed.mc.field_71439_g, MotionUtils.getBaseMoveSpeed());
                if (!(Speed.mc.field_71439_g.func_70093_af() || Speed.mc.field_71439_g.func_70090_H() || Speed.mc.field_71439_g.func_180799_ab() || Speed.mc.field_71439_g.field_71158_b.field_192832_b == 0.0f && Speed.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f)) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.field_71439_g, EntityUtil.getMaxSpeed());
                    if (this.gamer) {
                        Speed.mc.field_71474_y.field_74336_f = true;
                    }
                }
            }
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        if (Speed.mc.field_71474_y.field_74336_f) {
            this.gamer = true;
        }
        KamiMod.EVENT_BUS.subscribe((Object)this);
    }

    @Override
    protected void onDisable() {
        if (this.speedmode.getValue().equals((Object)speedmethods.IDK)) {
            KamiMod.EVENT_BUS.unsubscribe((Object)this);
            if (this.gamer) {
                Speed.mc.field_71474_y.field_74336_f = true;
            }
            MotionUtils.setSpeed((EntityLivingBase)Speed.mc.field_71439_g, MotionUtils.getBaseMoveSpeed());
        }
        this.gamer = false;
    }

    @Override
    public void onUpdate() {
        if (this.speed.getValue().equals((Object)speedmethods.NORMAL) && (Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) && !Speed.mc.field_71439_g.func_70093_af() && Speed.mc.field_71439_g.field_70122_E) {
            Speed.mc.field_71439_g.func_70664_aZ();
            Speed.mc.field_71439_g.field_70159_w *= (double)this.speed.getValue().floatValue();
            Speed.mc.field_71439_g.field_70181_x *= 0.4;
            Speed.mc.field_71439_g.field_70179_y *= (double)this.speed.getValue().floatValue();
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

