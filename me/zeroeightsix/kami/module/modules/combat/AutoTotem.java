//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

@Module.Info(name="AutoTotem", category=Module.Category.COMBAT)
public class AutoTotem
extends Module {
    int totems;
    boolean moving = false;
    boolean returnI = false;
    private Setting<Boolean> soft = this.register(Settings.b("Soft"));

    @Override
    public void onUpdate() {
        int i;
        int t;
        if (AutoTotem.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t = -1;
            for (i = 0; i < 45; ++i) {
                if (!AutoTotem.mc.player.inventory.getStackInSlot((int)i).isEmpty) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            AutoTotem.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            this.returnI = false;
        }
        this.totems = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        } else {
            if (this.soft.getValue().booleanValue() && !AutoTotem.mc.player.getHeldItemOffhand().isEmpty) {
                return;
            }
            if (this.moving) {
                AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = false;
                if (!AutoTotem.mc.player.inventory.itemStack.isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (AutoTotem.mc.player.inventory.itemStack.isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (AutoTotem.mc.player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = true;
            } else if (!this.soft.getValue().booleanValue()) {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!AutoTotem.mc.player.inventory.getStackInSlot((int)i).isEmpty) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            }
        }
    }

    public void disableSoft() {
        this.soft.setValue(false);
    }

    @Override
    public String getHudInfo() {
        return String.valueOf(this.totems);
    }
}

