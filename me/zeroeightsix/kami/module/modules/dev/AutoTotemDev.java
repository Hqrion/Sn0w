/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.dev;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoTotemDev", category=Module.Category.HIDDEN, description="Auto Totem")
public class AutoTotemDev
extends Module {
    private int numOfTotems;
    private int preferredTotemSlot;
    private Setting<Boolean> soft = this.register(Settings.b("Soft", false));
    private Setting<Boolean> pauseInContainers = this.register(Settings.b("PauseInContainers", true));
    private Setting<Boolean> pauseInInventory = this.register(Settings.b("PauseInInventory", true));

    @Override
    public void onUpdate() {
        if (AutoTotemDev.mc.field_71439_g == null) {
            return;
        }
        if (!this.findTotems()) {
            return;
        }
        if (this.pauseInContainers.getValue().booleanValue() && AutoTotemDev.mc.field_71462_r instanceof GuiContainer && !(AutoTotemDev.mc.field_71462_r instanceof GuiInventory)) {
            return;
        }
        if (this.pauseInInventory.getValue().booleanValue() && AutoTotemDev.mc.field_71462_r instanceof GuiInventory && AutoTotemDev.mc.field_71462_r instanceof GuiInventory) {
            return;
        }
        if (this.soft.getValue().booleanValue()) {
            if (AutoTotemDev.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_190931_a)) {
                AutoTotemDev.mc.field_71442_b.func_187098_a(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.field_71439_g);
                AutoTotemDev.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.field_71439_g);
                AutoTotemDev.mc.field_71442_b.func_78765_e();
            }
        } else if (!AutoTotemDev.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_190929_cY)) {
            boolean offhandEmptyPreSwitch = false;
            if (AutoTotemDev.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_190931_a)) {
                offhandEmptyPreSwitch = true;
            }
            AutoTotemDev.mc.field_71442_b.func_187098_a(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.field_71439_g);
            AutoTotemDev.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.field_71439_g);
            if (!offhandEmptyPreSwitch) {
                AutoTotemDev.mc.field_71442_b.func_187098_a(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.field_71439_g);
            }
            AutoTotemDev.mc.field_71442_b.func_78765_e();
        }
    }

    private boolean findTotems() {
        this.numOfTotems = 0;
        AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();
        preferredTotemSlotStackSize.set(Integer.MIN_VALUE);
        AutoTotemDev.getInventoryAndHotbarSlots().forEach((slotKey, slotValue) -> {
            int numOfTotemsInStack = 0;
            if (slotValue.func_77973_b().equals(Items.field_190929_cY)) {
                numOfTotemsInStack = slotValue.func_190916_E();
                if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
                    preferredTotemSlotStackSize.set(numOfTotemsInStack);
                    this.preferredTotemSlot = slotKey;
                }
            }
            this.numOfTotems += numOfTotemsInStack;
        });
        if (AutoTotemDev.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_190929_cY)) {
            this.numOfTotems += AutoTotemDev.mc.field_71439_g.func_184592_cb().func_190916_E();
        }
        return this.numOfTotems != 0;
    }

    private static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        return AutoTotemDev.getInventorySlots(9, 44);
    }

    private static Map<Integer, ItemStack> getInventorySlots(int current, int last) {
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoTotemDev.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

    public void disableSoft() {
        this.soft.setValue(false);
    }
}

