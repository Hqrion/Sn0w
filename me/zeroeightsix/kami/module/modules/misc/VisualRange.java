//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 */
package me.zeroeightsix.kami.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

@Module.Info(name="VisualRange", description="Reports Players in VisualRange", category=Module.Category.MISC)
public class VisualRange
extends Module {
    private Setting<Boolean> LOGonRender = this.register(Settings.b("Log On Render ", false));
    private Setting<Boolean> publicChat = this.register(Settings.b("PublicChat", false));
    private Setting<Boolean> leaving = this.register(Settings.b("Leaving", false));
    private Setting<Boolean> sound = this.register(Settings.b("sound", false));
    private List<String> knownPlayers;

    @Override
    public void onUpdate() {
        if (VisualRange.mc.player == null) {
            return;
        }
        ArrayList<String> tickPlayerList = new ArrayList<String>();
        for (Entity entity : VisualRange.mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityPlayer)) continue;
            tickPlayerList.add(entity.getName());
        }
        if (tickPlayerList.size() > 0) {
            for (String playerName : tickPlayerList) {
                if (playerName.equals(VisualRange.mc.player.getName()) || this.knownPlayers.contains(playerName)) continue;
                this.knownPlayers.add(playerName);
                if (this.publicChat.getValue().booleanValue()) {
                    VisualRange.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("" + playerName + " Nigger Spotted "));
                } else {
                    if (Friends.isFriend(playerName)) {
                        this.sendNotification(" " + ChatFormatting.GRAY.toString() + playerName + ChatFormatting.GRAY.toString() + " \u00a7aEn v\u00e4n the nigaga monkey has been spotted.");
                    } else {
                        this.sendNotification(" " + ChatFormatting.GRAY.toString() + playerName + ChatFormatting.GRAY.toString() + " \u00a74En the non nigaga monkey spotted.");
                    }
                    if (this.sound.getValue().booleanValue()) {
                        VisualRange.mc.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                    }
                }
                if (this.LOGonRender.getValue().booleanValue()) {
                    VisualRange.mc.player.world.sendQuittingDisconnectingPacket();
                }
                return;
            }
        }
        if (this.knownPlayers.size() > 0) {
            for (String playerName : this.knownPlayers) {
                if (tickPlayerList.contains(playerName)) continue;
                this.knownPlayers.remove(playerName);
                if (this.leaving.getValue().booleanValue()) {
                    if (this.publicChat.getValue().booleanValue()) {
                        VisualRange.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Jag kan inte se: " + playerName + " l\u00e4ngre! "));
                    } else if (Friends.isFriend(playerName)) {
                        this.sendNotification("[VisualRange] " + ChatFormatting.GREEN.toString() + playerName + ChatFormatting.RESET.toString() + " left my Render Distance!");
                    } else {
                        this.sendNotification("[VisualRange] " + ChatFormatting.RED.toString() + playerName + ChatFormatting.RESET.toString() + " left my Render Distance!");
                    }
                }
                return;
            }
        }
    }

    private void sendNotification(String s) {
        Command.sendChatMessage(s);
    }

    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<String>();
    }
}

