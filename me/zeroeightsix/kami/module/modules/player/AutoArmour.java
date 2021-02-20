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
        if (AutoArmour.mc.field_71439_g.field_70173_aa % 2 == 0) {
            return;
        }
        if (AutoArmour.mc.field_71462_r instanceof GuiContainer && !(AutoArmour.mc.field_71462_r instanceof InventoryEffectRenderer)) {
            return;
        }
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor = AutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType);
            if (oldArmor != null && oldArmor.func_77973_b() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.func_77973_b()).field_77879_b;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            int armorValue;
            ItemStack stack = AutoArmour.mc.field_71439_g.field_71071_by.func_70301_a(slot);
            if (stack.func_190916_E() > 1 || stack == null || !(stack.func_77973_b() instanceof ItemArmor)) continue;
            ItemArmor armor = (ItemArmor)stack.func_77973_b();
            int armorType2 = armor.field_77881_a.ordinal() - 2;
            if (armorType2 == 2 && AutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType2).func_77973_b().equals(Items.field_185160_cR) || (armorValue = armor.field_77879_b) <= bestArmorValues[armorType2]) continue;
            bestArmorSlots[armorType2] = slot;
            bestArmorValues[armorType2] = armorValue;
        }
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor;
            int slot = bestArmorSlots[armorType];
            if (slot == -1 || (oldArmor = AutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType)) != null && oldArmor == ItemStack.field_190927_a && AutoArmour.mc.field_71439_g.field_71071_by.func_70447_i() == -1) continue;
            if (slot < 9) {
                slot += 36;
            }
            AutoArmour.mc.field_71442_b.func_187098_a(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.field_71439_g);
            AutoArmour.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmour.mc.field_71439_g);
            break;
        }
    }
}

