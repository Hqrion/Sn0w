/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

@Module.Info(name="OffhandEatOLD", description="Automatically eat when low on health", category=Module.Category.HIDDEN)
public class OffhandEatOLD
extends Module {
    private Setting healthbar = this.register(Settings.integerBuilder("Health Slider").withMinimum(0).withMaximum(36).withValue(20).build());
    private int lastSlot = -1;
    private boolean eating = false;

    private boolean isValid(ItemStack stack, int food) {
        return stack.func_77973_b() instanceof ItemFood && (Integer)this.healthbar.getValue() - food >= ((ItemFood)stack.func_77973_b()).func_150905_g(stack);
    }

    @Override
    public void onUpdate() {
        if (this.eating && !OffhandEatOLD.mc.field_71439_g.func_184587_cr()) {
            if (this.lastSlot != -1) {
                OffhandEatOLD.mc.field_71439_g.field_71071_by.field_70461_c = this.lastSlot;
                this.lastSlot = -1;
            }
            this.eating = false;
            KeyBinding.func_74510_a((int)OffhandEatOLD.mc.field_71474_y.field_74313_G.func_151463_i(), (boolean)false);
            return;
        }
        if (this.eating) {
            return;
        }
        float playerAbsorption = OffhandEatOLD.mc.field_71439_g.func_110139_bj();
        if (this.isValid(OffhandEatOLD.mc.field_71439_g.func_184592_cb(), (int)OffhandEatOLD.mc.field_71439_g.func_110143_aJ() + (int)playerAbsorption)) {
            OffhandEatOLD.mc.field_71439_g.func_184598_c(EnumHand.OFF_HAND);
            this.eating = true;
            KeyBinding.func_74510_a((int)OffhandEatOLD.mc.field_71474_y.field_74313_G.func_151463_i(), (boolean)true);
            mc.func_147121_ag();
            return;
        }
        for (int i = 0; i < 9; ++i) {
            if (!this.isValid(OffhandEatOLD.mc.field_71439_g.field_71071_by.func_70301_a(i), (int)OffhandEatOLD.mc.field_71439_g.func_110143_aJ() + (int)playerAbsorption)) continue;
            this.lastSlot = OffhandEatOLD.mc.field_71439_g.field_71071_by.field_70461_c;
            OffhandEatOLD.mc.field_71439_g.field_71071_by.field_70461_c = i;
            this.eating = true;
            KeyBinding.func_74510_a((int)OffhandEatOLD.mc.field_71474_y.field_74313_G.func_151463_i(), (boolean)true);
            mc.func_147121_ag();
            return;
        }
    }
}

