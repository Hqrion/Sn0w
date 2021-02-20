//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemPiston
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemPiston;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Hacker Piston", category=Module.Category.COMBAT, description="hacks the hacker then piston the crystal")
public class HackerPiston
extends Module {
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private String stagething;
    private int EndCrystal;
    private int ShulkerSlot;
    private int GamerSLot;
    private int playerHotbarSlot = -1;
    private BlockPos placeTarget;
    private BlockPos placeTarget2;
    private BlockPos placeTarget3;
    private boolean active;
    private int beds;
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", false));
    private Setting<Boolean> Forwards = this.register(Settings.b("Forwards", false));
    private boolean isSneaking;
    float timer = 100.0f;

    @Override
    public void onUpdate() {
        if (this.timer == 0.0f) {
            this.timer = 100.0f;
        }
        this.timer -= 1.0f;
        if (HackerPiston.mc.player != null) {
            for (Entity entity : HackerPiston.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistance((Entity)HackerPiston.mc.player) < 4.5f)) continue;
                HackerPiston.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
            }
        }
        if (this.timer < 25.0f) {
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        ArrayList<BlockPos> targetPos = new ArrayList<BlockPos>();
        this.EndCrystal = -1;
        this.ShulkerSlot = -1;
        this.GamerSLot = -1;
        this.isSneaking = false;
        this.placeTarget = null;
        this.placeTarget2 = null;
        this.placeTarget3 = null;
        for (int x = 0; x <= 8; ++x) {
            Item item = HackerPiston.mc.player.inventory.getStackInSlot(x).getItem();
            if (item == Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK)) {
                this.ShulkerSlot = x;
                continue;
            }
            if (item instanceof ItemPiston) {
                this.GamerSLot = x;
                continue;
            }
            if (!(item instanceof ItemEndCrystal)) continue;
            this.EndCrystal = x;
        }
        for (EntityPlayer player : HackerPiston.mc.world.playerEntities) {
            targetPos.add(player.getPosition());
        }
        RayTraceResult lookingAt = Minecraft.getMinecraft().objectMouseOver;
        if (this.EndCrystal != -1 && this.ShulkerSlot != -1 && this.GamerSLot != -1) {
            if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) {
                this.placeTarget = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up();
                this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(1, 0, 0);
                this.placeTarget3 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(2, 0, 0);
                if (HackerPiston.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
                    this.placeTarget = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(0, 0, 1);
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(1, 0, 0);
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(2, 0, 0);
                    HackerPiston.mc.player.inventory.currentItem = this.GamerSLot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget2), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.ShulkerSlot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget3), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.EndCrystal;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
                }
                if (HackerPiston.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
                    this.placeTarget = HackerPiston.mc.objectMouseOver.getBlockPos().up();
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(0, 0, -1);
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(0, 0, -2);
                    HackerPiston.mc.player.inventory.currentItem = this.GamerSLot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget2), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.ShulkerSlot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget3), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.EndCrystal;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
                }
                if (HackerPiston.mc.player.getHorizontalFacing().equals((Object)EnumFacing.EAST)) {
                    this.placeTarget = HackerPiston.mc.objectMouseOver.getBlockPos().up();
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(0, 0, 1);
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(0, 0, 2);
                    HackerPiston.mc.player.inventory.currentItem = this.GamerSLot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget2), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.ShulkerSlot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget3), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.EndCrystal;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
                }
                if (HackerPiston.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
                    this.placeTarget = HackerPiston.mc.objectMouseOver.getBlockPos().up();
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(-1, 0, 0);
                    this.placeTarget2 = ((BlockPos)targetPos.get(0)).offset(HackerPiston.mc.player.getHorizontalFacing().getOpposite()).up().add(-2, 0, 0);
                    HackerPiston.mc.player.inventory.currentItem = this.GamerSLot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget2), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.ShulkerSlot;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget3), EnumFacing.DOWN);
                    HackerPiston.mc.player.inventory.currentItem = this.EndCrystal;
                    this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
                }
            }
        } else {
            Command.sendChatMessage("Not Enough Resources!");
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        if (!HackerPiston.mc.player.isSneaking()) {
            HackerPiston.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HackerPiston.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!this.isSneaking) {
            HackerPiston.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HackerPiston.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (this.rotate.getValue().booleanValue()) {
            float[] angle = BlockInteractionHelper.calcAngle(HackerPiston.mc.player.getPositionEyes(mc.getRenderPartialTicks()), hitVec);
            HackerPiston.setPlayerRotations(angle[0], angle[1]);
        }
        HackerPiston.mc.playerController.processRightClickBlock(HackerPiston.mc.player, HackerPiston.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        HackerPiston.mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    public static void setPlayerRotations(float yaw, float pitch) {
        HackerPiston.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, HackerPiston.mc.player.onGround));
    }
}

