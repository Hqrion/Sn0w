//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;

@Module.Info(name="AutoJump", category=Module.Category.HIDDEN, description="Automatically jumps if possible")
public class AutoJump
extends Module {
    @Override
    public void onUpdate() {
        if (AutoJump.mc.player.isInWater() || AutoJump.mc.player.isInLava()) {
            AutoJump.mc.player.motionY = 0.1;
        } else if (AutoJump.mc.player.onGround) {
            AutoJump.mc.player.jump();
        }
    }
}

