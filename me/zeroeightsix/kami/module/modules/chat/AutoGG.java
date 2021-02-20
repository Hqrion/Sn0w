/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 */
package me.zeroeightsix.kami.module.modules.chat;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

@Module.Info(name="AutoEZ", category=Module.Category.CHAT, description="Announce killed Players")
public class AutoGG
extends Module {
    public static AutoGG INSTANCE;
    private ConcurrentHashMap<String, Integer> targetedPlayers = null;
    private Setting<Boolean> SayName = this.register(Settings.b("Say Name", false));
    private Setting<Hacker> appendooga = this.register(Settings.e("Message", Hacker.EZZBYSN0W));
    private Setting<Integer> timeoutTicks = this.register(Settings.i("TimeoutTicks", 20));
    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (AutoGG.mc.field_71439_g == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        if (!(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
        if (!cPacketUseEntity.func_149565_c().equals((Object)CPacketUseEntity.Action.ATTACK)) {
            return;
        }
        Entity targetEntity = cPacketUseEntity.func_149564_a((World)AutoGG.mc.field_71441_e);
        if (!EntityUtil.isPlayer(targetEntity)) {
            return;
        }
        this.addTargetedPlayer(targetEntity.func_70005_c_());
    }, new Predicate[0]);
    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener = new Listener<LivingDeathEvent>(event -> {
        String name;
        EntityPlayer player;
        EntityLivingBase entity;
        if (AutoGG.mc.field_71439_g == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        if ((entity = event.getEntityLiving()) != null && entity instanceof EntityPlayer && (player = (EntityPlayer)entity).func_110143_aJ() <= 0.0f && this.shouldAnnounce(name = player.func_70005_c_())) {
            this.doAnnounce(name);
        }
        if (!EntityUtil.isPlayer((Entity)entity)) {
            return;
        }
        player = (EntityPlayer)entity;
        if (player.func_110143_aJ() > 0.0f) {
            return;
        }
        name = player.func_70005_c_();
        if (this.shouldAnnounce(name)) {
            this.doAnnounce(name);
        }
    }, new Predicate[0]);

    public AutoGG() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap();
    }

    @Override
    public void onDisable() {
        this.targetedPlayers = null;
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || AutoGG.mc.field_71439_g == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        for (Entity entity : AutoGG.mc.field_71441_e.func_72910_y()) {
            String name2;
            EntityPlayer player;
            if (!EntityUtil.isPlayer(entity) || (player = (EntityPlayer)entity).func_110143_aJ() > 0.0f || !this.shouldAnnounce(name2 = player.func_70005_c_())) continue;
            this.doAnnounce(name2);
            break;
        }
        this.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.targetedPlayers.remove(name);
            } else {
                this.targetedPlayers.put((String)name, timeout - 1);
            }
        });
    }

    private boolean shouldAnnounce(String name) {
        return this.targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {
        this.targetedPlayers.remove(name);
        StringBuilder message = new StringBuilder();
        if (this.SayName.getValue().booleanValue()) {
            message.append(name + " ");
        }
        if (this.appendooga.getValue().equals((Object)Hacker.EZZBYSN0W)) {
            message.append("ezzzed by Sn0w");
        } else if (this.appendooga.getValue().equals((Object)Hacker.ECLIENT)) {
            message.append("EZED by ECLIENT");
        } else if (this.appendooga.getValue().equals((Object)Hacker.HOWDOIPUTTHIS)) {
            message.append("how do i put this..");
        } else if (this.appendooga.getValue().equals((Object)Hacker.GG)) {
            message.append("GG!");
        } else if (this.appendooga.getValue().equals((Object)Hacker.EZNONAMER)) {
            message.append("LISTEN HERE NONAMER! YOU JUST GOT EZZZED");
        } else if (this.appendooga.getValue().equals((Object)Hacker.NOOB)) {
            message.append("lol noob");
        } else if (this.appendooga.getValue().equals((Object)Hacker.MONTAGE)) {
            message.append("has been put in the montage");
        } else if (this.appendooga.getValue().equals((Object)Hacker.CAPPED)) {
            message.append("YOU JUST GOT CAPPED, by the impeccable power of Sn0w!");
        } else {
            message.append("GG!");
        }
        String messageSanitized = message.toString().replaceAll("\u00a7", "");
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }
        AutoGG.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized));
    }

    public void addTargetedPlayer(String name) {
        if (Objects.equals(name, AutoGG.mc.field_71439_g.func_70005_c_())) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        this.targetedPlayers.put(name, this.timeoutTicks.getValue());
    }

    private static enum Hacker {
        EZZBYSN0W,
        HOWDOIPUTTHIS,
        EZNONAMER,
        NOOB,
        ECLIENT,
        GG,
        MONTAGE,
        CAPPED;

    }
}

