/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Module.Info(name="AutoTool", description="Automatically switch to the best tools when mining or attacking", category=Module.Category.MISC)
public class AutoTool
extends Module {
    @EventHandler
    private Listener<PlayerInteractEvent.LeftClickBlock> leftClickListener = new Listener<PlayerInteractEvent.LeftClickBlock>(event -> this.equipBestTool(AutoTool.mc.field_71441_e.func_180495_p(event.getPos())), new Predicate[0]);
    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<AttackEntityEvent>(event -> {
        if (event.getTarget() instanceof EntityEnderCrystal) {
            return;
        }
        AutoTool.equipBestWeapon();
    }, new Predicate[0]);

    private void equipBestTool(IBlockState blockState) {
        int bestSlot = -1;
        double max = 0.0;
        for (int i = 0; i < 9; ++i) {
            int eff;
            float speed;
            ItemStack stack = AutoTool.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.field_190928_g || !((speed = stack.func_150997_a(blockState)) > 1.0f) || !((double)(speed = (float)((double)speed + ((eff = EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185305_q, (ItemStack)stack)) > 0 ? Math.pow(eff, 2.0) + 1.0 : 0.0))) > max)) continue;
            max = speed;
            bestSlot = i;
        }
        if (bestSlot != -1) {
            AutoTool.equip(bestSlot);
        }
    }

    public static void equipBestWeapon() {
        int bestSlot = -1;
        double maxDamage = 0.0;
        for (int i = 0; i < 9; ++i) {
            double damage;
            ItemStack stack = AutoTool.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.field_190928_g) continue;
            if (stack.func_77973_b() instanceof ItemTool) {
                damage = (double)((ItemTool)stack.func_77973_b()).field_77865_bY + (double)EnchantmentHelper.func_152377_a((ItemStack)stack, (EnumCreatureAttribute)EnumCreatureAttribute.UNDEFINED);
                if (!(damage > maxDamage)) continue;
                maxDamage = damage;
                bestSlot = i;
                continue;
            }
            if (!(stack.func_77973_b() instanceof ItemSword) || !((damage = (double)((ItemSword)stack.func_77973_b()).func_150931_i() + (double)EnchantmentHelper.func_152377_a((ItemStack)stack, (EnumCreatureAttribute)EnumCreatureAttribute.UNDEFINED)) > maxDamage)) continue;
            maxDamage = damage;
            bestSlot = i;
        }
        if (bestSlot != -1) {
            AutoTool.equip(bestSlot);
        }
    }

    private static void equip(int slot) {
        AutoTool.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        AutoTool.mc.field_71442_b.func_78750_j();
    }
}

