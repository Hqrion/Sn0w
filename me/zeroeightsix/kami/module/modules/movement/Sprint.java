/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.MotionUtils;
import net.minecraft.entity.EntityLivingBase;

@Module.Info(name="Sprint", description="Automatically makes the player sprint", category=Module.Category.MOVEMENT)
public class Sprint
extends Module {
    private Setting<Boolean> rage = this.register(Settings.b("Rage", false));

    @Override
    public void onUpdate() {
        block7: {
            if (!this.rage.getValue().booleanValue()) {
                try {
                    if (!Sprint.mc.field_71439_g.field_70123_F && Sprint.mc.field_71439_g.field_191988_bg > 0.0f) {
                        Sprint.mc.field_71439_g.func_70031_b(true);
                        break block7;
                    }
                    Sprint.mc.field_71439_g.func_70031_b(false);
                }
                catch (Exception exception) {}
            } else if (MotionUtils.isMoving((EntityLivingBase)Sprint.mc.field_71439_g)) {
                Sprint.mc.field_71439_g.func_70031_b(true);
            } else {
                Sprint.mc.field_71439_g.func_70031_b(false);
            }
        }
    }

    @Override
    public String getHudInfo() {
        if (this.rage.getValue().booleanValue()) {
            return "Rage";
        }
        return "Legit";
    }
}

