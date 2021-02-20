//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if ((GMSpeed.mc.player.moveForward != 0.0f || GMSpeed.mc.player.moveStrafing != 0.0f) && !GMSpeed.mc.player.isSneaking() && GMSpeed.mc.player.onGround) {
            GMSpeed.mc.player.motionX *= this.gmspeed.getValue().doubleValue();
            GMSpeed.mc.player.motionZ *= this.gmspeed.getValue().doubleValue();
        }
    }
}

