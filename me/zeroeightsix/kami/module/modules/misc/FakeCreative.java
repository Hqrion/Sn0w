/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.GameType
 */
package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.world.GameType;

@Module.Info(name="FakeCreative", description="Fake GMC", category=Module.Category.HIDDEN)
public class FakeCreative
extends Module {
    @Override
    public void onEnable() {
        if (FakeCreative.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        FakeCreative.mc.field_71442_b.func_78746_a(GameType.CREATIVE);
    }

    @Override
    public void onDisable() {
        if (FakeCreative.mc.field_71439_g == null) {
            return;
        }
        FakeCreative.mc.field_71442_b.func_78746_a(GameType.SURVIVAL);
    }
}

