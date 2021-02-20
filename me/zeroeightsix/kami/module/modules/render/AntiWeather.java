//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;

@Module.Info(name="AntiWeather", description="Removes rain from your world", category=Module.Category.RENDER)
public class AntiWeather
extends Module {
    @Override
    public void onUpdate() {
        if (this.isDisabled()) {
            return;
        }
        if (AntiWeather.mc.world.isRaining()) {
            AntiWeather.mc.world.setRainStrength(0.0f);
        }
    }
}

