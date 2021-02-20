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

@Module.Info(name="PitchLock", category=Module.Category.HIDDEN)
public class PitchLock
extends Module {
    private Setting<Boolean> auto = this.register(Settings.b("Auto", true));
    private Setting<Float> pitch = this.register(Settings.f("Pitch", 180.0f));
    private Setting<Integer> slice = this.register(Settings.i("Slice", 8));

    @Override
    public void onUpdate() {
        if (this.slice.getValue() == 0) {
            return;
        }
        if (this.auto.getValue().booleanValue()) {
            int angle = 360 / this.slice.getValue();
            float yaw = PitchLock.mc.player.rotationPitch;
            PitchLock.mc.player.rotationPitch = yaw = (float)(Math.round(yaw / (float)angle) * angle);
            if (PitchLock.mc.player.isRiding()) {
                PitchLock.mc.player.getRidingEntity().rotationPitch = yaw;
            }
        } else {
            PitchLock.mc.player.rotationPitch = MathHelper.clamp((float)(this.pitch.getValue().floatValue() - 180.0f), (float)-180.0f, (float)180.0f);
        }
    }
}

