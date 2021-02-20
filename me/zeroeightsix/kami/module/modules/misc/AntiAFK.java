//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (AntiAFK.mc.playerController.getIsHittingBlock()) {
            return;
        }
        if (AntiAFK.mc.player.ticksExisted % 40 == 0 && this.swing.getValue().booleanValue()) {
            mc.getConnection().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        if (AntiAFK.mc.player.ticksExisted % 15 == 0 && this.turn.getValue().booleanValue()) {
            AntiAFK.mc.player.rotationYaw = this.random.nextInt(360) - 180;
        }
        if (!this.swing.getValue().booleanValue() && !this.turn.getValue().booleanValue() && AntiAFK.mc.player.ticksExisted % 80 == 0) {
            AntiAFK.mc.player.jump();
        }
    }
}

