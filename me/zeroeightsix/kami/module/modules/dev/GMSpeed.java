/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="GMSpeed", category=Module.Category.HIDDEN, description="Godmode Speed")
public class GMSpeed
extends Module {
    private Setting<Double> gmspeed = this.register(Settings.doubleBuilder("Speed").withRange(0.1, 10.0).withValue(1.0).build());

    @Override
    public void onUpdate() {
        if ((GMSpeed.mc.field_71439_g.field_191988_bg != 0.0f || GMSpeed.mc.field_71439_g.field_70702_br != 0.0f) && !GMSpeed.mc.field_71439_g.func_70093_af() && GMSpeed.mc.field_71439_g.field_70122_E) {
            GMSpeed.mc.field_71439_g.field_70159_w *= this.gmspeed.getValue().doubleValue();
            GMSpeed.mc.field_71439_g.field_70179_y *= this.gmspeed.getValue().doubleValue();
        }
    }
}

