/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 */
package me.zeroeightsix.kami.module.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;

@Module.Info(name="Reverse Step", category=Module.Category.MOVEMENT, description="Step")
public class ReverseStep
extends Module {
    private Setting<Float> height = this.register(Settings.f("Height", 2.5f));
    private Setting<Boolean> ChatMsg = this.register(Settings.b("ChatMsg"));
    private Setting<Boolean> CancelOnSpeed = this.register(Settings.b("Cancel On Speed"));
    private int ticks = 0;

    @Override
    public void onUpdate() {
        if (ReverseStep.mc.field_71441_e == null || ReverseStep.mc.field_71439_g == null) {
            return;
        }
        if (ReverseStep.mc.field_71439_g.func_70090_H() || ReverseStep.mc.field_71439_g.func_180799_ab() || ReverseStep.mc.field_71439_g.func_70617_f_() || ReverseStep.mc.field_71474_y.field_74314_A.func_151470_d()) {
            return;
        }
        if (this.CancelOnSpeed.getValue().booleanValue() && ModuleManager.isModuleEnabled("Speed")) {
            return;
        }
        if (ReverseStep.mc.field_71439_g != null && ReverseStep.mc.field_71439_g.field_70122_E && !ReverseStep.mc.field_71439_g.func_70090_H() && !ReverseStep.mc.field_71439_g.func_70617_f_()) {
            for (double y = 0.0; y < (double)this.height.getValue().floatValue() + 0.5; y += 0.01) {
                if (ReverseStep.mc.field_71441_e.func_184144_a((Entity)ReverseStep.mc.field_71439_g, ReverseStep.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -y, 0.0)).isEmpty()) continue;
                ReverseStep.mc.field_71439_g.field_70181_x = -10.0;
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.sendChatMessage("Reverse Step " + ChatFormatting.AQUA + "Enabled" + ChatFormatting.WHITE + "!");
        }
    }

    @Override
    public void onDisable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.sendChatMessage("Reverse Step " + ChatFormatting.BLUE + "Disabled" + ChatFormatting.WHITE + "!");
        }
    }
}

