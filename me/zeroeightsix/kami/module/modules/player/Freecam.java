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
        Freecam.mc.field_71439_g.field_70145_X = true;
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
        if (Freecam.mc.field_71439_g != null) {
            boolean bl = this.isRidingEntity = Freecam.mc.field_71439_g.func_184187_bx() != null;
            if (Freecam.mc.field_71439_g.func_184187_bx() == null) {
                this.posX = Freecam.mc.field_71439_g.field_70165_t;
                this.posY = Freecam.mc.field_71439_g.field_70163_u;
                this.posZ = Freecam.mc.field_71439_g.field_70161_v;
            } else {
                this.ridingEntity = Freecam.mc.field_71439_g.func_184187_bx();
                Freecam.mc.field_71439_g.func_184210_p();
            }
            this.pitch = Freecam.mc.field_71439_g.field_70125_A;
            this.yaw = Freecam.mc.field_71439_g.field_70177_z;
            this.clonedPlayer = new EntityOtherPlayerMP((World)Freecam.mc.field_71441_e, mc.func_110432_I().func_148256_e());
            this.clonedPlayer.func_82149_j((Entity)Freecam.mc.field_71439_g);
            this.clonedPlayer.field_70759_as = Freecam.mc.field_71439_g.field_70759_as;
            Freecam.mc.field_71441_e.func_73027_a(-100, (Entity)this.clonedPlayer);
            Freecam.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            Freecam.mc.field_71439_g.field_71075_bZ.func_75092_a((float)this.speed.getValue().intValue() / 100.0f);
            Freecam.mc.field_71439_g.field_70145_X = true;
        }
    }

    @Override
    protected void onDisable() {
        EntityPlayerSP localPlayer = Freecam.mc.field_71439_g;
        if (localPlayer != null) {
            Freecam.mc.field_71439_g.func_70080_a(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
            Freecam.mc.field_71441_e.func_73028_b(-100);
            this.clonedPlayer = null;
            this.posZ = 0.0;
            this.posY = 0.0;
            this.posX = 0.0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            Freecam.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            Freecam.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05f);
            Freecam.mc.field_71439_g.field_70145_X = false;
            Freecam.mc.field_71439_g.field_70179_y = 0.0;
            Freecam.mc.field_71439_g.field_70181_x = 0.0;
            Freecam.mc.field_71439_g.field_70159_w = 0.0;
            if (this.isRidingEntity) {
                Freecam.mc.field_71439_g.func_184205_a(this.ridingEntity, true);
            }
        }
    }

    @Override
    public void onUpdate() {
        Freecam.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
        Freecam.mc.field_71439_g.field_71075_bZ.func_75092_a((float)this.speed.getValue().intValue() / 100.0f);
        Freecam.mc.field_71439_g.field_70145_X = true;
        Freecam.mc.field_71439_g.field_70122_E = false;
        Freecam.mc.field_71439_g.field_70143_R = 0.0f;
    }
}

