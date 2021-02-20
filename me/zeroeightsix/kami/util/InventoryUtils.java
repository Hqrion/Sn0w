//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.util;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    private Minecraft mc = Minecraft.getMinecraft();
    public boolean inProgress = false;

    public ArrayList<Integer> getSlots(int min, int max, int itemID) {
        ArrayList<Integer> slots = new ArrayList<Integer>();
        for (int i = min; i <= max; ++i) {
            if (Item.getIdFromItem((Item)this.mc.player.inventory.getStackInSlot(i).getItem()) != itemID) continue;
            slots.add(i);
        }
        if (!slots.isEmpty()) {
            return slots;
        }
        return null;
    }

    public ArrayList<Integer> getSlotsHotbar(int itemId) {
        return this.getSlots(0, 8, itemId);
    }

    public ArrayList<Integer> getSlotsNoHotbar(int itemId) {
        return this.getSlots(9, 35, itemId);
    }

    public ArrayList<Integer> getSlotsFullInv(int min, int max, int itemId) {
        ArrayList<Integer> slots = new ArrayList<Integer>();
        for (int i = min; i < max; ++i) {
            if (Item.getIdFromItem((Item)((ItemStack)this.mc.player.inventoryContainer.getInventory().get(i)).getItem()) != itemId) continue;
            slots.add(i);
        }
        if (slots.isEmpty()) {
            return slots;
        }
        return null;
    }

    public ArrayList<Integer> getSlotsFullInvHotbar(int itemId) {
        return this.getSlots(36, 44, itemId);
    }

    public ArrayList<Integer> getSlotsFullInvNoHotbar(int itemId) {
        return this.getSlots(9, 35, itemId);
    }

    public int countItem(int min, int max, int itemId) {
        ArrayList<Integer> itemList = this.getSlots(min, max, itemId);
        int currentCount = 0;
        if (itemList != null) {
            for (int i : itemList) {
                currentCount += this.mc.player.inventory.getStackInSlot(i).getCount();
            }
        }
        return currentCount;
    }

    public static void swapSlot(int slot) {
        Minecraft.getMinecraft().player.inventory.currentItem = slot;
    }

    public void swapSlotToItem(int itemID) {
        if (this.getSlotsHotbar(itemID) != null) {
            InventoryUtils.swapSlot(this.getSlotsHotbar(itemID).get(0));
        }
    }

    private void inventoryClick(int slot, ClickType type2) {
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, slot, 0, type2, (EntityPlayer)this.mc.player);
    }

    public void moveToHotbar(int itemID, int exceptionID, long delayMillis) {
        ArrayList<Integer> gsfinh = this.getSlotsFullInvNoHotbar(itemID);
        if (gsfinh == null) {
            return;
        }
        int slot1 = gsfinh.get(0);
        int slot2 = 36;
        for (int i = 36; i < 44; ++i) {
            ItemStack currentItemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (currentItemStack.isEmpty()) {
                slot2 = i;
                break;
            }
            if (Item.getIdFromItem((Item)currentItemStack.getItem()) == exceptionID) continue;
            slot2 = i;
            break;
        }
        this.moveToSlot(slot1, slot2, delayMillis);
    }

    public void moveToSlot(final int slotFrom, final int slotTo, final long delayMillis) {
        if (this.inProgress) {
            return;
        }
        Thread thread = new Thread(){

            @Override
            public void run() {
                InventoryUtils.this.inProgress = true;
                GuiScreen prevScreen = ((InventoryUtils)InventoryUtils.this).mc.currentScreen;
                InventoryUtils.this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)((InventoryUtils)InventoryUtils.this).mc.player));
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotFrom, ClickType.PICKUP);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotTo, ClickType.PICKUP);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotFrom, ClickType.PICKUP);
                InventoryUtils.this.mc.displayGuiScreen(prevScreen);
                InventoryUtils.this.inProgress = false;
            }
        };
        thread.start();
    }

    public void moveAllToSlot(int slotFrom, final int slotTo, final long delayMillis) {
        if (this.inProgress) {
            return;
        }
        Thread thread = new Thread(){

            @Override
            public void run() {
                InventoryUtils.this.inProgress = true;
                GuiScreen prevScreen = ((InventoryUtils)InventoryUtils.this).mc.currentScreen;
                InventoryUtils.this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)((InventoryUtils)InventoryUtils.this).mc.player));
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotTo, ClickType.PICKUP_ALL);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotTo, ClickType.PICKUP);
                InventoryUtils.this.mc.displayGuiScreen(prevScreen);
                InventoryUtils.this.inProgress = false;
            }
        };
        thread.start();
    }

    public void quickMoveSlot(final int slotFrom, final long delayMillis) {
        if (this.inProgress) {
            return;
        }
        Thread thread = new Thread(){

            @Override
            public void run() {
                InventoryUtils.this.inProgress = true;
                InventoryUtils.this.inventoryClick(slotFrom, ClickType.QUICK_MOVE);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inProgress = false;
            }
        };
        thread.start();
    }

    public void quickTotem(final int slotFrom, final long delayMillis) {
        if (this.inProgress) {
            return;
        }
        Thread thread = new Thread(){

            @Override
            public void run() {
                InventoryUtils.this.inProgress = true;
                ((InventoryUtils)InventoryUtils.this).mc.playerController.windowClick(((InventoryUtils)InventoryUtils.this).mc.player.inventoryContainer.windowId, 36, 1, ClickType.THROW, (EntityPlayer)((InventoryUtils)InventoryUtils.this).mc.player);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inventoryClick(slotFrom, ClickType.QUICK_MOVE);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inProgress = false;
            }
        };
        thread.start();
    }

    public void throwAllInSlot(final int slot, final long delayMillis) {
        if (this.inProgress) {
            return;
        }
        Thread thread = new Thread(){

            @Override
            public void run() {
                InventoryUtils.this.inProgress = true;
                ((InventoryUtils)InventoryUtils.this).mc.playerController.windowClick(((InventoryUtils)InventoryUtils.this).mc.player.inventoryContainer.windowId, slot, 1, ClickType.THROW, (EntityPlayer)((InventoryUtils)InventoryUtils.this).mc.player);
                try {
                    Thread.sleep(delayMillis);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                InventoryUtils.this.inProgress = false;
            }
        };
        thread.start();
    }
}

