//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.MathHelper;

@Module.Info(name="ElytraFlight", description="Allows infinite elytra flying", category=Module.Category.HIDDEN)
public class ElytraFlight
extends Module {
    private Setting<ElytraFlightMode> mode = this.register(Settings.e("Mode", ElytraFlightMode.BOOST));

    @Override
    public void onUpdate() {
        if (!ElytraFlight.mc.player.isElytraFlying()) {
            return;
        }
        switch (this.mode.getValue()) {
            case BOOST: {
                if (ElytraFlight.mc.player.isInWater()) {
                    mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                    ElytraFlight.mc.player.motionY += 0.08;
                } else if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    ElytraFlight.mc.player.motionY -= 0.04;
                }
                if (ElytraFlight.mc.gameSettings.keyBindForward.isKeyDown()) {
                    float yaw = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
                    ElytraFlight.mc.player.motionX -= (double)(MathHelper.sin((float)yaw) * 0.05f);
                    ElytraFlight.mc.player.motionZ += (double)(MathHelper.cos((float)yaw) * 0.05f);
                    break;
                }
                if (!ElytraFlight.mc.gameSettings.keyBindBack.isKeyDown()) break;
                float yaw = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
                ElytraFlight.mc.player.motionX += (double)(MathHelper.sin((float)yaw) * 0.05f);
                ElytraFlight.mc.player.motionZ -= (double)(MathHelper.cos((float)yaw) * 0.05f);
                break;
            }
            case FLY: {
                ElytraFlight.mc.player.capabilities.isFlying = true;
            }
        }
    }

    @Override
    protected void onDisable() {
        if (ElytraFlight.mc.player.capabilities.isCreativeMode) {
            return;
        }
        ElytraFlight.mc.player.capabilities.isFlying = false;
    }

    private static enum ElytraFlightMode {
        BOOST,
        FLY;

    }
}

