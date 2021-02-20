/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="Capes", description="Show fancy Capes", category=Module.Category.RENDER)
public class Capes
extends Module {
    private static Capes INSTANCE;
    public static Setting<Boolean> WhiteCape;

    public Capes() {
        this.register(WhiteCape);
        INSTANCE = this;
    }

    public static boolean isActive() {
        return INSTANCE.isEnabled();
    }

    static {
        WhiteCape = Settings.b("WhiteCape", false);
    }
}

