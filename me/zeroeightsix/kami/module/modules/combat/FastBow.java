/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBow
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="FastBow", description="Fast Bow Release", category=Module.Category.COMBAT)
public class FastBow
extends Module {
    @Override
    public void onUpdate() {
        if (FastBow.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && FastBow.mc.field_71439_g.func_184587_cr() && FastBow.mc.field_71439_g.func_184612_cw() >= 3) {
            FastBow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, FastBow.mc.field_71439_g.func_174811_aO()));
            FastBow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(FastBow.mc.field_71439_g.func_184600_cs()));
            FastBow.mc.field_71439_g.func_184597_cx();
        }
    }
}

