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
        if (this.deathCoords.getValue().booleanValue() && AutoRespawn.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
            Command.sendChatMessage(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.field_71439_g.field_70165_t, (int)AutoRespawn.mc.field_71439_g.field_70163_u, (int)AutoRespawn.mc.field_71439_g.field_70161_v));
        }
        if (this.autoRespawn.getValue().booleanValue() || this.antiBug.getValue().booleanValue() && AutoRespawn.mc.field_71439_g.func_110143_aJ() > 0.0f) {
            AutoRespawn.mc.field_71439_g.func_71004_bE();
            mc.func_147108_a(null);
        }
    }, new Predicate[0]);
}

