/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="AntiFog", description="Disables or reduces fog", category=Module.Category.RENDER)
public class AntiFog
extends Module {
    public static Setting<VisionMode> mode = Settings.e("Mode", VisionMode.AIR);
    private static AntiFog INSTANCE = new AntiFog();

    public AntiFog() {
        INSTANCE = this;
        this.register(mode);
    }

    public static boolean enabled() {
        return INSTANCE.isEnabled();
    }

    public static enum VisionMode {
        NOFOG,
        AIR;

    }
}

