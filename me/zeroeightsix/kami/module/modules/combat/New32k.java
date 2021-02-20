//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockShulkerBox
 *  net.minecraft.client.gui.GuiHopper
 *  net.minecraft.client.gui.inventory.GuiDispenser
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Enchantments
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAir
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="Auto32k AutoPlacement", category=Module.Category.COMBAT, description="Do not use with any AntiGhostBlock Mod!")
public class New32k
extends Module {
    private BlockPos pos;
    private int hopper;
    private int redstone;
    private int shulker;
    private int[] rot;
    private boolean active;
    private boolean openedDispenser;
    private int dispenserTicks;
    private int ticksPassed;
    private int timer;

    @Override
    public void onEnable() {
        int x;
        this.ticksPassed = 0;
        this.hopper = -1;
        int dispenser = -1;
        this.redstone = -1;
        this.shulker = -1;
        int block = -1;
        this.active = false;
        this.openedDispenser = false;
        this.dispenserTicks = 0;
        this.timer = 0;
        for (x = 0; x <= 8; ++x) {
            Item item = New32k.mc.player.inventory.getStackInSlot(x).getItem();
            if (item == Item.getItemFromBlock((Block)Blocks.HOPPER)) {
                this.hopper = x;
                continue;
            }
            if (item == Item.getItemFromBlock((Block)Blocks.DISPENSER)) {
                dispenser = x;
                continue;
            }
            if (item == Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK)) {
                this.redstone = x;
                continue;
            }
            if (item instanceof ItemShulkerBox) {
                this.shulker = x;
                continue;
            }
            if (!(item instanceof ItemBlock)) continue;
            block = x;
        }
        if (this.hopper != -1 && dispenser != -1 && this.redstone != -1 && this.shulker != -1 && block != -1) {
            for (x = -2; x <= 2; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -2; z <= 2; ++z) {
                        int[] nArray;
                        if (Math.abs(x) > Math.abs(z)) {
                            if (x > 0) {
                                int[] nArray2 = new int[2];
                                nArray2[0] = -1;
                                nArray = nArray2;
                                nArray2[1] = 0;
                            } else {
                                int[] nArray3 = new int[2];
                                nArray3[0] = 1;
                                nArray = nArray3;
                                nArray3[1] = 0;
                            }
                        } else if (z > 0) {
                            int[] nArray4 = new int[2];
                            nArray4[0] = 0;
                            nArray = nArray4;
                            nArray4[1] = -1;
                        } else {
                            int[] nArray5 = new int[2];
                            nArray5[0] = 0;
                            nArray = nArray5;
                            nArray5[1] = 1;
                        }
                        this.rot = nArray;
                        this.pos = New32k.mc.player.getPosition().add(x, y, z);
                        if (!(New32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(New32k.mc.player.getPositionVector().add((double)(x - this.rot[0] / 2), (double)y + 0.5, (double)(z + this.rot[1] / 2))) <= 4.5) || !(New32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(New32k.mc.player.getPositionVector().add((double)x + 0.5, (double)y + 2.5, (double)z + 0.5)) <= 4.5) || !WorldUtils.canPlaceBlock(this.pos) || !WorldUtils.isBlockEmpty(this.pos) || !WorldUtils.isBlockEmpty(this.pos.add(this.rot[0], 0, this.rot[1])) || !WorldUtils.isBlockEmpty(this.pos.add(0, 1, 0)) || !WorldUtils.isBlockEmpty(this.pos.add(0, 2, 0)) || !WorldUtils.isBlockEmpty(this.pos.add(this.rot[0], 1, this.rot[1]))) continue;
                        WorldUtils.placeBlock(this.pos, block, true, false);
                        WorldUtils.rotatePacket((double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getX() + 0.5, this.pos.getY() + 1, (double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getZ() + 0.5);
                        WorldUtils.placeBlock(this.pos.add(0, 1, 0), dispenser, false, false);
                        return;
                    }
                }
            }
            this.setEnabled(false);
        } else {
            Command.sendChatMessage("[Auto32k] Cant Place 32k");
            this.setEnabled(false);
        }
    }

    @Override
    public void onUpdate() {
        if ((this.isEnabled() || this.active || this.ticksPassed <= 25) && (!this.active || New32k.mc.currentScreen instanceof GuiHopper)) {
            if (New32k.mc.currentScreen instanceof GuiDispenser) {
                this.openedDispenser = true;
            }
            if (New32k.mc.currentScreen instanceof GuiHopper) {
                int slot;
                GuiHopper gui = (GuiHopper)New32k.mc.currentScreen;
                for (slot = 32; slot <= 40; ++slot) {
                    if (EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)gui.inventorySlots.getSlot(slot).getStack()) <= 5) continue;
                    New32k.mc.player.inventory.currentItem = slot - 32;
                    break;
                }
                this.active = true;
                if (!(((Slot)gui.inventorySlots.inventorySlots.get(0)).getStack().getItem() instanceof ItemAir) && this.active) {
                    slot = New32k.mc.player.inventory.currentItem;
                    boolean pull = false;
                    for (int i = 40; i >= 32; --i) {
                        if (!gui.inventorySlots.getSlot(i).getStack().isEmpty()) continue;
                        slot = i;
                        pull = true;
                        break;
                    }
                    if (pull) {
                        New32k.mc.playerController.windowClick(gui.inventorySlots.windowId, 0, 0, ClickType.PICKUP, (EntityPlayer)New32k.mc.player);
                        New32k.mc.playerController.windowClick(gui.inventorySlots.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)New32k.mc.player);
                    }
                }
            }
            if (this.ticksPassed == 0) {
                WorldUtils.openBlock(this.pos.add(0, 1, 0));
            }
            if (this.openedDispenser && this.dispenserTicks == 0) {
                New32k.mc.playerController.windowClick(New32k.mc.player.openContainer.windowId, 36 + this.shulker, 0, ClickType.QUICK_MOVE, (EntityPlayer)New32k.mc.player);
            }
            if (this.dispenserTicks == 1) {
                mc.displayGuiScreen(null);
                WorldUtils.placeBlock(this.pos.add(0, 2, 0), this.redstone, true, false);
            }
            if (New32k.mc.world.getBlockState(this.pos.add(this.rot[0], 1, this.rot[1])).getBlock() instanceof BlockShulkerBox && New32k.mc.world.getBlockState(this.pos.add(this.rot[0], 0, this.rot[1])).getBlock() != Blocks.HOPPER) {
                WorldUtils.placeBlock(this.pos.add(this.rot[0], 0, this.rot[1]), this.hopper, false, false);
                WorldUtils.openBlock(this.pos.add(this.rot[0], 0, this.rot[1]));
            }
            if (this.openedDispenser) {
                ++this.dispenserTicks;
            }
            ++this.ticksPassed;
        } else {
            this.setEnabled(false);
        }
    }
}

