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

    @Override
    public void onUpdate() {
        if (!ModuleManager.getModuleByName("Offhand Gap").isEnabled()) {
            int i;
            int t;
            this.item = Items.field_185158_cP;
            if (Offhandtest.mc.field_71462_r instanceof GuiContainer) {
                return;
            }
            if (this.returnI) {
                t = -1;
                for (i = 0; i < 45; ++i) {
                    if (!Offhandtest.mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) continue;
                    t = i;
                    break;
                }
                if (t == -1) {
                    return;
                }
                Offhandtest.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                this.returnI = false;
            }
            this.totems = Offhandtest.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
            this.crystals = Offhandtest.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == this.item).mapToInt(ItemStack::func_190916_E).sum();
            if (this.shouldTotem() && Offhandtest.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
                ++this.totems;
            } else if (!this.shouldTotem() && Offhandtest.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
                this.crystals += Offhandtest.mc.field_71439_g.func_184592_cb().func_190916_E();
            } else {
                if (this.moving) {
                    Offhandtest.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                    this.moving = false;
                    this.returnI = true;
                    return;
                }
                if (Offhandtest.mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
                    if (!this.shouldTotem() && Offhandtest.mc.field_71439_g.func_184592_cb().func_77973_b() == this.item) {
                        return;
                    }
                    if (this.shouldTotem() && Offhandtest.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
                        return;
                    }
                    if (!this.shouldTotem()) {
                        if (this.crystals == 0) {
                            return;
                        }
                        t = -1;
                        for (i = 0; i < 45; ++i) {
                            if (Offhandtest.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != this.item) continue;
                            t = i;
                            break;
                        }
                        if (t == -1) {
                            return;
                        }
                        Offhandtest.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                        this.moving = true;
                    } else {
                        if (this.totems == 0) {
                            return;
                        }
                        t = -1;
                        for (i = 0; i < 45; ++i) {
                            if (Offhandtest.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_190929_cY) continue;
                            t = i;
                            break;
                        }
                        if (t == -1) {
                            return;
                        }
                        Offhandtest.mc.field_71442_b.func_187098_a(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, (EntityPlayer)Offhandtest.mc.field_71439_g);
                        this.moving = true;
                    }
                }
            }
        }
    }

    private boolean shouldTotem() {
        boolean hp = Offhandtest.mc.field_71439_g.func_110143_aJ() + Offhandtest.mc.field_71439_g.func_110139_bj() <= (float)this.health.getValue().intValue();
        boolean endcrystal = !this.isCrystalsAABBEmpty();
        return hp;
    }

    private boolean isEmpty(BlockPos pos) {
        List crystalsInAABB = Offhandtest.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(Offhandtest.mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 0)) && this.isEmpty(Offhandtest.mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 0)) && this.isEmpty(Offhandtest.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 1)) && this.isEmpty(Offhandtest.mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -1)) && this.isEmpty(Offhandtest.mc.field_71439_g.func_180425_c());
    }
}

