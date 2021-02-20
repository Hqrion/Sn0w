//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        return stack.getItem() instanceof ItemFood && (Integer)this.healthbar.getValue() - food >= ((ItemFood)stack.getItem()).getHealAmount(stack);
    }

    @Override
    public void onUpdate() {
        if (this.eating && !OffhandEatOLD.mc.player.isHandActive()) {
            if (this.lastSlot != -1) {
                OffhandEatOLD.mc.player.inventory.currentItem = this.lastSlot;
                this.lastSlot = -1;
            }
            this.eating = false;
            KeyBinding.setKeyBindState((int)OffhandEatOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            return;
        }
        if (this.eating) {
            return;
        }
        float playerAbsorption = OffhandEatOLD.mc.player.getAbsorptionAmount();
        if (this.isValid(OffhandEatOLD.mc.player.getHeldItemOffhand(), (int)OffhandEatOLD.mc.player.getHealth() + (int)playerAbsorption)) {
            OffhandEatOLD.mc.player.setActiveHand(EnumHand.OFF_HAND);
            this.eating = true;
            KeyBinding.setKeyBindState((int)OffhandEatOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
            mc.rightClickMouse();
            return;
        }
        for (int i = 0; i < 9; ++i) {
            if (!this.isValid(OffhandEatOLD.mc.player.inventory.getStackInSlot(i), (int)OffhandEatOLD.mc.player.getHealth() + (int)playerAbsorption)) continue;
            this.lastSlot = OffhandEatOLD.mc.player.inventory.currentItem;
            OffhandEatOLD.mc.player.inventory.currentItem = i;
            this.eating = true;
            KeyBinding.setKeyBindState((int)OffhandEatOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
            mc.rightClickMouse();
            return;
        }
    }
}

