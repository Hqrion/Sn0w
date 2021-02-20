//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (FakeCreative.mc.player == null) {
            this.disable();
            return;
        }
        FakeCreative.mc.playerController.setGameType(GameType.CREATIVE);
    }

    @Override
    public void onDisable() {
        if (FakeCreative.mc.player == null) {
            return;
        }
        FakeCreative.mc.playerController.setGameType(GameType.SURVIVAL);
    }
}

