//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
            Item item = Hopper32k.mc.player.inventory.getStackInSlot(x).getItem();
            if (item == Item.getItemFromBlock((Block)Blocks.HOPPER)) {
                this.Hopperslot = x;
                continue;
            }
            if (!(item instanceof ItemShulkerBox)) continue;
            this.ShulkerSlot = x;
        }
        RayTraceResult lookingAt = Minecraft.getMinecraft().objectMouseOver;
        if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) {
            this.placeTarget = Hopper32k.mc.objectMouseOver.getBlockPos().up();
            this.placeTarget2 = Hopper32k.mc.objectMouseOver.getBlockPos().up(1);
            Hopper32k.mc.player.inventory.currentItem = this.Hopperslot;
            this.stagething = "HOPPER";
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            Hopper32k.mc.player.inventory.currentItem = this.ShulkerSlot;
            this.stagething = "SHULKER";
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.up()), EnumFacing.DOWN);
            Hopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
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
        if (Hopper32k.mc.currentScreen instanceof GuiHopper) {
            int slot;
            GuiHopper gui = (GuiHopper)Hopper32k.mc.currentScreen;
            for (slot = 32; slot <= 40; ++slot) {
                if (EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)gui.inventorySlots.getSlot(slot).getStack()) <= 5) continue;
                Hopper32k.mc.player.inventory.currentItem = slot - 32;
                break;
            }
            this.active = true;
            if (!(((Slot)gui.inventorySlots.inventorySlots.get(0)).getStack().getItem() instanceof ItemAir) && this.active) {
                slot = Hopper32k.mc.player.inventory.currentItem;
                boolean pull = false;
                for (int i = 40; i >= 32; --i) {
                    if (!gui.inventorySlots.getSlot(i).getStack().isEmpty()) continue;
                    slot = i;
                    pull = true;
                    break;
                }
                if (pull) {
                    this.stagething = "HOPPER GUI";
                    Hopper32k.mc.playerController.windowClick(gui.inventorySlots.windowId, 0, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.player);
                    Hopper32k.mc.playerController.windowClick(gui.inventorySlots.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.player);
                    this.disable();
                }
            }
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!this.isSneaking) {
            Hopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        Hopper32k.mc.playerController.processRightClickBlock(Hopper32k.mc.player, Hopper32k.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Hopper32k.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}

