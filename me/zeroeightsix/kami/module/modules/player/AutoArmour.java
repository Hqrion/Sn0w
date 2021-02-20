//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.InventoryEffectRenderer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoArmour", category=Module.Category.HIDDEN)
public class AutoArmour
extends Module {
    @Override
    public void onUpdate() {
        int armorType;
        if (AutoArmour.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (AutoArmour.mc.currentScreen instanceof GuiContainer && !(AutoArmour.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor = AutoArmour.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            int armorValue;
            ItemStack stack = AutoArmour.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() > 1 || stack == null || !(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor armor = (ItemArmor)stack.getItem();
            int armorType2 = armor.armorType.ordinal() - 2;
            if (armorType2 == 2 && AutoArmour.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA) || (armorValue = armor.damageReduceAmount) <= bestArmorValues[armorType2]) continue;
            bestArmorSlots[armorType2] = slot;
            bestArmorValues[armorType2] = armorValue;
        }
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor;
            int slot = bestArmorSlots[armorType];
            if (slot == -1 || (oldArmor = AutoArmour.mc.player.inventory.armorItemInSlot(armorType)) != null && oldArmor == ItemStack.EMPTY && AutoArmour.mc.player.inventory.getFirstEmptyStack() == -1) continue;
            if (slot < 9) {
                slot += 36;
            }
            AutoArmour.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.player);
            AutoArmour.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.player);
            break;
        }
    }
}

