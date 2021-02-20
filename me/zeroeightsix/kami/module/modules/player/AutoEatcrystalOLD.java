//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

@Module.Info(name="AutoEatcrystalOLD", description="Automatically eat when hungry", category=Module.Category.HIDDEN)
public class AutoEatcrystalOLD
extends Module {
    private Setting healthbar = this.register(Settings.integerBuilder("Health Slider").withMinimum(0).withMaximum(36).withValue(20).build());
    private Setting<Boolean> switch1 = this.register(Settings.b("Switch", false));
    private int lastSlot = -1;
    private boolean eating = false;

    private boolean isValid(ItemStack stack, int food) {
        return stack.getItem() instanceof ItemFood && (Integer)this.healthbar.getValue() - food >= ((ItemFood)stack.getItem()).getHealAmount(stack);
    }

    @Override
    public void onUpdate() {
        if (this.eating && !AutoEatcrystalOLD.mc.player.isHandActive()) {
            if (this.lastSlot != -1) {
                AutoEatcrystalOLD.mc.player.inventory.currentItem = this.lastSlot;
                this.lastSlot = -1;
            }
            this.eating = false;
            KeyBinding.setKeyBindState((int)AutoEatcrystalOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            return;
        }
        if (this.eating) {
            return;
        }
        float playerAbsorption = AutoEatcrystalOLD.mc.player.getAbsorptionAmount();
        if (this.isValid(AutoEatcrystalOLD.mc.player.getHeldItemOffhand(), (int)AutoEatcrystalOLD.mc.player.getHealth() + (int)playerAbsorption)) {
            AutoEatcrystalOLD.mc.player.setActiveHand(EnumHand.OFF_HAND);
            this.eating = true;
            KeyBinding.setKeyBindState((int)AutoEatcrystalOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            mc.rightClickMouse();
        } else {
            for (int i = 0; i < 9; ++i) {
                if (!this.isValid(AutoEatcrystalOLD.mc.player.inventory.getStackInSlot(i), (int)AutoEatcrystalOLD.mc.player.getHealth() + (int)playerAbsorption)) continue;
                if (this.switch1.getValue().booleanValue()) {
                    this.lastSlot = AutoEatcrystalOLD.mc.player.inventory.currentItem;
                    AutoEatcrystalOLD.mc.player.inventory.currentItem = i;
                }
                if (AutoEatcrystalOLD.mc.player.getHeldItemMainhand().getItem() != Items.GOLDEN_APPLE) continue;
                this.eating = true;
                KeyBinding.setKeyBindState((int)AutoEatcrystalOLD.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
                mc.rightClickMouse();
                return;
            }
        }
    }
}

