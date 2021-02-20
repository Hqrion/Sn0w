//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGameOver
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.GuiGameOver;

@Module.Info(name="AutoRespawn", description="Respawn utility", category=Module.Category.MISC)
public class AutoRespawn
extends Module {
    private Setting<Boolean> autoRespawn = this.register(Settings.b("Auto Respawn", true));
    private Setting<Boolean> deathCoords = this.register(Settings.b("Death Coords", true));
    private Setting<Boolean> antiBug = this.register(Settings.b("Anti Bug", true));
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener = new Listener<GuiScreenEvent.Displayed>(event -> {
        if (!(event.getScreen() instanceof GuiGameOver)) {
            return;
        }
        if (this.deathCoords.getValue().booleanValue() && AutoRespawn.mc.player.getHealth() <= 0.0f) {
            Command.sendChatMessage(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
        }
        if (this.autoRespawn.getValue().booleanValue() || this.antiBug.getValue().booleanValue() && AutoRespawn.mc.player.getHealth() > 0.0f) {
            AutoRespawn.mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }, new Predicate[0]);
}

