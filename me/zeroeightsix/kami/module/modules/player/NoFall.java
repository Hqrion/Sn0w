/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Module.Info(category=Module.Category.PLAYER, description="Prevents fall damage", name="NoFall")
public class NoFall
extends Module {
    private Setting<Boolean> packet = this.register(Settings.b("Packet", false));
    private Setting<Boolean> bucket = this.register(Settings.b("Bucket", true));
    private Setting<Integer> distance = this.register(Settings.i("Distance", 15));
    private long last = 0L;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayer && this.packet.getValue().booleanValue()) {
            ((CPacketPlayer)event.getPacket()).field_149474_g = true;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        Vec3d posVec;
        RayTraceResult result;
        if (this.bucket.getValue().booleanValue() && NoFall.mc.field_71439_g.field_70143_R >= (float)this.distance.getValue().intValue() && !EntityUtil.isAboveWater((Entity)NoFall.mc.field_71439_g) && System.currentTimeMillis() - this.last > 100L && (result = NoFall.mc.field_71441_e.func_147447_a(posVec = NoFall.mc.field_71439_g.func_174791_d(), posVec.func_72441_c(0.0, (double)-5.33f, 0.0), true, true, false)) != null && result.field_72313_a == RayTraceResult.Type.BLOCK) {
            EnumHand hand = EnumHand.MAIN_HAND;
            if (NoFall.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151131_as) {
                hand = EnumHand.OFF_HAND;
            } else if (NoFall.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151131_as) {
                for (int i = 0; i < 9; ++i) {
                    if (NoFall.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_151131_as) continue;
                    NoFall.mc.field_71439_g.field_71071_by.field_70461_c = i;
                    NoFall.mc.field_71439_g.field_70125_A = 90.0f;
                    this.last = System.currentTimeMillis();
                    return;
                }
                return;
            }
            NoFall.mc.field_71439_g.field_70125_A = 90.0f;
            NoFall.mc.field_71442_b.func_187101_a((EntityPlayer)NoFall.mc.field_71439_g, (World)NoFall.mc.field_71441_e, hand);
            this.last = System.currentTimeMillis();
        }
    }
}

