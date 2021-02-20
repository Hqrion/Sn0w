//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlocksUtils;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

@Module.Info(name="BedAura TEST", category=Module.Category.COMBAT)
public class BedAuraRewrite
extends Module {
    private Setting<Boolean> announceusage;
    private Setting<Boolean> autobedswitch;
    private Setting<Boolean> antiSuicide;
    private Setting<Boolean> refill;
    private Setting<Integer> antiSuicideHlth = this.register(Settings.integerBuilder("Anti Suicide Health").withMinimum(0).withMaximum(36).withValue(16).withVisibility(b -> this.antiSuicide.getValue()).build());
    private Setting<Integer> range = this.register(Settings.integerBuilder("Range").withMinimum(0).withMaximum(10).withValue(6));
    BlockPos target;

    public BedAuraRewrite() {
        this.announceusage = this.register(Settings.b("Announce Usage", false));
        this.autobedswitch = this.register(Settings.b("Auto Switch", false));
        this.antiSuicide = this.register(Settings.b("AntiSuicide", true));
        this.refill = this.register(Settings.b("Refill", false));
    }

    @Override
    public void onUpdate() {
        if (BedAuraRewrite.mc.player.getHealth() < (float)this.antiSuicideHlth.getValue().intValue() && this.antiSuicide.getValue().booleanValue()) {
            this.disable();
        }
        for (EntityPlayer player : this.getTargets()) {
            this.target = new BlockPos(player.posX, player.posY, player.posZ);
            if (BedAuraRewrite.mc.player.getHeldItemMainhand().getItem() != ItemBlock.getItemById((int)355) || BedAuraRewrite.mc.world.getBlockState(this.target.up().up()) == Blocks.AIR) continue;
            this.placeBlock(this.target.up(), EnumFacing.DOWN);
        }
        if (BedAuraRewrite.mc.player.dimension != 0) {
            BedAuraRewrite.mc.world.loadedTileEntityList.stream().filter(e -> e instanceof TileEntityBed).filter(e -> BedAuraRewrite.mc.player.getPosition().getDistance(e.getPos().x, e.getPos().y, e.getPos().z) <= (double)this.range.getValue().intValue()).map(entity -> (TileEntityBed)entity).min(Comparator.comparing(e -> BedAuraRewrite.mc.player.getPosition().getDistance(e.getPos().x, e.getPos().y, e.getPos().z))).ifPresent(bed -> BedAuraRewrite.mc.playerController.processRightClickBlock(BedAuraRewrite.mc.player, BedAuraRewrite.mc.world, bed.getPos(), EnumFacing.UP, new Vec3d((double)bed.getPos().getX(), (double)bed.getPos().getY(), (double)bed.getPos().getZ()), EnumHand.MAIN_HAND));
        } else {
            this.disable();
        }
        if (this.refill.getValue().booleanValue()) {
            int slot = -1;
            for (int i = 0; i < 9; ++i) {
                if (BedAuraRewrite.mc.player.inventory.getStackInSlot(i) != ItemStack.EMPTY) continue;
                slot = i;
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.autobedswitch.getValue().booleanValue()) {
            this.switchHandToItemIfNeed(ItemBlock.getItemById((int)355));
        }
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("Bed Aura Rewrite" + ChatFormatting.BLUE.toString() + " Enabled");
        }
    }

    @Override
    public void onDisable() {
        if (this.announceusage.getValue().booleanValue()) {
            Command.sendChatMessage("Bed Aura Rewrite" + ChatFormatting.BLUE.toString() + " Disabled");
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        boolean shouldSneak;
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        boolean bl = shouldSneak = !BedAuraRewrite.mc.player.isSneaking();
        if (shouldSneak) {
            BedAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAuraRewrite.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        BlocksUtils.faceVectorPacketInstant(hitVec);
        BedAuraRewrite.mc.playerController.processRightClickBlock(BedAuraRewrite.mc.player, BedAuraRewrite.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        BedAuraRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (shouldSneak) {
            BedAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAuraRewrite.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        BedAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
    }

    public List<EntityPlayer> getTargets() {
        return BedAuraRewrite.mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).sorted(Comparator.comparing(e -> Float.valueOf(BedAuraRewrite.mc.player.getDistance((Entity)e)))).collect(Collectors.toList());
    }

    private boolean switchHandToItemIfNeed(Item toItem) {
        if (BedAuraRewrite.mc.player.getHeldItemMainhand().getItem() == toItem || BedAuraRewrite.mc.player.getHeldItemOffhand().getItem() == toItem) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = BedAuraRewrite.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || stack.getItem() != toItem) continue;
            BedAuraRewrite.mc.player.inventory.currentItem = i;
            BedAuraRewrite.mc.playerController.updateController();
            return true;
        }
        return true;
    }
}

