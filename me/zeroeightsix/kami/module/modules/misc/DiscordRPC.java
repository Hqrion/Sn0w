/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import java.util.Date;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="DiscordRPC", category=Module.Category.MISC, description="Discord RPC")
public class DiscordRPC
extends Module {
    private static final String applicationId = "793201261159120927";
    private Setting<Boolean> ServerIp = this.register(Settings.b("Server IP", true));
    private Setting<Boolean> AltLogo = this.register(Settings.b("Alt Logo", true));
    private club.minnced.discord.rpc.DiscordRPC discordRPC = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
    DiscordEventHandlers handlers = new DiscordEventHandlers();
    DiscordRichPresence presence = new DiscordRichPresence();
    int delayTimeTicks;

    @Override
    public void onEnable() {
        this.init();
    }

    @Override
    public void onUpdate() {
        if (this.delayTimeTicks < 100) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        this.presence.largeImageKey = this.AltLogo.getValue() != false ? "logo" : "sn0wlogosjnez";
        this.presence.state = "ezzing nonamers";
        this.presence.largeImageText = "Gaming";
        this.discordRPC.Discord_UpdatePresence(this.presence);
        this.presence.details = DiscordRPC.mc.field_71441_e != null ? (mc.func_71356_B() ? "playing singleplayer" : (this.ServerIp.getValue().booleanValue() ? "Playing " + DiscordRPC.mc.func_147104_D().field_78845_b : "Playing multiplayer")) : "Chilling in main menu";
    }

    @Override
    public void onDisable() {
        this.discordRPC.Discord_Shutdown();
    }

    private void init() {
        long gamer = new Date().getTime();
        this.discordRPC.Discord_Initialize(applicationId, this.handlers, true, "");
        this.presence.startTimestamp = gamer;
        this.presence.largeImageKey = this.AltLogo.getValue() != false ? "logo" : "sn0wlogosjnez";
        this.presence.state = "ezzing nonamers";
        this.presence.largeImageText = "Gaming";
        this.discordRPC.Discord_UpdatePresence(this.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.discordRPC.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }, "RPC-Callback-Handler").start();
    }
}

