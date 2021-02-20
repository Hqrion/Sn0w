//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 */
package me.zeroeightsix.kami.module.modules.movement;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(category=Module.Category.HIDDEN, description="Makes the player fly", name="Flight")
public class Flight
extends Module {
    private Setting<Float> speed = this.register(Settings.f("Speed", 10.0f));
    private Setting<FlightMode> mode = this.register(Settings.e("Mode", FlightMode.VANILLA));

    @Override
    protected void onEnable() {
        if (Flight.mc.player == null) {
            return;
        }
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.player.capabilities.isFlying = true;
                if (Flight.mc.player.capabilities.isCreativeMode) {
                    return;
                }
                Flight.mc.player.capabilities.allowFlying = true;
            }
        }
    }

    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case STATIC: {
                Flight.mc.player.capabilities.isFlying = false;
                Flight.mc.player.motionX = 0.0;
                Flight.mc.player.motionY = 0.0;
                Flight.mc.player.motionZ = 0.0;
                Flight.mc.player.jumpMovementFactor = this.speed.getValue().floatValue();
                if (Flight.mc.gameSettings.keyBindJump.isKeyDown()) {
                    Flight.mc.player.motionY += (double)this.speed.getValue().floatValue();
                }
                if (!Flight.mc.gameSettings.keyBindSneak.isKeyDown()) break;
                Flight.mc.player.motionY -= (double)this.speed.getValue().floatValue();
                break;
            }
            case VANILLA: {
                Flight.mc.player.capabilities.setFlySpeed(this.speed.getValue().floatValue() / 100.0f);
                Flight.mc.player.capabilities.isFlying = true;
                if (Flight.mc.player.capabilities.isCreativeMode) {
                    return;
                }
                Flight.mc.player.capabilities.allowFlying = true;
                break;
            }
            case PACKET: {
                int angle;
                boolean forward = Flight.mc.gameSettings.keyBindForward.isKeyDown();
                boolean left = Flight.mc.gameSettings.keyBindLeft.isKeyDown();
                boolean right = Flight.mc.gameSettings.keyBindRight.isKeyDown();
                boolean back = Flight.mc.gameSettings.keyBindBack.isKeyDown();
                if (left && right) {
                    angle = forward ? 0 : (back ? 180 : -1);
                } else if (forward && back) {
                    angle = left ? -90 : (right ? 90 : -1);
                } else {
                    int n = left ? -90 : (angle = right ? 90 : 0);
                    if (forward) {
                        angle /= 2;
                    } else if (back) {
                        angle = 180 - angle / 2;
                    }
                }
                if (angle != -1 && (forward || left || right || back)) {
                    float yaw = Flight.mc.player.rotationYaw + (float)angle;
                    Flight.mc.player.motionX = EntityUtil.getRelativeX(yaw) * (double)0.2f;
                    Flight.mc.player.motionZ = EntityUtil.getRelativeZ(yaw) * (double)0.2f;
                }
                Flight.mc.player.motionY = 0.0;
                Flight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Flight.mc.player.posX + Flight.mc.player.motionX, Flight.mc.player.posY + (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() ? 0.0622 : 0.0) - (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown() ? 0.0622 : 0.0), Flight.mc.player.posZ + Flight.mc.player.motionZ, Flight.mc.player.rotationYaw, Flight.mc.player.rotationPitch, false));
                Flight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Flight.mc.player.posX + Flight.mc.player.motionX, Flight.mc.player.posY - 42069.0, Flight.mc.player.posZ + Flight.mc.player.motionZ, Flight.mc.player.rotationYaw, Flight.mc.player.rotationPitch, true));
            }
        }
    }

    @Override
    protected void onDisable() {
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.player.capabilities.isFlying = false;
                Flight.mc.player.capabilities.setFlySpeed(0.05f);
                if (Flight.mc.player.capabilities.isCreativeMode) {
                    return;
                }
                Flight.mc.player.capabilities.allowFlying = false;
            }
        }
    }

    public double[] moveLooking() {
        return new double[]{Flight.mc.player.rotationYaw * 360.0f / 360.0f * 180.0f / 180.0f, 0.0};
    }

    public static enum FlightMode {
        VANILLA,
        STATIC,
        PACKET;

    }
}

