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
import me.zeroeightsix.kami.module.modules.combat.Offhandtest;
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

@Module.Info(name="OUTDATED Offhand Gap", category=Module.Category.HIDDEN)
public class OffhandGapple
extends Module {
    public int totems;
    int crystals;
    boolean moving = false;
    boolean returnI = false;
    Item item;
    private Setting<Integer> health = this.register(Settings.integerBuilder("Health").withRange(0, 36).withValue(16).build());
    private Setting<Boolean> disableCrystal = this.register(Settings.b("Disable Crystal"));

    @Override
    public void onEnable() {
        if (ModuleManager.getModuleByName("Offhand Crystal").isEnabled()) {
            if (Offhandtest.mc.field_71462_r instanceof GuiContainer) {
                return;
            }
            this.crystals = Offhandtest.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
            if (Offhandtest.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY) {
                if (this.crystals == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (Offhandtest.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_190929_cY) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                Offhandtest.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                Offhandtest.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                Offhandtest.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
            }
        }
    }

    @Override
    public void onDisable() {
        if (OffhandGapple.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        this.crystals = OffhandGapple.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        if (OffhandGapple.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY) {
            if (this.crystals == 0) {
                return;
            }
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (OffhandGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_190929_cY) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            OffhandGapple.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
            OffhandGapple.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
            OffhandGapple.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
        }
    }

    @Override
    public void onUpdate() {
        int i;
        int t;
        this.item = Items.field_151153_ao;
        if (OffhandGapple.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t = -1;
            for (i = 0; i < 45; ++i) {
                if (!OffhandGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) continue;
                t = i;
                break;
            }
            if (t == -1) {
                return;
            }
            OffhandGapple.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
            this.returnI = false;
        }
        this.totems = OffhandGapple.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        this.crystals = OffhandGapple.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == this.item).mapToInt(ItemStack::func_190916_E).sum();
        if (this.shouldTotem() && OffhandGapple.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
            ++this.totems;
        } else if (!this.shouldTotem() && OffhandGapple.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
            this.crystals += OffhandGapple.mc.field_71439_g.func_184592_cb().func_190916_E();
        } else {
            if (this.moving) {
                OffhandGapple.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
                this.moving = false;
                this.returnI = true;
                return;
            }
            if (OffhandGapple.mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                if (!this.shouldTotem() && OffhandGapple.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
                    return;
                }
                if (this.shouldTotem() && OffhandGapple.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
                    return;
                }
                if (!this.shouldTotem()) {
                    if (this.crystals == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (OffhandGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != this.item) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    OffhandGapple.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
                    this.moving = true;
                } else {
                    if (this.totems == 0) {
                        return;
                    }
                    t = -1;
                    for (i = 0; i < 45; ++i) {
                        if (OffhandGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_190929_cY) continue;
                        t = i;
                        break;
                    }
                    if (t == -1) {
                        return;
                    }
                    OffhandGapple.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
                    this.moving = true;
                }
            } else {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!OffhandGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                OffhandGapple.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)OffhandGapple.mc.field_71439_g);
            }
        }
    }

    private boolean shouldTotem() {
        boolean hp = OffhandGapple.mc.field_71439_g.func_110143_aJ() + OffhandGapple.mc.field_71439_g.func_110139_bj() <= (float)this.health.getValue().intValue();
        boolean endcrystal = !this.isCrystalsAABBEmpty();
        return hp;
    }

    private boolean isEmpty(BlockPos pos) {
        List crystalsInAABB = OffhandGapple.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(OffhandGapple.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 0)) && this.isEmpty(OffhandGapple.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 0)) && this.isEmpty(OffhandGapple.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 1)) && this.isEmpty(OffhandGapple.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -1)) && this.isEmpty(OffhandGapple.mc.field_71439_g.func_180425_c());
    }
}

