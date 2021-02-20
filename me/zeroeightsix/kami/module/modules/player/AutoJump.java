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
        if (AutoJump.mc.field_71439_g.func_70090_H() || AutoJump.mc.field_71439_g.func_180799_ab()) {
            AutoJump.mc.field_71439_g.field_70181_x = 0.1;
        } else if (AutoJump.mc.field_71439_g.field_70122_E) {
            AutoJump.mc.field_71439_g.func_70664_aZ();
        }
    }
}

