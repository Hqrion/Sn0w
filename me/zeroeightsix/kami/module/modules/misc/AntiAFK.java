/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.util.EnumHand
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.Random;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;

@Module.Info(name="AntiAFK", category=Module.Category.MISC, description="Moves in order not to get kicked. (May be invisible client-sided)")
public class AntiAFK
extends Module {
    private Setting<Boolean> swing = this.register(Settings.b("Swing", true));
    private Setting<Boolean> turn = this.register(Settings.b("Turn", true));
    private Random random = new Random();

    @Override
    public void onUpdate() {
        if (AntiAFK.mc.field_71442_b.func_181040_m()) {
            return;
        }
        if (AntiAFK.mc.field_71439_g.field_70173_aa % 40 == 0 && this.swing.getValue().booleanValue()) {
            mc.func_147114_u().func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        if (AntiAFK.mc.field_71439_g.field_70173_aa % 15 == 0 && this.turn.getValue().booleanValue()) {
            AntiAFK.mc.field_71439_g.field_70177_z = this.random.nextInt(360) - 180;
        }
        if (!this.swing.getValue().booleanValue() && !this.turn.getValue().booleanValue() && AntiAFK.mc.field_71439_g.field_70173_aa % 80 == 0) {
            AntiAFK.mc.field_71439_g.func_70664_aZ();
        }
    }
}

