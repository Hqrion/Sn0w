//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Auto 32k Dispenser", category=Module.Category.COMBAT, description="Do not use with any AntiGhostBlock Mod!")
public class DispenserMeta
extends Module {
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", false));
    private Setting<Boolean> grabItem = this.register(Settings.b("Grab Item", false));
    private Setting<Boolean> autoEnableHitAura = this.register(Settings.b("Auto enable Hit Aura", false));
    private Setting<Boolean> autoEnableBypass = this.register(Settings.b("Auto enable Secret Close", false));
    private Setting<Boolean> SideRedstone = this.register(Settings.b("Redstone On Side", false));
    private Setting<Boolean> debugMessages = this.register(Settings.b("Debug Messages", false));
    private int stage;
    private BlockPos placeTarget;
    private BlockPos redstonetarget;
    private int obiSlot;
    private int dispenserSlot;
    private int shulkerSlot;
    private int redstoneSlot;
    private int hopperSlot;
    private boolean isSneaking;

    @Override
    protected void onEnable() {
        if (DispenserMeta.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            this.disable();
            return;
        }
        df.setRoundingMode(RoundingMode.CEILING);
        this.stage = 0;
        this.placeTarget = null;
        this.obiSlot = -1;
        this.dispenserSlot = -1;
        this.shulkerSlot = -1;
        this.redstoneSlot = -1;
        this.hopperSlot = -1;
        this.isSneaking = false;
        for (int i = 0; i < 9 && (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1); ++i) {
            ItemStack stack = DispenserMeta.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (block == Blocks.HOPPER) {
                this.hopperSlot = i;
                continue;
            }
            if (BlockInteractionHelper.shulkerList.contains(block)) {
                this.shulkerSlot = i;
                continue;
            }
            if (block == Blocks.OBSIDIAN) {
                this.obiSlot = i;
                continue;
            }
            if (block == Blocks.DISPENSER) {
                this.dispenserSlot = i;
                continue;
            }
            if (block != Blocks.REDSTONE_BLOCK) continue;
            this.redstoneSlot = i;
        }
        if (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[Auto32k] Items missing, disabling.");
            }
            this.disable();
            return;
        }
        if (DispenserMeta.mc.objectMouseOver == null || DispenserMeta.mc.objectMouseOver.getBlockPos() == null || DispenserMeta.mc.objectMouseOver.getBlockPos().up() == null) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[Auto32k] Not a valid place target, disabling.");
            }
            this.disable();
            return;
        }
        this.placeTarget = DispenserMeta.mc.objectMouseOver.getBlockPos().up();
        if (DispenserMeta.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
            this.redstonetarget = this.placeTarget.up().up().east();
        }
        if (DispenserMeta.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
            this.redstonetarget = this.placeTarget.up().up().north();
        }
        if (DispenserMeta.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST)) {
            this.redstonetarget = this.placeTarget.up().up().south();
        }
        if (DispenserMeta.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
            this.redstonetarget = this.placeTarget.up().up().west();
        }
        if (this.autoEnableBypass.getValue().booleanValue()) {
            ModuleManager.getModuleByName("Secret Close").enable();
        }
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[Auto32k] Place Target: " + this.placeTarget.x + " " + this.placeTarget.y + " " + this.placeTarget.z + " Distance: " + df.format(DispenserMeta.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)this.placeTarget))));
        }
    }

    @Override
    public void onUpdate() {
        if (DispenserMeta.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.stage == 0) {
            DispenserMeta.mc.player.inventory.currentItem = this.obiSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            DispenserMeta.mc.player.inventory.currentItem = this.dispenserSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 1, 0)), EnumFacing.DOWN);
            DispenserMeta.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            DispenserMeta.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.add(0, 1, 0), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.stage = 1;
            return;
        }
        if (this.stage == 1) {
            if (!(DispenserMeta.mc.currentScreen instanceof GuiContainer)) {
                return;
            }
            DispenserMeta.mc.playerController.windowClick(DispenserMeta.mc.player.openContainer.windowId, 1, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)DispenserMeta.mc.player);
            DispenserMeta.mc.player.closeScreen();
            DispenserMeta.mc.player.inventory.currentItem = this.redstoneSlot;
            if (this.SideRedstone.getValue().booleanValue()) {
                this.placeBlock(new BlockPos((Vec3i)this.redstonetarget), EnumFacing.DOWN);
            } else {
                this.placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 2, 0)), EnumFacing.DOWN);
            }
            this.stage = 2;
            return;
        }
        if (this.stage == 2) {
            Block block = DispenserMeta.mc.world.getBlockState(this.placeTarget.offset(DispenserMeta.mc.player.getHorizontalFacing().getOpposite()).up()).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid) {
                return;
            }
            DispenserMeta.mc.player.inventory.currentItem = this.hopperSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.offset(DispenserMeta.mc.player.getHorizontalFacing().getOpposite())), DispenserMeta.mc.player.getHorizontalFacing());
            DispenserMeta.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            DispenserMeta.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.offset(DispenserMeta.mc.player.getHorizontalFacing().getOpposite()), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            DispenserMeta.mc.player.inventory.currentItem = this.shulkerSlot;
            if (!this.grabItem.getValue().booleanValue()) {
                this.disable();
                return;
            }
            this.stage = 3;
            return;
        }
        if (this.stage == 3) {
            if (!(DispenserMeta.mc.currentScreen instanceof GuiContainer)) {
                return;
            }
            if (((GuiContainer)DispenserMeta.mc.currentScreen).inventorySlots.getSlot((int)0).getStack().isEmpty) {
                return;
            }
            DispenserMeta.mc.playerController.windowClick(DispenserMeta.mc.player.openContainer.windowId, 0, DispenserMeta.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)DispenserMeta.mc.player);
            if (this.autoEnableHitAura.getValue().booleanValue()) {
                ModuleManager.getModuleByName("Aura").enable();
            }
            this.disable();
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!this.isSneaking) {
            DispenserMeta.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)DispenserMeta.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        DispenserMeta.mc.playerController.processRightClickBlock(DispenserMeta.mc.player, DispenserMeta.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        DispenserMeta.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}

