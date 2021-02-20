/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.init.Items;

@Module.Info(name="FastExp", category=Module.Category.PLAYER, description="Auto Switch to XP and throw fast")
public class FastExp
extends Module {
    private Setting<Boolean> autoThrow = this.register(Settings.b("Auto Throw", false));
    private Setting<Boolean> autoSwitch = this.register(Settings.b("Auto Switch", false));
    private Setting<Boolean> autoDisable = this.register(Settings.booleanBuilder("Auto Disable").withValue(false).withVisibility(o -> this.autoSwitch.getValue()).build());
    private int initHotbarSlot = -1;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (FastExp.mc.field_71439_g != null && FastExp.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
            FastExp.mc.field_71467_ac = 0;
        }
        if (FastExp.mc.field_71439_g != null && FastExp.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151062_by) {
            FastExp.mc.field_71467_ac = 0;
        }
    }, new Predicate[0]);

    @Override
    protected void onEnable() {
        if (FastExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue().booleanValue()) {
            this.initHotbarSlot = FastExp.mc.field_71439_g.field_71071_by.field_70461_c;
        }
    }

    @Override
    protected void onDisable() {
        if (FastExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue().booleanValue() && this.initHotbarSlot != -1 && this.initHotbarSlot != FastExp.mc.field_71439_g.field_71071_by.field_70461_c) {
            FastExp.mc.field_71439_g.field_71071_by.field_70461_c = this.initHotbarSlot;
        }
    }

    @Override
    public void onUpdate() {
        if (FastExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue().booleanValue() && FastExp.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151062_by) {
            int xpSlot = this.findXpPots();
            if (xpSlot == -1) {
                if (this.autoDisable.getValue().booleanValue()) {
                    this.disable();
                }
                return;
            }
            FastExp.mc.field_71439_g.field_71071_by.field_70461_c = xpSlot;
        }
        if (this.autoThrow.getValue().booleanValue() && FastExp.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
            mc.func_147121_ag();
        }
    }

    private int findXpPots() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (FastExp.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_151062_by) continue;
            slot = i;
            break;
        }
        return slot;
    }
}

