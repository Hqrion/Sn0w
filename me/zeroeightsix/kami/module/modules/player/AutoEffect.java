//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.settings.KeyBinding
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

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="Auto Effect", description="Auto bow effect thing", category=Module.Category.PLAYER)
public class AutoEffect
extends Module {
    public boolean ready1 = false;
    public boolean ready2 = false;
    @EventHandler
    public Listener<PacketEvent.Send> sendPacket = new Listener<PacketEvent.Send>(event -> {
        if (event.getEra() == KamiEvent.Era.PRE) {
            if (event.getPacket() instanceof CPacketPlayer.Rotation && AutoEffect.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                ((CPacketPlayer.Rotation)event.getPacket()).pitch = -90.0f;
            }
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && AutoEffect.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                ((CPacketPlayer.PositionRotation)event.getPacket()).pitch = -90.0f;
            }
        }
    }, new Predicate[0]);

    @Override
    protected void onEnable() {
        super.onEnable();
        KeyBinding.setKeyBindState((int)AutoEffect.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
        Command.sendChatMessage(" This Feature IS " + ChatFormatting.RED + "Buggy " + ChatFormatting.WHITE + "RN im fixing");
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        KeyBinding.setKeyBindState((int)AutoEffect.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
        this.ready1 = false;
        this.ready2 = false;
    }

    @Override
    public void onUpdate() {
        if (AutoEffect.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && AutoEffect.mc.player.isHandActive() && AutoEffect.mc.player.getItemInUseMaxCount() >= 4) {
            AutoEffect.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, AutoEffect.mc.player.getHorizontalFacing()));
            AutoEffect.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(AutoEffect.mc.player.getActiveHand()));
            AutoEffect.mc.player.stopActiveHand();
            this.disable();
        }
    }
}

