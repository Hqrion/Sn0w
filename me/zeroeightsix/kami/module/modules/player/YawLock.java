/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.util.math.MathHelper;

@Module.Info(name="YawLock", category=Module.Category.PLAYER)
public class YawLock
extends Module {
    private Setting<Boolean> auto = this.register(Settings.b("Auto", true));
    private Setting<Float> yaw = this.register(Settings.f("Yaw", 180.0f));
    private Setting<Integer> slice = this.register(Settings.i("Slice", 8));

    @Override
    public void onUpdate() {
        if (this.slice.getValue() == 0) {
            return;
        }
        if (this.auto.getValue().booleanValue()) {
            int angle = 360 / this.slice.getValue();
            float yaw = YawLock.mc.field_71439_g.field_70177_z;
            YawLock.mc.field_71439_g.field_70177_z = yaw = (float)(Math.round(yaw / (float)angle) * angle);
            if (YawLock.mc.field_71439_g.func_184218_aH()) {
                YawLock.mc.field_71439_g.func_184187_bx().field_70177_z = yaw;
            }
        } else {
            YawLock.mc.field_71439_g.field_70177_z = MathHelper.func_76131_a((float)(this.yaw.getValue().floatValue() - 180.0f), (float)-180.0f, (float)180.0f);
        }
    }
}

