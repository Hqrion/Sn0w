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

@Module.Info(name="AutoGapple", description="Eat gap when low health", category=Module.Category.PLAYER)
public class OffhandEat
extends Module {
    private Setting healthbar = this.register(Settings.integerBuilder("Health Slider").withMinimum(0).withMaximum(36).withValue(20).build());
    private boolean eating = false;

    private boolean isValid(ItemStack stack, int food) {
        return stack.getItem() instanceof ItemFood && (Integer)this.healthbar.getValue() - food >= ((ItemFood)stack.getItem()).getHealAmount(stack);
    }

    @Override
    public void onUpdate() {
        if (this.eating && !OffhandEat.mc.player.isHandActive()) {
            this.eating = false;
            KeyBinding.setKeyBindState((int)OffhandEat.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            return;
        }
        if (this.eating) {
            return;
        }
        float playerAbsorption = OffhandEat.mc.player.getAbsorptionAmount();
        if (this.isValid(OffhandEat.mc.player.getHeldItemOffhand(), (int)OffhandEat.mc.player.getHealth() + (int)playerAbsorption)) {
            OffhandEat.mc.player.setActiveHand(EnumHand.OFF_HAND);
            this.eating = true;
            KeyBinding.setKeyBindState((int)OffhandEat.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
            mc.rightClickMouse();
            return;
        }
    }
}

