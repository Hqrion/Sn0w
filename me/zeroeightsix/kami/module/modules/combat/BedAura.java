//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBed
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.tileentity.TileEntityBed
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BedAuraUtil;
import me.zeroeightsix.kami.util.BlocksUtils;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="AutoBedBomb", category=Module.Category.COMBAT)
public class BedAura
extends Module {
    private BlockPos render;
    private Setting<Boolean> autoSwitch;
    private Setting<Boolean> antiSuicide;
    private Setting<Boolean> announceusage;
    private Setting<Boolean> refill;
    private Setting<Boolean> rotate;
    private Setting<Integer> range;
    private Setting<Integer> switchDelay;
    private Setting<Integer> antiSuicideHlth;
    boolean moving = false;
    BlockPos targetBlock;
    BlockPos targetPlayer;
    BlockPos west;
    BlockPos east;
    BlockPos north;
    BlockPos south;
    EnumFacing facing;
    BlockPos targetPlace;
    BedAuraUtil util = new BedAuraUtil();

    public BedAura() {
        this.range = this.register(Settings.integerBuilder("Range").withMinimum(0).withMaximum(10).withValue(6));
        this.switchDelay = this.register(Settings.integerBuilder("Switch Delay").withMinimum(0).withMaximum(10).withValue(3));
        this.antiSuicideHlth = this.register(Settings.integerBuilder("Anti Suicide Health").withMinimum(0).withMaximum(36).withValue(16).withVisibility(b -> this.antiSuicide.getValue()).build());
        this.announceusage = this.register(Settings.b("Announce Usage", true));
        this.antiSuicide = this.register(Settings.b("AntiSuicide", true));
        this.autoSwitch = this.register(Settings.b("AutoSwitch", true));
        this.refill = this.register(Settings.b("Auto Refill", true));
        this.rotate = this.register(Settings.b("Rotate", true));
    }

    @Override
    public void onEnable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("AutoBedBomb" + ChatFormatting.GREEN.toString() + " Enabled");
        }
    }

    @Override
    public void onDisable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("AutoBedBomb" + ChatFormatting.RED.toString() + " Disabled");
        }
    }

    @Override
    public void onUpdate() {
        BedAura.mc.world.loadedTileEntityList.stream().filter(e -> e instanceof TileEntityBed).filter(e -> BedAura.mc.player.getDistance((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ()) <= (double)this.range.getValue().intValue()).sorted(Comparator.comparing(e -> BedAura.mc.player.getDistance((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ()))).forEach(bed -> {
            if (BedAura.mc.player.dimension == 0) {
                return;
            }
            if (this.rotate.getValue().booleanValue()) {
                BlocksUtils.faceVectorPacketInstant(new Vec3d((Vec3i)bed.getPos().add(0.5, 0.5, 0.5)));
            }
            BedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        });
        if (this.refill.getValue().booleanValue()) {
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                if (BedAura.mc.player.inventory.getStackInSlot(i) != ItemStack.EMPTY) continue;
                slot = i;
                break;
            }
            if (this.moving && slot != -1) {
                BedAura.mc.playerController.windowClick(0, slot + 36, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.player);
                this.moving = false;
                slot = -1;
            }
            int bedSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = BedAura.mc.player.inventory.getStackInSlot(i);
                if (!(stack.getItem() instanceof ItemBed)) continue;
                bedSlot = 1;
                break;
            }
            if (slot != -1 && !(BedAura.mc.currentScreen instanceof GuiContainer) && BedAura.mc.player.inventory.getItemStack().isEmpty()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (BedAura.mc.player.inventory.getStackInSlot(i).getItem() != Items.BED || i < 9) continue;
                    t = i;
                    break;
                }
                if (bedSlot != -1 && BedAura.mc.player.ticksExisted % this.switchDelay.getValue() == 0) {
                    BedAura.mc.player.inventory.currentItem = bedSlot;
                    BedAura.mc.playerController.updateController();
                }
                if (t != -1) {
                    BedAura.mc.playerController.windowClick(0, t, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.player);
                    this.moving = true;
                }
            }
        }
        if (BedAura.mc.player.getHealth() < (float)this.antiSuicideHlth.getValue().intValue() && this.antiSuicide.getValue().booleanValue()) {
            this.disable();
        }
        for (EntityPlayer player : this.getTargets()) {
            this.targetPlayer = new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ);
            if (!(this.targetPlayer.getDistance((int)BedAura.mc.player.posX, (int)BedAura.mc.player.posY, (int)BedAura.mc.player.posZ) <= (double)this.range.getValue().intValue())) continue;
            this.west = this.targetPlayer.west();
            this.east = this.targetPlayer.east();
            this.north = this.targetPlayer.north();
            this.south = this.targetPlayer.south();
            if (player.isElytraFlying()) {
                this.targetBlock = this.util.beddistance(this.west, this.east, this.north, this.south);
                this.targetBlock = new BlockPos(this.targetBlock.getX(), this.targetBlock.getY() - 1, this.targetBlock.getZ());
            } else {
                this.targetBlock = this.util.beddistance(this.west, this.east, this.north, this.south);
            }
            if (this.targetBlock == null) continue;
            if (this.targetBlock == this.west) {
                this.facing = EnumFacing.EAST;
            }
            if (this.targetBlock == this.east) {
                this.facing = EnumFacing.WEST;
            }
            if (this.targetBlock == this.north) {
                this.facing = EnumFacing.SOUTH;
            }
            if (this.targetBlock == this.south) {
                this.facing = EnumFacing.NORTH;
            }
            if (this.facing == null) continue;
            if (this.autoSwitch.getValue().booleanValue()) {
                this.switchHandToItemIfNeed(Items.BED);
            }
            if (BedAura.mc.player.getHeldItemMainhand().getItem() != Items.BED) continue;
            this.targetPlace = new BlockPos(this.targetBlock.getX(), this.targetBlock.getY() + 1, this.targetBlock.getZ());
            this.placeBlock(this.targetPlace, this.facing);
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.targetBlock != null) {
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            KamiTessellator.prepare(7);
            KamiTessellator.drawBox(this.targetBlock, 5, 5, 255, 52, 63);
            KamiTessellator.release();
            KamiTessellator.prepare(7);
            KamiTessellator.drawBoundingBoxBlockPos(this.targetBlock, 1.0f, 5, 5, 255, 255);
        }
        KamiTessellator.release();
    }

    public List<EntityPlayer> getTargets() {
        return BedAura.mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).sorted(Comparator.comparing(e -> Float.valueOf(BedAura.mc.player.getDistance((Entity)e)))).collect(Collectors.toList());
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        boolean shouldSneak;
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = this.facing;
        boolean bl = shouldSneak = !BedAura.mc.player.isSneaking();
        if (shouldSneak) {
            BedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        BlocksUtils.faceVectorPacketInstant(hitVec);
        BedAura.mc.playerController.processRightClickBlock(BedAura.mc.player, BedAura.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        BedAura.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (shouldSneak) {
            BedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        BedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
    }

    private boolean switchHandToItemIfNeed(Item toItem) {
        if (BedAura.mc.player.getHeldItemMainhand().getItem() == toItem || BedAura.mc.player.getHeldItemOffhand().getItem() == toItem) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = BedAura.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || stack.getItem() != toItem) continue;
            BedAura.mc.player.inventory.currentItem = i;
            BedAura.mc.playerController.updateController();
            return true;
        }
        return true;
    }
}

