//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;

@Module.Info(name="Fastbreak", category=Module.Category.PLAYER, description="Nullifies block hit delay")
public class Fastbreak
extends Module {
    @Override
    public void onUpdate() {
        Fastbreak.mc.playerController.blockHitDelay = 0;
    }
}

