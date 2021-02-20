//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
            float yaw = YawLock.mc.player.rotationYaw;
            YawLock.mc.player.rotationYaw = yaw = (float)(Math.round(yaw / (float)angle) * angle);
            if (YawLock.mc.player.isRiding()) {
                YawLock.mc.player.getRidingEntity().rotationYaw = yaw;
            }
        } else {
            YawLock.mc.player.rotationYaw = MathHelper.clamp((float)(this.yaw.getValue().floatValue() - 180.0f), (float)-180.0f, (float)180.0f);
        }
    }
}

