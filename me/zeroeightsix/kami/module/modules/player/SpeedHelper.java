//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBow
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="SpeedHelper", description="Lets u shoot urself with speed ez", category=Module.Category.PLAYER)
public class SpeedHelper
extends Module {
    @EventHandler
    public Listener<PacketEvent.Send> sendPacket = new Listener<PacketEvent.Send>(event -> {
        if (event.getEra() == KamiEvent.Era.PRE) {
            if (event.getPacket() instanceof CPacketPlayer.Rotation && SpeedHelper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                ((CPacketPlayer.Rotation)event.getPacket()).pitch = 5.0f;
            }
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && SpeedHelper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                ((CPacketPlayer.PositionRotation)event.getPacket()).pitch = 5.0f;
            }
        }
    }, new Predicate[0]);

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (SpeedHelper.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && SpeedHelper.mc.player.isHandActive() && SpeedHelper.mc.player.getItemInUseMaxCount() >= 4) {
            SpeedHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, SpeedHelper.mc.player.getHorizontalFacing()));
            SpeedHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(SpeedHelper.mc.player.getActiveHand()));
            SpeedHelper.mc.player.stopActiveHand();
        }
    }
}

