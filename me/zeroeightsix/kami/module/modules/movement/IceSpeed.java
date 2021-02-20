//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        Blocks.ICE.slipperiness = this.slipperiness.getValue().floatValue();
        Blocks.PACKED_ICE.slipperiness = this.slipperiness.getValue().floatValue();
        Blocks.FROSTED_ICE.slipperiness = this.slipperiness.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}

