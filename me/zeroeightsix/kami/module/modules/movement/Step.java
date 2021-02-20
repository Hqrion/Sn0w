/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.text.DecimalFormat;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.MotionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(name="Step", category=Module.Category.MOVEMENT, description="Step")
public class Step
extends Module {
    private Setting<Boolean> timer = this.register(Settings.b("Timer", false));
    private Setting<Float> height = this.register(Settings.f("Height", 1.0f));
    private Setting<Boolean> antiStuck = this.register(Settings.b("AntiStuck"));
    private Setting<StepModes> StepMode = this.register(Settings.e("Step", StepModes.VANILLA));
    private Setting<Boolean> Reverse = this.register(Settings.b("Reverse", false));
    private Setting<Boolean> ChatMsg = this.register(Settings.b("ChatMsg"));
    private int ticks = 0;

    @Override
    public void onUpdate() {
        if (Step.mc.field_71441_e == null || Step.mc.field_71439_g == null || Step.mc.field_71439_g.func_70090_H() || Step.mc.field_71439_g.func_180799_ab() || Step.mc.field_71439_g.func_70617_f_() || Step.mc.field_71474_y.field_74314_A.func_151470_d()) {
            return;
        }
        if (this.StepMode.getValue().equals((Object)StepModes.NORMAL)) {
            if (this.timer.getValue().booleanValue()) {
                if (this.ticks == 0) {
                    EntityUtil.resetTimer();
                } else {
                    --this.ticks;
                }
            }
            if (Step.mc.field_71439_g != null && Step.mc.field_71439_g.field_70122_E && !Step.mc.field_71439_g.func_70090_H() && !Step.mc.field_71439_g.func_70617_f_() && this.Reverse.getValue().booleanValue()) {
                for (double y = 0.0; y < (double)this.height.getValue().floatValue() + 0.5; y += 0.01) {
                    if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -y, 0.0)).isEmpty()) continue;
                    Step.mc.field_71439_g.field_70181_x = -10.0;
                    break;
                }
            }
            double[] dir = MotionUtils.forward(0.1);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.4, dir[1])).isEmpty()) {
                twofive = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.9, dir[1])).isEmpty()) {
                two = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.4, dir[1])).isEmpty()) {
                onefive = true;
            }
            if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], 0.6, dir[1])).isEmpty()) {
                one = true;
            }
            if (Step.mc.field_71439_g.field_70123_F && (Step.mc.field_71439_g.field_191988_bg != 0.0f || Step.mc.field_71439_g.field_70702_br != 0.0f) && Step.mc.field_71439_g.field_70122_E) {
                int i;
                if (one && (double)this.height.getValue().floatValue() >= 1.0) {
                    double[] oneOffset = new double[]{0.42, 0.753};
                    for (i = 0; i < oneOffset.length; ++i) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + oneOffset[i], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.6f);
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.0, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 1;
                }
                if (onefive && (double)this.height.getValue().floatValue() >= 1.5) {
                    double[] oneFiveOffset = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
                    for (i = 0; i < oneFiveOffset.length; ++i) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + oneFiveOffset[i], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.35f);
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 1.5, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 1;
                }
                if (two && (double)this.height.getValue().floatValue() >= 2.0) {
                    double[] twoOffset = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
                    for (i = 0; i < twoOffset.length; ++i) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + twoOffset[i], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.25f);
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 2.0, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 2;
                }
                if (twofive && (double)this.height.getValue().floatValue() >= 2.5) {
                    double[] twoFiveOffset = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + twoFiveOffset[i], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.15f);
                    }
                    Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + 2.5, Step.mc.field_71439_g.field_70161_v);
                    this.ticks = 2;
                }
            }
        }
        if (this.StepMode.getValue().equals((Object)StepModes.VANILLA)) {
            DecimalFormat df = new DecimalFormat("#");
            Step.mc.field_71439_g.field_70138_W = Float.parseFloat(df.format(this.height.getValue()));
        }
    }

    @Override
    public String getHudInfo() {
        String t = "";
        if (this.StepMode.getValue().equals((Object)StepModes.NORMAL)) {
            t = "Normal";
        }
        if (this.StepMode.getValue().equals((Object)StepModes.VANILLA)) {
            t = "Vanilla";
        }
        return t;
    }

    @Override
    public void onEnable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
    }

    @Override
    public void onDisable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
        Step.mc.field_71439_g.field_70138_W = 0.5f;
    }

    private static enum StepModes {
        VANILLA,
        NORMAL;

    }
}

