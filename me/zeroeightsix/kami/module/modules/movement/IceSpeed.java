/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.init.Blocks;

@Module.Info(name="IceSpeed", description="Ice Speed", category=Module.Category.MOVEMENT)
public class IceSpeed
extends Module {
    private Setting<Float> slipperiness = this.register(Settings.floatBuilder("Slipperiness").withMinimum(Float.valueOf(0.2f)).withValue(Float.valueOf(0.4f)).withMaximum(Float.valueOf(1.0f)).build());

    @Override
    public void onUpdate() {
        Blocks.field_150432_aD.field_149765_K = this.slipperiness.getValue().floatValue();
        Blocks.field_150403_cj.field_149765_K = this.slipperiness.getValue().floatValue();
        Blocks.field_185778_de.field_149765_K = this.slipperiness.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        Blocks.field_150432_aD.field_149765_K = 0.98f;
        Blocks.field_150403_cj.field_149765_K = 0.98f;
        Blocks.field_185778_de.field_149765_K = 0.98f;
    }
}

