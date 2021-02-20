//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;

@Module.Info(name="GMFly", category=Module.Category.HIDDEN, description="Godmode Fly")
public class GMFly
extends Module {
    @Override
    public void onEnable() {
        this.toggleFly(true);
    }

    @Override
    public void onDisable() {
        this.toggleFly(false);
    }

    @Override
    public void onUpdate() {
        this.toggleFly(true);
    }

    private void toggleFly(boolean b) {
        GMFly.mc.player.capabilities.isFlying = b;
        if (GMFly.mc.player.capabilities.isCreativeMode) {
            return;
        }
        GMFly.mc.player.capabilities.allowFlying = b;
    }
}

