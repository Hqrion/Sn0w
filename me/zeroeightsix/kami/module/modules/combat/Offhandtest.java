//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.util.List;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

@Module.Info(name="OUTDATED Offhand Crystal", category=Module.Category.HIDDEN)
public class Offhandtest
extends Module {
    public int totems;
    int crystals;
    boolean shouldhack = false;
    boolean moving = false;
    boolean returnI = false;
    Item item;
    private Setting<Integer> health = this.register(Settings.integerBuilder("Health").withRange(0, 36).withValue(16).build());
    private Setting<Boolean> disableGapple = this.register(Settings.b("disable GAP"));
    private Setting<Boolean> SoftGapTransition = this.register(Settings.b("Soft Gap Transition"));

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        if (Offhandtest.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        this.crystals = Offhandtest.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Offhandtest.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
            if (this.crystals == 0) {
                return;
            }
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (Offhandtest.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            Offhandtest.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
            Offhandtest.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
            Offhandtest.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
        }
    }

    @Override
    public void onUpdate() {
        if (!ModuleManager.getModuleByName("Offhand Gap").isEnabled()) {
            int i;
            int t;
            this.item = Items.END_CRYSTAL;
            if (Offhandtest.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            if (this.returnI) {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!Offhandtest.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                Offhandtest.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
                this.returnI = false;
            }
            this.totems = Offhandtest.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
            this.crystals = Offhandtest.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
            if (this.shouldTotem() && Offhandtest.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                ++this.totems;
            } else if (!this.shouldTotem() && Offhandtest.mc.player.getHeldItemOffhand().getItem() == this.item) {
                this.crystals += Offhandtest.mc.player.getHeldItemOffhand().getCount();
            } else {
                if (this.moving) {
                    Offhandtest.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
                    this.moving = false;
                    this.returnI = true;
                    return;
                }
                if (Offhandtest.mc.player.inventory.getItemStack().isEmpty()) {
                    if (!this.shouldTotem() && Offhandtest.mc.player.getHeldItemOffhand().getItem() == this.item) {
                        return;
                    }
                    if (this.shouldTotem() && Offhandtest.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                        return;
                    }
                    if (!this.shouldTotem()) {
                        if (this.crystals == 0) {
                            return;
                        }
                        t = -1;
                        for (i = 0; i < 45; ++i) {
                            if (Offhandtest.mc.player.inventory.getStackInSlot(i).getItem() != this.item) continue;
                            t = i;
                            break;
                        }
                        if (t == -1) {
                            return;
                        }
                        Offhandtest.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
                        this.moving = true;
                    } else {
                        if (this.totems == 0) {
                            return;
                        }
                        t = -1;
                        for (i = 0; i < 45; ++i) {
                            if (Offhandtest.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                            t = i;
                            break;
                        }
                        if (t == -1) {
                            return;
                        }
                        Offhandtest.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.player);
                        this.moving = true;
                    }
                }
            }
        }
    }

    private boolean shouldTotem() {
        boolean hp = Offhandtest.mc.player.getHealth() + Offhandtest.mc.player.getAbsorptionAmount() <= (float)this.health.getValue().intValue();
        boolean endcrystal = !this.isCrystalsAABBEmpty();
        return hp;
    }

    private boolean isEmpty(BlockPos pos) {
        List crystalsInAABB = Offhandtest.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(Offhandtest.mc.player.getPosition().add(1, 0, 0)) && this.isEmpty(Offhandtest.mc.player.getPosition().add(-1, 0, 0)) && this.isEmpty(Offhandtest.mc.player.getPosition().add(0, 0, 1)) && this.isEmpty(Offhandtest.mc.player.getPosition().add(0, 0, -1)) && this.isEmpty(Offhandtest.mc.player.getPosition());
    }
}

