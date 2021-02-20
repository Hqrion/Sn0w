//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.HashMap;
import java.util.Map;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoReplenish", category=Module.Category.PLAYER, description="Refills your Hotbar")
public class AutoReplenish
extends Module {
    private Setting<Integer> threshold = this.register(Settings.integerBuilder("Threshold").withMinimum(1).withValue(1).withMaximum(63).build());
    private Setting<Integer> tickDelay = this.register(Settings.integerBuilder("TickDelay").withMinimum(1).withValue(1).withMaximum(10).build());
    private int delayStep = 0;

    private static Map<Integer, ItemStack> getInventory() {
        return AutoReplenish.getInventorySlots(9, 35);
    }

    private static Map<Integer, ItemStack> getHotbar() {
        return AutoReplenish.getInventorySlots(36, 44);
    }

    private static Map<Integer, ItemStack> getInventorySlots(int current, int last) {
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)AutoReplenish.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

    @Override
    public void onUpdate() {
        if (AutoReplenish.mc.player == null) {
            return;
        }
        if (AutoReplenish.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        int inventorySlot = slots.getKey();
        int hotbarSlot = slots.getValue();
        AutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
        AutoReplenish.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
        AutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)AutoReplenish.mc.player);
    }

    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (Map.Entry<Integer, ItemStack> hotbarSlot : AutoReplenish.getHotbar().entrySet()) {
            int inventorySlot;
            ItemStack stack = hotbarSlot.getValue();
            if (stack.isEmpty || stack.getItem() == Items.AIR || !stack.isStackable() || stack.stackSize >= stack.getMaxStackSize() || stack.stackSize > this.threshold.getValue() || (inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) continue;
            returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
        }
        return returnPair;
    }

    private int findCompatibleInventorySlot(ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry<Integer, ItemStack> entry : AutoReplenish.getInventory().entrySet()) {
            int currentStackSize;
            ItemStack inventoryStack = entry.getValue();
            if (inventoryStack.isEmpty || inventoryStack.getItem() == Items.AIR || !this.isCompatibleStacks(hotbarStack, inventoryStack) || smallestStackSize <= (currentStackSize = ((ItemStack)AutoReplenish.mc.player.inventoryContainer.getInventory().get((int)entry.getKey().intValue())).stackSize)) continue;
            smallestStackSize = currentStackSize;
            inventorySlot = entry.getKey();
        }
        return inventorySlot;
    }

    private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        if (!stack1.getDisplayName().equals(stack2.getDisplayName())) {
            return false;
        }
        return stack1.getItemDamage() == stack2.getItemDamage();
    }
}

