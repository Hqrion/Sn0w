/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="FastBow", description="Fast Bow Release", category=Module.Category.COMBAT)
public class Skitdestroyer
extends Module {
    private Setting<Boolean> kill = this.register(Settings.b("Kill", true));

    @Override
    public void onEnable() {
        if (this.kill.getValue().booleanValue()) {
            Skitdestroyer.mc.field_71439_g.func_71165_d("/kill Skitttyy");
        }
    }
}

