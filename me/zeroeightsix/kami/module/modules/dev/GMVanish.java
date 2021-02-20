/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketVehicleMove
 */
package me.zeroeightsix.kami.module.modules.dev;

import java.util.Objects;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;

@Module.Info(name="GMVanish", category=Module.Category.HIDDEN, description="Godmode Vanish")
public class GMVanish
extends Module {
    private Entity entity;

    @Override
    public void onEnable() {
        if (GMVanish.mc.field_71439_g == null || GMVanish.mc.field_71439_g.func_184187_bx() == null) {
            this.disable();
            return;
        }
        this.entity = GMVanish.mc.field_71439_g.func_184187_bx();
        GMVanish.mc.field_71439_g.func_184210_p();
        GMVanish.mc.field_71441_e.func_72900_e(this.entity);
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || GMVanish.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (GMVanish.mc.field_71439_g.func_184187_bx() == null) {
            this.disable();
            return;
        }
        if (this.entity != null) {
            this.entity.field_70165_t = GMVanish.mc.field_71439_g.field_70165_t;
            this.entity.field_70163_u = GMVanish.mc.field_71439_g.field_70163_u;
            this.entity.field_70161_v = GMVanish.mc.field_71439_g.field_70161_v;
            try {
                Objects.requireNonNull(mc.func_147114_u()).func_147297_a((Packet)new CPacketVehicleMove(this.entity));
            }
            catch (Exception e) {
                System.out.println("ERROR: Dude we kinda have a problem here:");
                e.printStackTrace();
            }
        }
    }
}

