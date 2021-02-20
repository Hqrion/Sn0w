/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="Auto Trash", category=Module.Category.CHAT)
public class AutoGrefGrupIsTrash
extends Module {
    private Setting<Boolean> grefGrup = this.register(Settings.b("Gref Grup", false));
    private Setting<Boolean> builderseighttool = this.register(Settings.b("8b8t", false));

    @Override
    public void onEnable() {
        if (this.grefGrup.getValue().booleanValue()) {
            AutoGrefGrupIsTrash.mc.field_71439_g.func_71165_d("Gref Grup is trash");
            this.disable();
        } else if (this.builderseighttool.getValue().booleanValue()) {
            AutoGrefGrupIsTrash.mc.field_71439_g.func_71165_d("8 Bad 8 tps");
            this.disable();
        } else {
            AutoGrefGrupIsTrash.mc.field_71439_g.func_71165_d("YOU ARE TRASH");
            this.disable();
        }
    }
}

