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
            Item item = New32k.mc.field_71439_g.field_71071_by.func_70301_a(x).func_77973_b();
            if (item == Item.func_150898_a((Block)Blocks.field_150438_bZ)) {
                this.hopper = x;
                continue;
            }
            if (item == Item.func_150898_a((Block)Blocks.field_150367_z)) {
                dispenser = x;
                continue;
            }
            if (item == Item.func_150898_a((Block)Blocks.field_150451_bX)) {
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
                        this.pos = New32k.mc.field_71439_g.func_180425_c().func_177982_a(x, y, z);
                        if (!(New32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(New32k.mc.field_71439_g.func_174791_d().func_72441_c((double)(x - this.rot[0] / 2), (double)y + 0.5, (double)(z + this.rot[1] / 2))) <= 4.5) || !(New32k.mc.field_71439_g.func_174824_e(mc.func_184121_ak()).func_72438_d(New32k.mc.field_71439_g.func_174791_d().func_72441_c((double)x + 0.5, (double)y + 2.5, (double)z + 0.5)) <= 4.5) || !WorldUtils.canPlaceBlock(this.pos) || !WorldUtils.isBlockEmpty(this.pos) || !WorldUtils.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])) || !WorldUtils.isBlockEmpty(this.pos.func_177982_a(0, 1, 0)) || !WorldUtils.isBlockEmpty(this.pos.func_177982_a(0, 2, 0)) || !WorldUtils.isBlockEmpty(this.pos.func_177982_a(this.rot[0], 1, this.rot[1]))) continue;
                        WorldUtils.placeBlock(this.pos, block, true, false);
                        WorldUtils.rotatePacket((double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177958_n() + 0.5, this.pos.func_177956_o() + 1, (double)this.pos.func_177982_a(-this.rot[0], 1, -this.rot[1]).func_177952_p() + 0.5);
                        WorldUtils.placeBlock(this.pos.func_177982_a(0, 1, 0), dispenser, false, false);
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
        if ((this.isEnabled() || this.active || this.ticksPassed <= 25) && (!this.active || New32k.mc.field_71462_r instanceof GuiHopper)) {
            if (New32k.mc.field_71462_r instanceof GuiDispenser) {
                this.openedDispenser = true;
            }
            if (New32k.mc.field_71462_r instanceof GuiHopper) {
                int slot;
                GuiHopper gui = (GuiHopper)New32k.mc.field_71462_r;
                for (slot = 32; slot <= 40; ++slot) {
                    if (EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185302_k, (ItemStack)gui.field_147002_h.func_75139_a(slot).func_75211_c()) <= 5) continue;
                    New32k.mc.field_71439_g.field_71071_by.field_70461_c = slot - 32;
                    break;
                }
                this.active = true;
                if (!(((Slot)gui.field_147002_h.field_75151_b.get(0)).func_75211_c().func_77973_b() instanceof ItemAir) && this.active) {
                    slot = New32k.mc.field_71439_g.field_71071_by.field_70461_c;
                    boolean pull = false;
                    for (int i = 40; i >= 32; --i) {
                        if (!gui.field_147002_h.func_75139_a(i).func_75211_c().func_190926_b()) continue;
                        slot = i;
                        pull = true;
                        break;
                    }
                    if (pull) {
                        New32k.mc.field_71442_b.func_187098_a(gui.field_147002_h.field_75152_c, 0, 0, ClickType.PICKUP, (EntityPlayer)New32k.mc.field_71439_g);
                        New32k.mc.field_71442_b.func_187098_a(gui.field_147002_h.field_75152_c, slot, 0, ClickType.PICKUP, (EntityPlayer)New32k.mc.field_71439_g);
                    }
                }
            }
            if (this.ticksPassed == 0) {
                WorldUtils.openBlock(this.pos.func_177982_a(0, 1, 0));
            }
            if (this.openedDispenser && this.dispenserTicks == 0) {
                New32k.mc.field_71442_b.func_187098_a(New32k.mc.field_71439_g.field_71070_bA.field_75152_c, 36 + this.shulker, 0, ClickType.QUICK_MOVE, (EntityPlayer)New32k.mc.field_71439_g);
            }
            if (this.dispenserTicks == 1) {
                mc.func_147108_a(null);
                WorldUtils.placeBlock(this.pos.func_177982_a(0, 2, 0), this.redstone, true, false);
            }
            if (New32k.mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 1, this.rot[1])).func_177230_c() instanceof BlockShulkerBox && New32k.mc.field_71441_e.func_180495_p(this.pos.func_177982_a(this.rot[0], 0, this.rot[1])).func_177230_c() != Blocks.field_150438_bZ) {
                WorldUtils.placeBlock(this.pos.func_177982_a(this.rot[0], 0, this.rot[1]), this.hopper, false, false);
                WorldUtils.openBlock(this.pos.func_177982_a(this.rot[0], 0, this.rot[1]));
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

