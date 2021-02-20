/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.event.events.EntityEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

@Module.Info(name="Velocity", description="Modify knockback impact", category=Module.Category.MOVEMENT)
public class Velocity
extends Module {
    private Setting<Float> horizontal = this.register(Settings.f("Horizontal", 0.0f));
    private Setting<Float> vertical = this.register(Settings.f("Vertical", 0.0f));
    @EventHandler
    private Listener<PacketEvent.Receive> packetEventListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getEra() == KamiEvent.Era.PRE) {
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity velocity = (SPacketEntityVelocity)event.getPacket();
                if (velocity.func_149412_c() == Velocity.mc.field_71439_g.field_145783_c) {
                    if (this.horizontal.getValue().floatValue() == 0.0f && this.vertical.getValue().floatValue() == 0.0f) {
                        event.cancel();
                    }
                    velocity.field_149415_b = (int)((float)velocity.field_149415_b * this.horizontal.getValue().floatValue());
                    velocity.field_149416_c = (int)((float)velocity.field_149416_c * this.vertical.getValue().floatValue());
                    velocity.field_149414_d = (int)((float)velocity.field_149414_d * this.horizontal.getValue().floatValue());
                }
            } else if (event.getPacket() instanceof SPacketExplosion) {
                if (this.horizontal.getValue().floatValue() == 0.0f && this.vertical.getValue().floatValue() == 0.0f) {
                    event.cancel();
                }
                SPacketExplosion velocity = (SPacketExplosion)event.getPacket();
                velocity.field_149152_f *= this.horizontal.getValue().floatValue();
                velocity.field_149153_g *= this.vertical.getValue().floatValue();
                velocity.field_149159_h *= this.horizontal.getValue().floatValue();
            }
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<EntityEvent.EntityCollision> entityCollisionListener = new Listener<EntityEvent.EntityCollision>(event -> {
        if (event.getEntity() == Velocity.mc.field_71439_g) {
            if (this.horizontal.getValue().floatValue() == 0.0f && this.vertical.getValue().floatValue() == 0.0f) {
                event.cancel();
                return;
            }
            event.setX(-event.getX() * (double)this.horizontal.getValue().floatValue());
            event.setY(0.0);
            event.setZ(-event.getZ() * (double)this.horizontal.getValue().floatValue());
        }
    }, new Predicate[0]);

    @Override
    public String getHudInfo() {
        return "H" + this.horizontal.getValue() + "%V" + this.vertical.getValue() + "%";
    }
}

