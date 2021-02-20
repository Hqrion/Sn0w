//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

@Module.Info(name="Better Crystal", description="Better Crystal", category=Module.Category.PLAYER)
public class NoCrystalWaste
extends Module {
    private Setting<Boolean> Chorus = this.register(Settings.b("Chorus", false));
    private Setting<Boolean> EmptyHand = this.register(Settings.b("EmptyHand", false));
    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.Chorus.getValue().booleanValue() && NoCrystalWaste.mc.player.getHeldItemMainhand().getItem() == Items.CHORUS_FRUIT) {
            event.cancel();
        }
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.EmptyHand.getValue().booleanValue() && NoCrystalWaste.mc.player.itemStackMainHand == ItemStack.EMPTY) {
            event.cancel();
        }
    }, new Predicate[0]);
}

