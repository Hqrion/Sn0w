//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
            Skitdestroyer.mc.player.sendChatMessage("/kill Skitttyy");
        }
    }
}

