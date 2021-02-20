/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;

@Module.Info(name="Fastplace", category=Module.Category.PLAYER, description="Nullifies block place delay")
public class Fastplace
extends Module {
    @Override
    public void onUpdate() {
        Fastplace.mc.field_71467_ac = 0;
    }
}

