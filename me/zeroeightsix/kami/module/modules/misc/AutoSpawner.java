/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockSoulSand
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.List;
import java.util.Random;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="AutoSpawner", category=Module.Category.MISC, description="Automatically spawns Withers, Iron Golems and Snowmen")
public class AutoSpawner
extends Module {
    private static boolean isSneaking;
    private Setting<UseMode> useMode = this.register(Settings.e("UseMode", UseMode.SPAM));
    private Setting<Boolean> party = this.register(Settings.b("Party", false));
    private Setting<Boolean> partyWithers = this.register(Settings.booleanBuilder("Withers").withValue(false).withVisibility(v -> this.party.getValue()).build());
    private Setting<EntityMode> entityMode = this.register(Settings.enumBuilder(EntityMode.class).withName("EntityMode").withValue(EntityMode.SNOW).withVisibility(v -> this.party.getValue() == false).build());
    private Setting<Float> placeRange = this.register(Settings.floatBuilder("PlaceRange").withMinimum(Float.valueOf(2.0f)).withValue(Float.valueOf(3.5f)).withMaximum(Float.valueOf(10.0f)).build());
    private Setting<Integer> delay = this.register(Settings.integerBuilder("Delay").withMinimum(12).withValue(20).withMaximum(100).withVisibility(v -> this.useMode.getValue().equals((Object)UseMode.SPAM)).build());
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private Setting<Boolean> debug = this.register(Settings.b("Debug", false));
    private BlockPos placeTarget;
    private boolean rotationPlaceableX;
    private boolean rotationPlaceableZ;
    private int bodySlot;
    private int headSlot;
    private int buildStage;
    private int delayStep;

    private static void placeBlock(BlockPos pos, boolean rotate) {
        EnumFacing side = AutoSpawner.getPlaceableSide(pos);
        if (side == null) {
            return;
        }
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        Block neighbourBlock = AutoSpawner.mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
        if (!isSneaking && (BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock))) {
            AutoSpawner.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }
        if (rotate) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        AutoSpawner.mc.field_71442_b.func_187099_a(AutoSpawner.mc.field_71439_g, AutoSpawner.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoSpawner.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        AutoSpawner.mc.field_71467_ac = 4;
    }

    private static EnumFacing getPlaceableSide(BlockPos pos) {
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState blockState;
            BlockPos neighbour = pos.func_177972_a(side);
            if (!AutoSpawner.mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(AutoSpawner.mc.field_71441_e.func_180495_p(neighbour), false) || (blockState = AutoSpawner.mc.field_71441_e.func_180495_p(neighbour)).func_185904_a().func_76222_j() || blockState.func_177230_c() instanceof BlockTallGrass || blockState.func_177230_c() instanceof BlockDeadBush) continue;
            return side;
        }
        return null;
    }

    @Override
    protected void onEnable() {
        if (AutoSpawner.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        this.buildStage = 1;
        this.delayStep = 1;
        if (this.debug.getValue().booleanValue()) {
            if (this.party.getValue().booleanValue()) {
                if (this.partyWithers.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoSpawner] Active, mode: PARTY WITHER");
                }
                Command.sendChatMessage("[AutoSpawner] Active, mode: PARTY");
            }
            Command.sendChatMessage("[AutoSpawner] Active, mode: " + this.entityMode.getValue().toString());
        }
    }

    private boolean checkBlocksInHotbar() {
        this.headSlot = -1;
        this.bodySlot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a) continue;
            if (this.entityMode.getValue().equals((Object)EntityMode.WITHER)) {
                if (stack.func_77973_b() == Items.field_151144_bL && stack.func_77952_i() == 1) {
                    if (AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a < 3) continue;
                    this.headSlot = i;
                    continue;
                }
                if (!(stack.func_77973_b() instanceof ItemBlock)) continue;
                block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockSoulSand && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a >= 4) {
                    this.bodySlot = i;
                }
            }
            if (this.entityMode.getValue().equals((Object)EntityMode.IRON)) {
                if (!(stack.func_77973_b() instanceof ItemBlock)) continue;
                block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if ((block == Blocks.field_150428_aP || block == Blocks.field_150423_aK) && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a >= 1) {
                    this.headSlot = i;
                }
                if (block == Blocks.field_150339_S && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a >= 4) {
                    this.bodySlot = i;
                }
            }
            if (!this.entityMode.getValue().equals((Object)EntityMode.SNOW) || !(stack.func_77973_b() instanceof ItemBlock)) continue;
            block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if ((block == Blocks.field_150428_aP || block == Blocks.field_150423_aK) && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a >= 1) {
                this.headSlot = i;
            }
            if (block != Blocks.field_150433_aE || AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a((int)i).field_77994_a < 2) continue;
            this.bodySlot = i;
        }
        return this.bodySlot != -1 && this.headSlot != -1;
    }

    private boolean testStructure() {
        if (this.entityMode.getValue().equals((Object)EntityMode.WITHER)) {
            return this.testWitherStructure();
        }
        if (this.entityMode.getValue().equals((Object)EntityMode.IRON)) {
            return this.testIronGolemStructure();
        }
        if (this.entityMode.getValue().equals((Object)EntityMode.SNOW)) {
            return this.testSnowGolemStructure();
        }
        return false;
    }

    private boolean testWitherStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (AutoSpawner.getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (BlockPos pos : BodyParts.bodyBase) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            noRotationPlaceable = false;
        }
        for (BlockPos pos : BodyParts.ArmsX) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) && !this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) continue;
            this.rotationPlaceableX = false;
        }
        for (BlockPos pos : BodyParts.ArmsZ) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) && !this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) continue;
            this.rotationPlaceableZ = false;
        }
        for (BlockPos pos : BodyParts.headsX) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            this.rotationPlaceableX = false;
        }
        for (BlockPos pos : BodyParts.headsZ) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            this.rotationPlaceableZ = false;
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }

    private boolean testIronGolemStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (AutoSpawner.getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (BlockPos pos : BodyParts.bodyBase) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            noRotationPlaceable = false;
        }
        for (BlockPos pos : BodyParts.ArmsX) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) && !this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) continue;
            this.rotationPlaceableX = false;
        }
        for (BlockPos pos : BodyParts.ArmsZ) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) && !this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) continue;
            this.rotationPlaceableZ = false;
        }
        for (BlockPos pos : BodyParts.head) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            noRotationPlaceable = false;
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }

    private boolean testSnowGolemStructure() {
        boolean noRotationPlaceable = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (AutoSpawner.getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (BlockPos pos : BodyParts.bodyBase) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            noRotationPlaceable = false;
        }
        for (BlockPos pos : BodyParts.head) {
            if (!this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) continue;
            noRotationPlaceable = false;
        }
        return !isShitGrass && noRotationPlaceable;
    }

    @Override
    public void onUpdate() {
        if (AutoSpawner.mc.field_71439_g == null) {
            return;
        }
        if (this.buildStage == 1) {
            isSneaking = false;
            this.rotationPlaceableX = false;
            this.rotationPlaceableZ = false;
            if (this.party.getValue().booleanValue()) {
                Random random = new Random();
                int partyMode = this.partyWithers.getValue() != false ? random.nextInt(3) : random.nextInt(2);
                if (partyMode == 0) {
                    this.entityMode.setValue(EntityMode.SNOW);
                } else if (partyMode == 1) {
                    this.entityMode.setValue(EntityMode.IRON);
                } else if (partyMode == 2) {
                    this.entityMode.setValue(EntityMode.WITHER);
                }
            }
            if (!this.checkBlocksInHotbar()) {
                if (!this.party.getValue().booleanValue()) {
                    if (this.debug.getValue().booleanValue()) {
                        Command.sendChatMessage("[AutoSpawner] " + ChatFormatting.RED.toString() + "Blocks missing for: " + ChatFormatting.RESET.toString() + this.entityMode.getValue().toString() + ChatFormatting.RED.toString() + ", disabling.");
                    }
                    this.disable();
                }
                return;
            }
            List<BlockPos> blockPosList = BlockInteractionHelper.getSphere(AutoSpawner.mc.field_71439_g.func_180425_c().func_177977_b(), this.placeRange.getValue().floatValue(), this.placeRange.getValue().intValue(), false, true, 0);
            boolean noPositionInArea = true;
            for (BlockPos pos : blockPosList) {
                this.placeTarget = pos.func_177977_b();
                if (!this.testStructure()) continue;
                noPositionInArea = false;
                break;
            }
            if (noPositionInArea) {
                if (this.useMode.getValue().equals((Object)UseMode.SINGLE)) {
                    if (this.debug.getValue().booleanValue()) {
                        Command.sendChatMessage("[AutoSpawner] " + ChatFormatting.RED.toString() + "Position not valid, disabling.");
                    }
                    this.disable();
                }
                return;
            }
            AutoSpawner.mc.field_71439_g.field_71071_by.field_70461_c = this.bodySlot;
            for (BlockPos pos : BodyParts.bodyBase) {
                AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
            }
            if (this.entityMode.getValue().equals((Object)EntityMode.WITHER) || this.entityMode.getValue().equals((Object)EntityMode.IRON)) {
                if (this.rotationPlaceableX) {
                    for (BlockPos pos : BodyParts.ArmsX) {
                        AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
                    }
                } else if (this.rotationPlaceableZ) {
                    for (BlockPos pos : BodyParts.ArmsZ) {
                        AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
                    }
                }
            }
            this.buildStage = 2;
        } else if (this.buildStage == 2) {
            AutoSpawner.mc.field_71439_g.field_71071_by.field_70461_c = this.headSlot;
            if (this.entityMode.getValue().equals((Object)EntityMode.WITHER)) {
                if (this.rotationPlaceableX) {
                    for (BlockPos pos : BodyParts.headsX) {
                        AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
                    }
                } else if (this.rotationPlaceableZ) {
                    for (BlockPos pos : BodyParts.headsZ) {
                        AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
                    }
                }
            }
            if (this.entityMode.getValue().equals((Object)EntityMode.IRON) || this.entityMode.getValue().equals((Object)EntityMode.SNOW)) {
                for (BlockPos pos : BodyParts.head) {
                    AutoSpawner.placeBlock(this.placeTarget.func_177971_a((Vec3i)pos), this.rotate.getValue());
                }
            }
            if (isSneaking) {
                AutoSpawner.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                isSneaking = false;
            }
            if (this.useMode.getValue().equals((Object)UseMode.SINGLE)) {
                this.disable();
            }
            this.buildStage = 3;
        } else if (this.buildStage == 3) {
            if (this.delayStep < this.delay.getValue()) {
                ++this.delayStep;
            } else {
                this.delayStep = 1;
                this.buildStage = 1;
            }
        }
    }

    private boolean placingIsBlocked(BlockPos pos) {
        Block block = AutoSpawner.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (!(block instanceof BlockAir)) {
            return true;
        }
        for (Entity entity : AutoSpawner.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
            return true;
        }
        return false;
    }

    @Override
    public String getHudInfo() {
        if (this.party.getValue().booleanValue()) {
            if (this.partyWithers.getValue().booleanValue()) {
                return "PARTY WITHER";
            }
            return "PARTY";
        }
        return this.entityMode.getValue().toString();
    }

    private static class BodyParts {
        private static final BlockPos[] bodyBase = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(0, 2, 0)};
        private static final BlockPos[] ArmsX = new BlockPos[]{new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0)};
        private static final BlockPos[] ArmsZ = new BlockPos[]{new BlockPos(0, 2, -1), new BlockPos(0, 2, 1)};
        private static final BlockPos[] headsX = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0)};
        private static final BlockPos[] headsZ = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1)};
        private static final BlockPos[] head = new BlockPos[]{new BlockPos(0, 3, 0)};

        private BodyParts() {
        }
    }

    private static enum EntityMode {
        SNOW,
        IRON,
        WITHER;

    }

    private static enum UseMode {
        SINGLE,
        SPAM;

    }
}

