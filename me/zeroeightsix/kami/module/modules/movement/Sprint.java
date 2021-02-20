//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
                    if (!Sprint.mc.player.collidedHorizontally && Sprint.mc.player.moveForward > 0.0f) {
                        Sprint.mc.player.setSprinting(true);
                        break block7;
                    }
                    Sprint.mc.player.setSprinting(false);
                }
                catch (Exception exception) {}
            } else if (MotionUtils.isMoving((EntityLivingBase)Sprint.mc.player)) {
                Sprint.mc.player.setSprinting(true);
            } else {
                Sprint.mc.player.setSprinting(false);
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

