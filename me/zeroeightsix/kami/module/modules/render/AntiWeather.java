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
        if (AntiWeather.mc.field_71441_e.func_72896_J()) {
            AntiWeather.mc.field_71441_e.func_72894_k(0.0f);
        }
    }
}

