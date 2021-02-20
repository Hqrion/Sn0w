//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.GameType
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.exploits.NoBreakAnimation;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;

@Module.Info(name="Holefill", category=Module.Category.COMBAT)
public class Holefill
extends Module {
    private int totalTicksRunning = 0;
    private ArrayList<BlockPos> holes = new ArrayList();
    private List<Block> whiteList = Arrays.asList(Blocks.OBSIDIAN);
    BlockPos pos;
    private int waitCounter;
    private Setting<Integer> range = this.register(Settings.integerBuilder("range").withMinimum(1).withValue(4).withMaximum(6).build());
    private Setting<Integer> yRange = this.register(Settings.integerBuilder("yRange").withMinimum(1).withValue(4).withMaximum(6).build());
    private Setting<Boolean> chat = this.register(Settings.b("chat", false));
    private Setting<Boolean> rotate = this.register(Settings.b("rotate", false));
    private Setting<Boolean> noGlitchBlocks = this.register(Settings.b("NoGlitchBlocks", true));
    private Setting<Boolean> triggerable = this.register(Settings.b("Triggerable", true));
    private Setting<Integer> timeoutTicks = this.register(Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(1).withMaximum(100).withVisibility(b -> this.triggerable.getValue()).build());

    @Override
    public void onUpdate() {
        if (this.triggerable.getValue().booleanValue() && this.totalTicksRunning >= this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
            return;
        }
        ++this.totalTicksRunning;
        this.holes = new ArrayList();
        Iterable blocks = BlockPos.getAllInBox((BlockPos)Holefill.mc.player.getPosition().add(-this.range.getValue().intValue(), -this.yRange.getValue().intValue(), -this.range.getValue().intValue()), (BlockPos)Holefill.mc.player.getPosition().add(this.range.getValue().intValue(), this.yRange.getValue().intValue(), this.range.getValue().intValue()));
        for (BlockPos pos : blocks) {
            boolean solidNeighbours;
            if (Holefill.mc.world.getBlockState(pos).getMaterial().blocksMovement() || Holefill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement() || !(solidNeighbours = Holefill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | Holefill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN && Holefill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | Holefill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN && Holefill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | Holefill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN && Holefill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | Holefill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN && Holefill.mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && Holefill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && Holefill.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR)) continue;
            this.holes.add(pos);
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Holefill.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !this.whiteList.contains(block = ((ItemBlock)stack.getItem()).getBlock())) continue;
            newSlot = i;
            break;
        }
        if (newSlot == -1) {
            return;
        }
        int oldSlot = Holefill.mc.player.inventory.currentItem;
        Holefill.mc.player.inventory.currentItem = newSlot;
        this.holes.forEach(this::place);
        Holefill.mc.player.inventory.currentItem = oldSlot;
    }

    @Override
    public void onEnable() {
        if (Holefill.mc.player != null && this.chat.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
    }

    @Override
    public void onDisable() {
        Command.toggle_message(this);
    }

    private void place(BlockPos blockPos) {
        for (Entity entity : Holefill.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))) {
            if (!(entity instanceof EntityLivingBase)) continue;
            return;
        }
        Holefill.placeBlockScaffold(blockPos, this.rotate.getValue());
        ++this.waitCounter;
    }

    private void placeBlock(BlockPos pos) {
        Block block = Holefill.mc.world.getBlockState(pos).getBlock();
        if (block instanceof BlockAir || !(block instanceof BlockLiquid)) {
            // empty if block
        }
        for (Entity entity : Holefill.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && entity instanceof EntityXPOrb) continue;
        }
        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);
        if (side == null) {
            // empty if block
        }
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            // empty if block
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = Holefill.mc.world.getBlockState(neighbour).getBlock();
        if (this.noGlitchBlocks.getValue().booleanValue() && !Holefill.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            Holefill.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
            if (ModuleManager.getModuleByName("NoBreakAnimation").isEnabled()) {
                ((NoBreakAnimation)ModuleManager.getModuleByName("NoBreakAnimation")).resetMining();
            }
        }
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        Vec3d eyesPos = new Vec3d(Holefill.mc.player.posX, Holefill.mc.player.posY + (double)Holefill.mc.player.getEyeHeight(), Holefill.mc.player.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(neighbor)) continue;
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (rotate) {
                Holefill.faceVectorPacketInstant(hitVec);
            }
            Holefill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Holefill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            Holefill.processRightClickBlock(neighbor, side2, hitVec);
            Holefill.mc.player.swingArm(EnumHand.MAIN_HAND);
            Holefill.mc.rightClickDelayTimer = 0;
            Holefill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Holefill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            return true;
        }
        return false;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        Holefill.getPlayerController().processRightClickBlock(Holefill.mc.player, Holefill.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = Holefill.getNeededRotations2(vec);
        Holefill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Holefill.mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = Holefill.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Holefill.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - Holefill.mc.player.rotationYaw)), Holefill.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - Holefill.mc.player.rotationPitch))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(Holefill.mc.player.posX, Holefill.mc.player.posY + (double)Holefill.mc.player.getEyeHeight(), Holefill.mc.player.posZ);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(Holefill.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return Holefill.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    private static PlayerControllerMP getPlayerController() {
        return Holefill.mc.playerController;
    }
}

