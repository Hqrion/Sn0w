/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiHopper
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Enchantments
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAir
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.DecimalFormat;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Hopper Auto32k", category=Module.Category.COMBAT, description="Automatically places a hopper then a shulker ontop")
public class Hopper32k
extends Module {
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private String stagething;
    private int Hopperslot;
    private int ShulkerSlot;
    private int playerHotbarSlot = -1;
    private BlockPos placeTarget;
    private BlockPos placeTarget2;
    private boolean active;
    private int beds;
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", false));
    private Setting<Boolean> insultonfail = this.register(Settings.b("Insult On Fail", false));
    private Setting<Boolean> AirPlace = this.register(Settings.b("AirPlace", false));
    private boolean isSneaking;

    @Override
    protected void onEnable() {
        this.Hopperslot = -1;
        this.ShulkerSlot = -1;
        this.isSneaking = false;
        this.placeTarget = null;
        this.placeTarget2 = null;
        for (int x = 0; x <= 8; ++x) {
            Item item = Hopper32k.mc.field_71439_g.field_71071_by.func_70301_a(x).func_77973_b();
            if (item == Item.func_150898_a((Block)Blocks.field_150438_bZ)) {
                this.Hopperslot = x;
                continue;
            }
            if (!(item instanceof ItemShulkerBox)) continue;
            this.ShulkerSlot = x;
        }
        RayTraceResult lookingAt = Minecraft.func_71410_x().field_71476_x;
        if (lookingAt != null && lookingAt.field_72313_a == RayTraceResult.Type.BLOCK) {
            this.placeTarget = Hopper32k.mc.field_71476_x.func_178782_a().func_177984_a();
            this.placeTarget2 = Hopper32k.mc.field_71476_x.func_178782_a().func_177981_b(1);
            Hopper32k.mc.field_71439_g.field_71071_by.field_70461_c = this.Hopperslot;
            this.stagething = "HOPPER";
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            Hopper32k.mc.field_71439_g.field_71071_by.field_70461_c = this.ShulkerSlot;
            this.stagething = "SHULKER";
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.func_177984_a()), EnumFacing.DOWN);
            Hopper32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            this.stagething = "OPENING";
            WorldUtils.openBlock(this.placeTarget);
            Command.sendChatMessage("[Auto32kHopper] " + ChatFormatting.GREEN + "Succesfully" + ChatFormatting.WHITE + " placed 32k");
        } else {
            if (this.insultonfail.getValue().booleanValue()) {
                Command.sendChatMessage("[Auto32kHopper] " + ChatFormatting.RED + "FAILED" + ChatFormatting.WHITE + " because your dumbass thought you could place there");
            } else {
                Command.sendChatMessage("[Auto32kHopper] " + ChatFormatting.RED + "Invalid" + ChatFormatting.WHITE + " place location");
            }
            this.disable();
        }
    }

    @Override
    public void onUpdate() {
        if (Hopper32k.mc.field_71462_r instanceof GuiHopper) {
            int slot;
            GuiHopper gui = (GuiHopper)Hopper32k.mc.field_71462_r;
            for (slot = 32; slot <= 40; ++slot) {
                if (EnchantmentHelper.func_77506_a((Enchantment)Enchantments.field_185302_k, (ItemStack)gui.field_147002_h.func_75139_a(slot).func_75211_c()) <= 5) continue;
                Hopper32k.mc.field_71439_g.field_71071_by.field_70461_c = slot - 32;
                break;
            }
            this.active = true;
            if (!(((Slot)gui.field_147002_h.field_75151_b.get(0)).func_75211_c().func_77973_b() instanceof ItemAir) && this.active) {
                slot = Hopper32k.mc.field_71439_g.field_71071_by.field_70461_c;
                boolean pull = false;
                for (int i = 40; i >= 32; --i) {
                    if (!gui.field_147002_h.func_75139_a(i).func_75211_c().func_190926_b()) continue;
                    slot = i;
                    pull = true;
                    break;
                }
                if (pull) {
                    this.stagething = "HOPPER GUI";
                    Hopper32k.mc.field_71442_b.func_187098_a(gui.field_147002_h.field_75152_c, 0, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.field_71439_g);
                    Hopper32k.mc.field_71442_b.func_187098_a(gui.field_147002_h.field_75152_c, slot, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.field_71439_g);
                    this.disable();
                }
            }
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        if (!this.isSneaking) {
            Hopper32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        Hopper32k.mc.field_71442_b.func_187099_a(Hopper32k.mc.field_71439_g, Hopper32k.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Hopper32k.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
}

