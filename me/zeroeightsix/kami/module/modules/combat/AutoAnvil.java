//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockAnvil
 *  net.minecraft.block.BlockButton
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.BlockPressurePlate
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.util.ArrayList;
import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockssUtils;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Auto Anvil", category=Module.Category.COMBAT)
public class AutoAnvil
extends Module {
    private Setting<Double> range = this.register(Settings.doubleBuilder("Range").withMinimum(3.5).withValue(5.0).withMaximum(32.0).build());
    private Setting<Integer> blocksPerTick = this.register(Settings.integerBuilder("BlocksPerTick").withMinimum(1).withValue(2).withMaximum(23).build());
    private Setting<Integer> tickDelay = this.register(Settings.integerBuilder("TickDelay").withMinimum(0).withValue(0).withMaximum(10).build());
    private Setting<Boolean> fastAnvil = this.register(Settings.b("FastAnvil", true));
    private Setting<Integer> enemyRange = this.register(Settings.integerBuilder("Enemy Range9").withMinimum(0).withValue(5).withMaximum(10).build());
    private Setting<Integer> hDistance = this.register(Settings.integerBuilder("hDistance").withMinimum(0).withValue(5).withMaximum(10).build());
    private Setting<Integer> minH = this.register(Settings.integerBuilder("minH").withMinimum(0).withValue(5).withMaximum(10).build());
    private Setting<Integer> decrease = this.register(Settings.integerBuilder("decrease").withMinimum(0).withValue(5).withMaximum(10).build());
    private Setting<type> anvilMode = this.register(Settings.e("Mode", type.Pick));
    private Setting<Boolean> chatMsg = this.register(Settings.b("Chat MSG", true));
    private Setting<Boolean> antiCrystal = this.register(Settings.b("AntiCrystal", true));
    private Setting<Boolean> FailStop = this.register(Settings.b("Fail Stop", true));
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private String lastTickTargetName;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int delayStep = 0;
    private boolean isSneaking = false;
    private boolean firstRun = false;
    private boolean noMaterials = false;
    private boolean hasMoved = false;
    private boolean isHole = true;
    private boolean enoughSpace = true;
    private int oldSlot = -1;
    private int[] slot_mat = new int[]{-1, -1, -1, -1};
    private double[] enemyCoords;
    Double[][] sur_block;
    private int noKick;
    int[][] model = new int[][]{{1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1}};
    private int blocksPlaced = 0;
    private int delayTimeTicks = 0;
    private int offsetSteps = 0;
    private boolean pick_d = false;
    private EntityPlayer closestTarget;
    private static ArrayList<Vec3d> to_place = new ArrayList();

    @Override
    public void onEnable() {
        if (this.anvilMode.getValue().equals((Object)type.Pick)) {
            this.pick_d = true;
        }
        this.blocksPlaced = 0;
        this.isHole = true;
        this.hasMoved = false;
        this.firstRun = true;
        this.slot_mat = new int[]{-1, -1, -1, -1};
        to_place = new ArrayList();
        if (AutoAnvil.mc.player == null) {
            this.disable();
            return;
        }
        if (this.chatMsg.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
        this.oldSlot = AutoAnvil.mc.player.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        if (AutoAnvil.mc.player == null) {
            return;
        }
        if (this.chatMsg.getValue().booleanValue()) {
            if (this.noMaterials) {
                Command.sendChatMessage("No Materials Detected... AutoAnvil turned OFF!");
            } else if (!this.isHole) {
                Command.sendChatMessage("The enemy is not in a hole... AutoAnvil turned OFF!");
            } else if (!this.enoughSpace) {
                Command.sendChatMessage("Not enough space... AutoAnvil turned OFF!");
            } else if (this.hasMoved) {
                Command.sendChatMessage("He moved away from the hole... AutoAnvil turned OFF!");
            } else {
                Command.toggle_message(this);
            }
        }
        if (this.isSneaking) {
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        if (this.oldSlot != AutoAnvil.mc.player.inventory.currentItem && this.oldSlot != -1) {
            AutoAnvil.mc.player.inventory.currentItem = this.oldSlot;
            this.oldSlot = -1;
        }
        this.noMaterials = false;
        this.firstRun = true;
    }

    @Override
    public void onUpdate() {
        if (AutoAnvil.mc.player == null) {
            this.disable();
            return;
        }
        if (this.firstRun) {
            this.closestTarget = this.findClosestTarget();
            if (this.closestTarget == null) {
                return;
            }
            this.firstRun = false;
            if (this.getMaterialsSlot()) {
                if (this.is_in_hole()) {
                    this.enemyCoords = new double[]{this.closestTarget.posX, this.closestTarget.posY, this.closestTarget.posZ};
                    this.enoughSpace = this.createStructure();
                } else {
                    this.isHole = false;
                }
            } else {
                this.noMaterials = true;
            }
        } else {
            if (this.delayTimeTicks < this.tickDelay.getValue()) {
                ++this.delayTimeTicks;
                return;
            }
            this.delayTimeTicks = 0;
        }
        this.blocksPlaced = 0;
        if (this.noMaterials || !this.isHole || !this.enoughSpace || this.hasMoved) {
            this.disable();
            return;
        }
        this.noKick = 0;
        while (this.blocksPlaced <= this.blocksPerTick.getValue()) {
            int maxSteps = to_place.size();
            if (this.offsetSteps >= maxSteps) {
                this.offsetSteps = 0;
                break;
            }
            BlockPos offsetPos = new BlockPos(to_place.get(this.offsetSteps));
            BlockPos targetPos = new BlockPos(this.closestTarget.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
            boolean tryPlacing = true;
            if (this.offsetSteps > 0 && this.offsetSteps < to_place.size() - 1) {
                for (Entity entity : AutoAnvil.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (!(entity instanceof EntityPlayer)) continue;
                    tryPlacing = false;
                    break;
                }
            }
            if (tryPlacing && this.placeBlock(targetPos, this.offsetSteps)) {
                ++this.blocksPlaced;
            }
            ++this.offsetSteps;
            if (!this.isSneaking) continue;
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
    }

    private boolean placeBlock(BlockPos pos, int step) {
        EnumFacing prova;
        int utilSlot;
        Block block = AutoAnvil.mc.world.getBlockState(pos).getBlock();
        EnumFacing side = BlockssUtils.getPlaceableSide(pos);
        if (step == to_place.size() - 1 && block instanceof BlockAnvil && side != null) {
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
            ++this.noKick;
        }
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        if (side == null) {
            return false;
        }
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!BlockssUtils.canBeClicked(neighbour)) {
            return false;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = AutoAnvil.mc.world.getBlockState(neighbour).getBlock();
        int n = step == 0 && this.anvilMode.getValue().equals((Object)type.Feet) ? 2 : (utilSlot = step == to_place.size() - 1 ? 1 : 0);
        if (AutoAnvil.mc.player.inventory.getStackInSlot(this.slot_mat[utilSlot]) != ItemStack.EMPTY) {
            if (AutoAnvil.mc.player.inventory.currentItem != this.slot_mat[utilSlot]) {
                AutoAnvil.mc.player.inventory.currentItem = this.slot_mat[utilSlot];
            }
        } else {
            return false;
        }
        if (!this.isSneaking && BlockssUtils.blackList.contains(neighbourBlock) || BlockssUtils.shulkerList.contains(neighbourBlock)) {
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        boolean stoppedAC = false;
        if (this.rotate.getValue().booleanValue()) {
            BlockssUtils.faceVectorPacketInstant(hitVec);
        }
        int bef = AutoAnvil.mc.rightClickDelayTimer;
        if (step == to_place.size() - 1) {
            EntityPlayer found = this.getPlayerFromName(this.closestTarget.gameProfile.getName());
            if (found == null || (int)found.posX != (int)this.enemyCoords[0] || (int)found.posZ != (int)this.enemyCoords[2]) {
                this.hasMoved = true;
                return false;
            }
            if (this.fastAnvil.getValue().booleanValue()) {
                AutoAnvil.mc.rightClickDelayTimer = 0;
            }
        }
        AutoAnvil.mc.playerController.processRightClickBlock(AutoAnvil.mc.player, AutoAnvil.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (this.fastAnvil.getValue().booleanValue() && step == to_place.size() - 1) {
            AutoAnvil.mc.rightClickDelayTimer = bef;
        }
        if (this.pick_d && step == to_place.size() - 1 && (prova = BlockssUtils.getPlaceableSide(new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]))) != null) {
            AutoAnvil.mc.player.inventory.currentItem = this.slot_mat[3];
            AutoAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
        }
        return true;
    }

    private EntityPlayer findClosestTarget() {
        List playerList = AutoAnvil.mc.world.playerEntities;
        EntityPlayer closestTarget_test = null;
        for (EntityPlayer entityPlayer : playerList) {
            if (entityPlayer == AutoAnvil.mc.player || Friends.isFriend(entityPlayer.getName()) || entityPlayer.isDead) continue;
            if (this.closestTarget == null && AutoAnvil.mc.player.getDistance((Entity)entityPlayer) <= (float)this.enemyRange.getValue().intValue()) {
                closestTarget_test = entityPlayer;
                continue;
            }
            if (this.closestTarget == null || !(AutoAnvil.mc.player.getDistance((Entity)entityPlayer) <= (float)this.enemyRange.getValue().intValue()) || !(AutoAnvil.mc.player.getDistance((Entity)entityPlayer) < AutoAnvil.mc.player.getDistance((Entity)this.closestTarget))) continue;
            closestTarget_test = entityPlayer;
        }
        return closestTarget_test;
    }

    private EntityPlayer getPlayerFromName(String name) {
        List playerList = AutoAnvil.mc.world.playerEntities;
        for (EntityPlayer entityPlayer : playerList) {
            if (!entityPlayer.gameProfile.getName().equals(name)) continue;
            return entityPlayer;
        }
        return null;
    }

    private boolean getMaterialsSlot() {
        boolean feet = false;
        boolean pick = false;
        if (this.anvilMode.getValue().equals((Object)type.Feet)) {
            feet = true;
        }
        if (this.anvilMode.getValue().equals((Object)type.Pick)) {
            pick = true;
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = AutoAnvil.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) continue;
            if (pick && stack.getItem() instanceof ItemPickaxe) {
                this.slot_mat[3] = i;
            }
            if (!(stack.getItem() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (block instanceof BlockObsidian) {
                this.slot_mat[0] = i;
                continue;
            }
            if (block instanceof BlockAnvil) {
                this.slot_mat[1] = i;
                continue;
            }
            if (!feet || !(block instanceof BlockPressurePlate) && !(block instanceof BlockButton)) continue;
            this.slot_mat[2] = i;
        }
        int count = 0;
        for (int val : this.slot_mat) {
            if (val == -1) continue;
            ++count;
        }
        return count - (feet || pick ? 1 : 0) == 2;
    }

    private boolean is_in_hole() {
        this.sur_block = new Double[][]{{this.closestTarget.posX + 1.0, this.closestTarget.posY, this.closestTarget.posZ}, {this.closestTarget.posX - 1.0, this.closestTarget.posY, this.closestTarget.posZ}, {this.closestTarget.posX, this.closestTarget.posY, this.closestTarget.posZ + 1.0}, {this.closestTarget.posX, this.closestTarget.posY, this.closestTarget.posZ - 1.0}};
        this.enemyCoords = new double[]{this.closestTarget.posX, this.closestTarget.posY, this.closestTarget.posZ};
        return !(this.get_block(this.sur_block[0][0], this.sur_block[0][1], this.sur_block[0][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[1][0], this.sur_block[1][1], this.sur_block[1][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[2][0], this.sur_block[2][1], this.sur_block[2][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[3][0], this.sur_block[3][1], this.sur_block[3][2]) instanceof BlockAir);
    }

    private boolean createStructure() {
        int incr;
        if (this.anvilMode.getValue().equals((Object)type.Feet)) {
            to_place.add(new Vec3d(0.0, 0.0, 0.0));
        }
        to_place.add(new Vec3d(1.0, 1.0, 0.0));
        to_place.add(new Vec3d(-1.0, 1.0, 0.0));
        to_place.add(new Vec3d(0.0, 1.0, 1.0));
        to_place.add(new Vec3d(0.0, 1.0, -1.0));
        to_place.add(new Vec3d(1.0, 2.0, 0.0));
        to_place.add(new Vec3d(-1.0, 2.0, 0.0));
        to_place.add(new Vec3d(0.0, 2.0, 1.0));
        to_place.add(new Vec3d(0.0, 2.0, -1.0));
        int hDistanceMod = this.hDistance.getValue();
        for (double distEnemy = (double)AutoAnvil.mc.player.getDistance((Entity)this.closestTarget); distEnemy > (double)this.decrease.getValue().intValue(); distEnemy -= (double)this.decrease.getValue().intValue()) {
            --hDistanceMod;
        }
        int add = (int)(AutoAnvil.mc.player.posY - this.closestTarget.posY);
        if (add > 1) {
            add = 2;
        }
        hDistanceMod = (int)((double)hDistanceMod + (AutoAnvil.mc.player.posY - this.closestTarget.posY));
        double min_found = Double.MAX_VALUE;
        double[] coords_blocks_min = new double[]{-1.0, -1.0, -1.0};
        int cor = -1;
        int i = 0;
        for (Double[] cord_b : this.sur_block) {
            double d;
            double[] coords_blocks_temp = new double[]{cord_b[0], cord_b[1], cord_b[2]};
            double distance_now = AutoAnvil.mc.player.getDistanceSq(new BlockPos(cord_b[0].doubleValue(), cord_b[1].doubleValue(), cord_b[2].doubleValue()));
            if (d < min_found) {
                min_found = distance_now;
                cor = i;
            }
            ++i;
        }
        boolean possible = false;
        for (incr = 1; this.get_block(this.enemyCoords[0], this.enemyCoords[1] + (double)incr, this.enemyCoords[2]) instanceof BlockAir && incr < hDistanceMod; ++incr) {
            if (!this.antiCrystal.getValue().booleanValue()) {
                to_place.add(new Vec3d((double)this.model[cor][0], (double)(this.model[cor][1] + incr), (double)this.model[cor][2]));
                continue;
            }
            for (int ij = 0; ij < 4; ++ij) {
                to_place.add(new Vec3d((double)this.model[ij][0], (double)(this.model[ij][1] + incr), (double)this.model[ij][2]));
            }
        }
        if (!(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + (double)incr, this.enemyCoords[2]) instanceof BlockAir)) {
            --incr;
        }
        if (incr >= this.minH.getValue()) {
            possible = true;
        }
        to_place.add(new Vec3d(0.0, (double)(this.model[cor][1] + incr - 1), 0.0));
        return possible;
    }

    private Block get_block(double x, double y, double z) {
        return AutoAnvil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    private static enum type {
        Pick,
        Feet,
        None;

    }
}

