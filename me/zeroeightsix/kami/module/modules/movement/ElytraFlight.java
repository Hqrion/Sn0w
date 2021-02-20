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
        if (!ElytraFlight.mc.field_71439_g.func_184613_cA()) {
            return;
        }
        switch (this.mode.getValue()) {
            case BOOST: {
                if (ElytraFlight.mc.field_71439_g.func_70090_H()) {
                    mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (ElytraFlight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    ElytraFlight.mc.field_71439_g.field_70181_x += 0.08;
                } else if (ElytraFlight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    ElytraFlight.mc.field_71439_g.field_70181_x -= 0.04;
                }
                if (ElytraFlight.mc.field_71474_y.field_74351_w.func_151470_d()) {
                    float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
                    ElytraFlight.mc.field_71439_g.field_70159_w -= (double)(MathHelper.func_76126_a((float)yaw) * 0.05f);
                    ElytraFlight.mc.field_71439_g.field_70179_y += (double)(MathHelper.func_76134_b((float)yaw) * 0.05f);
                    break;
                }
                if (!ElytraFlight.mc.field_71474_y.field_74368_y.func_151470_d()) break;
                float yaw = (float)Math.toRadians(ElytraFlight.mc.field_71439_g.field_70177_z);
                ElytraFlight.mc.field_71439_g.field_70159_w += (double)(MathHelper.func_76126_a((float)yaw) * 0.05f);
                ElytraFlight.mc.field_71439_g.field_70179_y -= (double)(MathHelper.func_76134_b((float)yaw) * 0.05f);
                break;
            }
            case FLY: {
                ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            }
        }
    }

    @Override
    protected void onDisable() {
        if (ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
            return;
        }
        ElytraFlight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
    }

    private static enum ElytraFlightMode {
        BOOST,
        FLY;

    }
}

