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
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
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

@Module.Info(name="Smart Offhand", category=Module.Category.COMBAT)
public class SmartOffHand
extends Module {
    public int totems;
    int crystals;
    boolean moving = false;
    boolean returnI = false;
    Item item;
    private Setting<Integer> health = this.register(Settings.integerBuilder("Health").withRange(0, 36).withValue(16).build());
    private Setting<Boolean> crystalCheck = this.register(Settings.b("CrystalCheck", false));
    private Setting<Boolean> message = this.register(Settings.b("message", true));

    @Override
    public void onUpdate() {
        int i;
        int t;
        this.item = Items.END_CRYSTAL;
        if (SmartOffHand.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t = -1;
            for (i = 0; i < 45; ++i) {
                if (!SmartOffHand.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            SmartOffHand.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.player);
            this.returnI = false;
        }
        this.totems = SmartOffHand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        this.crystals = SmartOffHand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
        if (this.shouldTotem() && SmartOffHand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        } else if (!this.shouldTotem() && SmartOffHand.mc.player.getHeldItemOffhand().getItem() == this.item) {
            this.crystals += SmartOffHand.mc.player.getHeldItemOffhand().getCount();
        } else {
            if (this.moving) {
                SmartOffHand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.player);
                this.moving = false;
                this.returnI = true;
                return;
            }
            if (SmartOffHand.mc.player.inventory.getItemStack().isEmpty()) {
                if (!this.shouldTotem() && SmartOffHand.mc.player.getHeldItemOffhand().getItem() == this.item) {
                    return;
                }
                if (this.shouldTotem() && SmartOffHand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                    return;
                }
                if (!this.shouldTotem()) {
                    if (this.crystals == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (SmartOffHand.mc.player.inventory.getStackInSlot(i).getItem() != this.item) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    SmartOffHand.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.player);
                    this.moving = true;
                } else {
                    if (this.totems == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (SmartOffHand.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    SmartOffHand.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.player);
                    this.moving = true;
                }
            } else {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!SmartOffHand.mc.player.inventory.getStackInSlot(i).isEmpty()) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                SmartOffHand.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.player);
            }
        }
    }

    private boolean shouldTotem() {
        boolean endcrystal;
        boolean hp = SmartOffHand.mc.player.getHealth() + SmartOffHand.mc.player.getAbsorptionAmount() <= (float)this.health.getValue().intValue();
        boolean bl = endcrystal = !this.isCrystalsAABBEmpty();
        if (this.crystalCheck.getValue().booleanValue()) {
            return hp || endcrystal;
        }
        return hp;
    }

    private boolean isEmpty(BlockPos pos) {
        List crystalsInAABB = SmartOffHand.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(SmartOffHand.mc.player.getPosition().add(1, 0, 0)) && this.isEmpty(SmartOffHand.mc.player.getPosition().add(-1, 0, 0)) && this.isEmpty(SmartOffHand.mc.player.getPosition().add(0, 0, 1)) && this.isEmpty(SmartOffHand.mc.player.getPosition().add(0, 0, -1)) && this.isEmpty(SmartOffHand.mc.player.getPosition());
    }

    @Override
    protected void onEnable() {
        if (this.message.getValue().booleanValue()) {
            Command.sendChatMessage("\u00a7dCrystal Count =" + this.crystals);
        }
    }
}

