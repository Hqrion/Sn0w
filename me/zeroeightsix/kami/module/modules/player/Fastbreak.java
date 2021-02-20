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
        Fastbreak.mc.field_71442_b.field_78781_i = 0;
    }
}

