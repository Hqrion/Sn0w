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
        this.item = Items.field_185158_cP;
        if (SmartOffHand.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t = -1;
            for (i = 0; i < 45; ++i) {
                if (!SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            SmartOffHand.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
            this.returnI = false;
        }
        this.totems = SmartOffHand.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        this.crystals = SmartOffHand.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == this.item).mapToInt(ItemStack::func_190916_E).sum();
        if (this.shouldTotem() && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
            ++this.totems;
        } else if (!this.shouldTotem() && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
            this.crystals += SmartOffHand.mc.field_71439_g.func_184592_cb().func_190916_E();
        } else {
            if (this.moving) {
                SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                this.moving = false;
                this.returnI = true;
                return;
            }
            if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                if (!this.shouldTotem() && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
                    return;
                }
                if (this.shouldTotem() && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
                    return;
                }
                if (!this.shouldTotem()) {
                    if (this.crystals == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != this.item) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    this.moving = true;
                } else {
                    if (this.totems == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_190929_cY) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    this.moving = true;
                }
            } else {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                SmartOffHand.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
            }
        }
    }

    private boolean shouldTotem() {
        boolean endcrystal;
        boolean hp = SmartOffHand.mc.field_71439_g.func_110143_aJ() + SmartOffHand.mc.field_71439_g.func_110139_bj() <= (float)this.health.getValue().intValue();
        boolean bl = endcrystal = !this.isCrystalsAABBEmpty();
        if (this.crystalCheck.getValue().booleanValue()) {
            return hp || endcrystal;
        }
        return hp;
    }

    private boolean isEmpty(BlockPos pos) {
        List crystalsInAABB = SmartOffHand.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(SmartOffHand.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 0)) && this.isEmpty(SmartOffHand.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 0)) && this.isEmpty(SmartOffHand.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 1)) && this.isEmpty(SmartOffHand.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -1)) && this.isEmpty(SmartOffHand.mc.field_71439_g.func_180425_c());
    }

    @Override
    protected void onEnable() {
        if (this.message.getValue().booleanValue()) {
            Command.sendChatMessage("\u00a7dCrystal Count =" + this.crystals);
        }
    }
}

