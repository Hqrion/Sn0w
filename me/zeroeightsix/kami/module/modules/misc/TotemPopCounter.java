/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.HashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.TotemPopEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;

@Module.Info(name="TotemPopCounter", description="Counts the times your enemy pops", category=Module.Category.MISC)
public class TotemPopCounter
extends Module {
    public static TotemPopCounter INSTANCE;
    private HashMap<String, Integer> popList = new HashMap();
    private Setting<colour> mode = this.register(Settings.e("Colour", colour.DARK_GREEN));
    private Setting<Boolean> toxicmode = this.register(Settings.b("Toxic Mode", true));
    private Setting<Boolean> announce = this.register(Settings.b("Announce", false));
    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent = new Listener<TotemPopEvent>(event -> {
        if (this.popList == null) {
            this.popList = new HashMap();
        }
        if (this.popList.get(event.getEntity().func_70005_c_()) == null) {
            this.popList.put(event.getEntity().func_70005_c_(), 1);
            Command.sendChatMessage(this.colourchoice() + event.getEntity().func_70005_c_() + " popped " + 1 + " totem!");
        } else if (this.popList.get(event.getEntity().func_70005_c_()) != null) {
            int popCounter = this.popList.get(event.getEntity().func_70005_c_());
            int newPopCounter = ++popCounter;
            this.popList.put(event.getEntity().func_70005_c_(), newPopCounter);
            if (this.toxicmode.getValue().booleanValue()) {
                if (this.announce.getValue().booleanValue()) {
                    TotemPopCounter.mc.field_71439_g.func_71165_d("/w " + event.getEntity().func_70005_c_() + " you popped " + newPopCounter + " totems! ez");
                } else {
                    Command.sendChatMessage(this.colourchoice() + event.getEntity().func_70005_c_() + " popped " + newPopCounter + " totems! ez");
                }
            } else if (this.announce.getValue().booleanValue()) {
                Command.sendChatMessage("/w " + event.getEntity().func_70005_c_() + " you popped " + newPopCounter + " totems!");
            } else {
                Command.sendChatMessage(event.getEntity().func_70005_c_() + " popped " + newPopCounter + " totems!");
            }
        }
    }, new Predicate[0]);
    @EventHandler
    public Listener<PacketEvent.Receive> totemPopListener = new Listener<PacketEvent.Receive>(event -> {
        SPacketEntityStatus packet;
        if (TotemPopCounter.mc.field_71441_e == null || TotemPopCounter.mc.field_71439_g == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).func_149160_c() == 35) {
            Entity entity = packet.func_149161_a((World)TotemPopCounter.mc.field_71441_e);
            KamiMod.EVENT_BUS.post(new TotemPopEvent(entity));
        }
    }, new Predicate[0]);

    public TotemPopCounter() {
        INSTANCE = this;
    }

    public HashMap<String, Integer> getpoplist() {
        return this.popList;
    }

    @Override
    public void onUpdate() {
        for (EntityPlayer player : TotemPopCounter.mc.field_71441_e.field_73010_i) {
            if (!(player.func_110143_aJ() <= 0.0f) || !this.popList.containsKey(player.func_70005_c_())) continue;
            if (this.toxicmode.getValue().booleanValue()) {
                Command.sendChatMessage(this.colourchoice() + player.func_70005_c_() + "\u00a74died after popping\u00a7a " + this.popList.get(player.func_70005_c_()) + " \u00a74EZZZZ!");
            } else {
                Command.sendChatMessage(this.colourchoice() + player.func_70005_c_() + "\u00a74died after popping\u00a7a " + this.popList.get(player.func_70005_c_()) + " \u00a74!");
            }
            this.popList.remove(player.func_70005_c_(), this.popList.get(player.func_70005_c_()));
        }
    }

    private String colourchoice() {
        switch (this.mode.getValue()) {
            case BLACK: {
                return "&0";
            }
            case RED: {
                return "&c";
            }
            case AQUA: {
                return "&b";
            }
            case BLUE: {
                return "&9";
            }
            case GOLD: {
                return "&6";
            }
            case GRAY: {
                return "&7";
            }
            case WHITE: {
                return "&f";
            }
            case GREEN: {
                return "&a";
            }
            case YELLOW: {
                return "&e";
            }
            case DARK_RED: {
                return "&4";
            }
            case DARK_AQUA: {
                return "&3";
            }
            case DARK_BLUE: {
                return "&1";
            }
            case DARK_GRAY: {
                return "&8";
            }
            case DARK_GREEN: {
                return "&2";
            }
            case DARK_PURPLE: {
                return "&5";
            }
            case LIGHT_PURPLE: {
                return "&d";
            }
        }
        return "";
    }

    private static enum colour {
        BLACK,
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW,
        WHITE;

    }
}

