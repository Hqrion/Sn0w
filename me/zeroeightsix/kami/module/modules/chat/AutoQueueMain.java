/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.chat;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="AutoQueueMain", category=Module.Category.HIDDEN, description="Sends AutoQueueMain")
public class AutoQueueMain
extends Module {
    private final Timer timer = new Timer();
    private Setting<Integer> delay = this.register(Settings.integerBuilder("Seconds Delay").withMinimum(0).withValue(9).withMaximum(90).build());
    private Setting<Boolean> info = this.register(Settings.b("Info Messages", false));

    @Override
    public void onUpdate() {
        if (!this.shouldSendMessage((EntityPlayer)AutoQueueMain.mc.field_71439_g)) {
            return;
        }
        if (this.info.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoQueueMain] Sending message: /queue main");
        }
        AutoQueueMain.mc.field_71439_g.func_71165_d("/queue main");
        this.timer.reset();
    }

    private boolean shouldSendMessage(EntityPlayer player) {
        if (player.field_71093_bK != 1) {
            return false;
        }
        if (!this.timer.passed(this.delay.getValue() * 1000)) {
            return false;
        }
        return player.func_180425_c().equals((Object)new Vec3i(0, 240, 0));
    }

    public static final class Timer {
        private long time = -1L;

        Timer() {
        }

        boolean passed(double ms) {
            return (double)(System.currentTimeMillis() - this.time) >= ms;
        }

        public void reset() {
            this.time = System.currentTimeMillis();
        }
    }
}

