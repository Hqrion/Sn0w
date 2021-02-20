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
        if (Flight.mc.field_71439_g == null) {
            return;
        }
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = true;
            }
        }
    }

    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case STATIC: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
                Flight.mc.field_71439_g.field_70159_w = 0.0;
                Flight.mc.field_71439_g.field_70181_x = 0.0;
                Flight.mc.field_71439_g.field_70179_y = 0.0;
                Flight.mc.field_71439_g.field_70747_aH = this.speed.getValue().floatValue();
                if (Flight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    Flight.mc.field_71439_g.field_70181_x += (double)this.speed.getValue().floatValue();
                }
                if (!Flight.mc.field_71474_y.field_74311_E.func_151470_d()) break;
                Flight.mc.field_71439_g.field_70181_x -= (double)this.speed.getValue().floatValue();
                break;
            }
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.func_75092_a(this.speed.getValue().floatValue() / 100.0f);
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = true;
                break;
            }
            case PACKET: {
                int angle;
                boolean forward = Flight.mc.field_71474_y.field_74351_w.func_151470_d();
                boolean left = Flight.mc.field_71474_y.field_74370_x.func_151470_d();
                boolean right = Flight.mc.field_71474_y.field_74366_z.func_151470_d();
                boolean back = Flight.mc.field_71474_y.field_74368_y.func_151470_d();
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
                    float yaw = Flight.mc.field_71439_g.field_70177_z + (float)angle;
                    Flight.mc.field_71439_g.field_70159_w = EntityUtil.getRelativeX(yaw) * (double)0.2f;
                    Flight.mc.field_71439_g.field_70179_y = EntityUtil.getRelativeZ(yaw) * (double)0.2f;
                }
                Flight.mc.field_71439_g.field_70181_x = 0.0;
                Flight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Flight.mc.field_71439_g.field_70165_t + Flight.mc.field_71439_g.field_70159_w, Flight.mc.field_71439_g.field_70163_u + (Minecraft.func_71410_x().field_71474_y.field_74314_A.func_151470_d() ? 0.0622 : 0.0) - (Minecraft.func_71410_x().field_71474_y.field_74311_E.func_151470_d() ? 0.0622 : 0.0), Flight.mc.field_71439_g.field_70161_v + Flight.mc.field_71439_g.field_70179_y, Flight.mc.field_71439_g.field_70177_z, Flight.mc.field_71439_g.field_70125_A, false));
                Flight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Flight.mc.field_71439_g.field_70165_t + Flight.mc.field_71439_g.field_70159_w, Flight.mc.field_71439_g.field_70163_u - 42069.0, Flight.mc.field_71439_g.field_70161_v + Flight.mc.field_71439_g.field_70179_y, Flight.mc.field_71439_g.field_70177_z, Flight.mc.field_71439_g.field_70125_A, true));
            }
        }
    }

    @Override
    protected void onDisable() {
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
                Flight.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05f);
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = false;
            }
        }
    }

    public double[] moveLooking() {
        return new double[]{Flight.mc.field_71439_g.field_70177_z * 360.0f / 360.0f * 180.0f / 180.0f, 0.0};
    }

    public static enum FlightMode {
        VANILLA,
        STATIC,
        PACKET;

    }
}

