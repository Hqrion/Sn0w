//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.PlayerMoveEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;

@Module.Info(name="Freecam", category=Module.Category.HIDDEN, description="Leave your body and trascend into the realm of the gods")
public class Freecam
extends Module {
    private Setting<Integer> speed = this.register(Settings.i("Speed", 5));
    private double posX;
    private double posY;
    private double posZ;
    private float pitch;
    private float yaw;
    private EntityOtherPlayerMP clonedPlayer;
    private boolean isRidingEntity;
    private Entity ridingEntity;
    @EventHandler
    private Listener<PlayerMoveEvent> moveListener = new Listener<PlayerMoveEvent>(event -> {
        Freecam.mc.player.noClip = true;
    }, new Predicate[0]);
    @EventHandler
    private Listener<PlayerSPPushOutOfBlocksEvent> pushListener = new Listener<PlayerSPPushOutOfBlocksEvent>(event -> event.setCanceled(true), new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) {
            event.cancel();
        }
    }, new Predicate[0]);

    @Override
    protected void onEnable() {
        if (Freecam.mc.player != null) {
            boolean bl = this.isRidingEntity = Freecam.mc.player.getRidingEntity() != null;
            if (Freecam.mc.player.getRidingEntity() == null) {
                this.posX = Freecam.mc.player.posX;
                this.posY = Freecam.mc.player.posY;
                this.posZ = Freecam.mc.player.posZ;
            } else {
                this.ridingEntity = Freecam.mc.player.getRidingEntity();
                Freecam.mc.player.dismountRidingEntity();
            }
            this.pitch = Freecam.mc.player.rotationPitch;
            this.yaw = Freecam.mc.player.rotationYaw;
            this.clonedPlayer = new EntityOtherPlayerMP((World)Freecam.mc.world, mc.getSession().getProfile());
            this.clonedPlayer.copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.clonedPlayer.rotationYawHead = Freecam.mc.player.rotationYawHead;
            Freecam.mc.world.addEntityToWorld(-100, (Entity)this.clonedPlayer);
            Freecam.mc.player.capabilities.isFlying = true;
            Freecam.mc.player.capabilities.setFlySpeed((float)this.speed.getValue().intValue() / 100.0f);
            Freecam.mc.player.noClip = true;
        }
    }

    @Override
    protected void onDisable() {
        EntityPlayerSP localPlayer = Freecam.mc.player;
        if (localPlayer != null) {
            Freecam.mc.player.setPositionAndRotation(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
            Freecam.mc.world.removeEntityFromWorld(-100);
            this.clonedPlayer = null;
            this.posZ = 0.0;
            this.posY = 0.0;
            this.posX = 0.0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            Freecam.mc.player.capabilities.isFlying = false;
            Freecam.mc.player.capabilities.setFlySpeed(0.05f);
            Freecam.mc.player.noClip = false;
            Freecam.mc.player.motionZ = 0.0;
            Freecam.mc.player.motionY = 0.0;
            Freecam.mc.player.motionX = 0.0;
            if (this.isRidingEntity) {
                Freecam.mc.player.startRiding(this.ridingEntity, true);
            }
        }
    }

    @Override
    public void onUpdate() {
        Freecam.mc.player.capabilities.isFlying = true;
        Freecam.mc.player.capabilities.setFlySpeed((float)this.speed.getValue().intValue() / 100.0f);
        Freecam.mc.player.noClip = true;
        Freecam.mc.player.onGround = false;
        Freecam.mc.player.fallDistance = 0.0f;
    }
}

