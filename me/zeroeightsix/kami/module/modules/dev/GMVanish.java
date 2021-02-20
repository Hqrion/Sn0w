//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (GMVanish.mc.player == null || GMVanish.mc.player.getRidingEntity() == null) {
            this.disable();
            return;
        }
        this.entity = GMVanish.mc.player.getRidingEntity();
        GMVanish.mc.player.dismountRidingEntity();
        GMVanish.mc.world.removeEntity(this.entity);
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || GMVanish.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (GMVanish.mc.player.getRidingEntity() == null) {
            this.disable();
            return;
        }
        if (this.entity != null) {
            this.entity.posX = GMVanish.mc.player.posX;
            this.entity.posY = GMVanish.mc.player.posY;
            this.entity.posZ = GMVanish.mc.player.posZ;
            try {
                Objects.requireNonNull(mc.getConnection()).sendPacket((Packet)new CPacketVehicleMove(this.entity));
            }
            catch (Exception e) {
                System.out.println("ERROR: Dude we kinda have a problem here:");
                e.printStackTrace();
            }
        }
    }
}

