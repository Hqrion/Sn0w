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
    private List<Block> whiteList = Arrays.asList(Blocks.field_150343_Z);
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
        Iterable blocks = BlockPos.func_177980_a((BlockPos)Holefill.mc.field_71439_g.func_180425_c().func_177982_a(-this.range.getValue().intValue(), -this.yRange.getValue().intValue(), -this.range.getValue().intValue()), (BlockPos)Holefill.mc.field_71439_g.func_180425_c().func_177982_a(this.range.getValue().intValue(), this.yRange.getValue().intValue(), this.range.getValue().intValue()));
        for (BlockPos pos : blocks) {
            boolean solidNeighbours;
            if (Holefill.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76230_c() || Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a().func_76230_c() || !(solidNeighbours = Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h | Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h | Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h | Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h | Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 0)).func_185904_a() == Material.field_151579_a && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a() == Material.field_151579_a && Holefill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a() == Material.field_151579_a)) continue;
            this.holes.add(pos);
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Holefill.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || !this.whiteList.contains(block = ((ItemBlock)stack.func_77973_b()).func_179223_d())) continue;
            newSlot = i;
            break;
        }
        if (newSlot == -1) {
            return;
        }
        int oldSlot = Holefill.mc.field_71439_g.field_71071_by.field_70461_c;
        Holefill.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
        this.holes.forEach(this::place);
        Holefill.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
    }

    @Override
    public void onEnable() {
        if (Holefill.mc.field_71439_g != null && this.chat.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
    }

    @Override
    public void onDisable() {
        Command.toggle_message(this);
    }

    private void place(BlockPos blockPos) {
        for (Entity entity : Holefill.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(blockPos))) {
            if (!(entity instanceof EntityLivingBase)) continue;
            return;
        }
        Holefill.placeBlockScaffold(blockPos, this.rotate.getValue());
        ++this.waitCounter;
    }

    private void placeBlock(BlockPos pos) {
        Block block = Holefill.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (block instanceof BlockAir || !(block instanceof BlockLiquid)) {
            // empty if block
        }
        for (Entity entity : Holefill.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && entity instanceof EntityXPOrb) continue;
        }
        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);
        if (side == null) {
            // empty if block
        }
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            // empty if block
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        Block neighbourBlock = Holefill.mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
        if (this.noGlitchBlocks.getValue().booleanValue() && !Holefill.mc.field_71442_b.func_178889_l().equals((Object)GameType.CREATIVE)) {
            Holefill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
            if (ModuleManager.getModuleByName("NoBreakAnimation").isEnabled()) {
                ((NoBreakAnimation)ModuleManager.getModuleByName("NoBreakAnimation")).resetMining();
            }
        }
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        Vec3d eyesPos = new Vec3d(Holefill.mc.field_71439_g.field_70165_t, Holefill.mc.field_71439_g.field_70163_u + (double)Holefill.mc.field_71439_g.func_70047_e(), Holefill.mc.field_71439_g.field_70161_v);
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.func_177972_a(side);
            EnumFacing side2 = side.func_176734_d();
            if (!BlockInteractionHelper.canBeClicked(neighbor)) continue;
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
            if (rotate) {
                Holefill.faceVectorPacketInstant(hitVec);
            }
            Holefill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Holefill.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            Holefill.processRightClickBlock(neighbor, side2, hitVec);
            Holefill.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            Holefill.mc.field_71467_ac = 0;
            Holefill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Holefill.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            return true;
        }
        return false;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        Holefill.getPlayerController().func_187099_a(Holefill.mc.field_71439_g, Holefill.mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = Holefill.getNeededRotations2(vec);
        Holefill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Holefill.mc.field_71439_g.field_70122_E));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = Holefill.getEyesPos();
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Holefill.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g((float)(yaw - Holefill.mc.field_71439_g.field_70177_z)), Holefill.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g((float)(pitch - Holefill.mc.field_71439_g.field_70125_A))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(Holefill.mc.field_71439_g.field_70165_t, Holefill.mc.field_71439_g.field_70163_u + (double)Holefill.mc.field_71439_g.func_70047_e(), Holefill.mc.field_71439_g.field_70161_v);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(Holefill.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return Holefill.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }

    private static PlayerControllerMP getPlayerController() {
        return Holefill.mc.field_71442_b;
    }
}

