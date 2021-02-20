//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.misc;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="AutoNomadHut", category=Module.Category.MISC, description="Builds a NomadHut. Stand still in the middle of a block!")
public class AutoNomadHut
extends Module {
    private final Vec3d[] surroundTargets = new Vec3d[]{new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 1.0), new Vec3d(2.0, 0.0, -1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(1.0, 0.0, 2.0), new Vec3d(-1.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(-1.0, 0.0, -2.0), new Vec3d(1.0, 0.0, -2.0), new Vec3d(2.0, 1.0, -1.0), new Vec3d(2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, 0.0), new Vec3d(-2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(-1.0, 1.0, 2.0), new Vec3d(0.0, 1.0, -2.0), new Vec3d(1.0, 1.0, -2.0), new Vec3d(-1.0, 1.0, -2.0), new Vec3d(2.0, 2.0, -1.0), new Vec3d(2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 2.0), new Vec3d(-1.0, 2.0, 2.0), new Vec3d(1.0, 2.0, -2.0), new Vec3d(-1.0, 2.0, -2.0), new Vec3d(2.0, 3.0, 0.0), new Vec3d(2.0, 3.0, -1.0), new Vec3d(2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, 0.0), new Vec3d(-2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 2.0), new Vec3d(1.0, 3.0, 2.0), new Vec3d(-1.0, 3.0, 2.0), new Vec3d(0.0, 3.0, -2.0), new Vec3d(1.0, 3.0, -2.0), new Vec3d(-1.0, 3.0, -2.0), new Vec3d(0.0, 4.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(-1.0, 4.0, 0.0), new Vec3d(0.0, 4.0, 1.0), new Vec3d(0.0, 4.0, -1.0), new Vec3d(1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, -1.0), new Vec3d(1.0, 4.0, -1.0), new Vec3d(2.0, 4.0, 0.0), new Vec3d(2.0, 4.0, 1.0), new Vec3d(2.0, 4.0, -1.0)};
    private final Vec3d[] surroundTargetsCritical = new Vec3d[]{new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 1.0), new Vec3d(2.0, 0.0, -1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(1.0, 0.0, 2.0), new Vec3d(-1.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(-1.0, 0.0, -2.0), new Vec3d(1.0, 0.0, -2.0), new Vec3d(2.0, 1.0, -1.0), new Vec3d(2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, 0.0), new Vec3d(-2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(-1.0, 1.0, 2.0), new Vec3d(0.0, 1.0, -2.0), new Vec3d(1.0, 1.0, -2.0), new Vec3d(-1.0, 1.0, -2.0), new Vec3d(2.0, 2.0, -1.0), new Vec3d(2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 2.0), new Vec3d(-1.0, 2.0, 2.0), new Vec3d(1.0, 2.0, -2.0), new Vec3d(-1.0, 2.0, -2.0), new Vec3d(2.0, 3.0, 0.0), new Vec3d(2.0, 3.0, -1.0), new Vec3d(2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, 0.0), new Vec3d(-2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 2.0), new Vec3d(1.0, 3.0, 2.0), new Vec3d(-1.0, 3.0, 2.0), new Vec3d(0.0, 3.0, -2.0), new Vec3d(1.0, 3.0, -2.0), new Vec3d(-1.0, 3.0, -2.0), new Vec3d(0.0, 4.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(-1.0, 4.0, 0.0), new Vec3d(0.0, 4.0, 1.0), new Vec3d(0.0, 4.0, -1.0), new Vec3d(1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, -1.0), new Vec3d(1.0, 4.0, -1.0), new Vec3d(2.0, 4.0, 0.0), new Vec3d(2.0, 4.0, 1.0), new Vec3d(2.0, 4.0, -1.0)};
    private Setting<Boolean> toggleable = this.register(Settings.b("Toggleable", true));
    private Setting<Boolean> spoofRotations = this.register(Settings.b("Spoof Rotations", false));
    private Setting<Boolean> spoofHotbar = this.register(Settings.b("Spoof Hotbar", false));
    private Setting<Double> blockPerTick = this.register(Settings.d("Blocks per Tick", 1.0));
    private Setting<Boolean> debugMessages = this.register(Settings.b("Debug Messages", false));
    private BlockPos basePos;
    private int offsetStep = 0;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;

    @Override
    public void onUpdate() {
        if (this.isDisabled() || AutoNomadHut.mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.offsetStep == 0) {
            this.basePos = new BlockPos(AutoNomadHut.mc.player.getPositionVector()).down();
            this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] Starting Loop, current Player Slot: " + this.playerHotbarSlot);
            }
            if (!this.spoofHotbar.getValue().booleanValue()) {
                this.lastHotbarSlot = AutoNomadHut.mc.player.inventory.currentItem;
            }
        }
        for (int i = 0; i < (int)Math.floor(this.blockPerTick.getValue()); ++i) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] Loop iteration: " + this.offsetStep);
            }
            if (this.offsetStep >= this.surroundTargets.length) {
                this.endLoop();
                return;
            }
            Vec3d offset = this.surroundTargets[this.offsetStep];
            this.placeBlock(new BlockPos((Vec3i)this.basePos.add(offset.x, offset.y, offset.z)));
            ++this.offsetStep;
        }
    }

    @Override
    protected void onEnable() {
        if (AutoNomadHut.mc.player == null) {
            this.disable();
            return;
        }
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoFeetPlace] Enabling");
        }
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoFeetPlace] Saving initial Slot  = " + this.playerHotbarSlot);
        }
    }

    @Override
    protected void onDisable() {
        if (AutoNomadHut.mc.player == null) {
            return;
        }
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoFeetPlace] Disabling");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] Setting Slot to  = " + this.playerHotbarSlot);
            }
            if (this.spoofHotbar.getValue().booleanValue()) {
                AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            } else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }

    private void endLoop() {
        this.offsetStep = 0;
        if (this.debugMessages.getValue().booleanValue()) {
            Command.sendChatMessage("[AutoFeetPlace] Ending Loop");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] Setting Slot back to  = " + this.playerHotbarSlot);
            }
            if (this.spoofHotbar.getValue().booleanValue()) {
                AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            } else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        if (!this.toggleable.getValue().booleanValue()) {
            this.disable();
        }
    }

    private void placeBlock(BlockPos blockPos) {
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] Block is already placed, skipping");
            }
            return;
        }
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoFeetPlace] !checkForNeighbours(blockPos), disabling! ");
            }
            return;
        }
        this.placeBlockExecute(blockPos);
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    public void placeBlockExecute(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        for (EnumFacing side : EnumFacing.values()) {
            int obiSlot;
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!AutoNomadHut.canBeClicked(neighbor)) {
                if (!this.debugMessages.getValue().booleanValue()) continue;
                Command.sendChatMessage("[AutoFeetPlace] No neighbor to click at!");
                continue;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (eyesPos.squareDistanceTo(hitVec) > 18.0625) {
                if (!this.debugMessages.getValue().booleanValue()) continue;
                Command.sendChatMessage("[AutoFeetPlace] Distance > 4.25 blocks!");
                continue;
            }
            boolean needSneak = false;
            Block blockBelow = AutoNomadHut.mc.world.getBlockState(neighbor).getBlock();
            if (BlockInteractionHelper.blackList.contains(blockBelow) || BlockInteractionHelper.shulkerList.contains(blockBelow)) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoFeetPlace] Sneak enabled!");
                }
                needSneak = true;
            }
            if (needSneak) {
                AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoNomadHut.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            if ((obiSlot = this.findObiInHotbar()) == -1) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoFeetPlace] No Obi in Hotbar, disabling!");
                }
                this.disable();
                return;
            }
            if (this.lastHotbarSlot != obiSlot) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoFeetPlace] Setting Slot to Obi at  = " + obiSlot);
                }
                if (this.spoofHotbar.getValue().booleanValue()) {
                    AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obiSlot));
                } else {
                    Wrapper.getPlayer().inventory.currentItem = obiSlot;
                }
                this.lastHotbarSlot = obiSlot;
            }
            AutoNomadHut.mc.playerController.processRightClickBlock(Wrapper.getPlayer(), AutoNomadHut.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            if (needSneak) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoFeetPlace] Sneak disabled!");
                }
                AutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoNomadHut.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            return;
        }
    }

    private static boolean canBeClicked(BlockPos pos) {
        return AutoNomadHut.getBlock(pos).canCollideCheck(AutoNomadHut.getState(pos), false);
    }

    private static Block getBlock(BlockPos pos) {
        return AutoNomadHut.getState(pos).getBlock();
    }

    private static IBlockState getState(BlockPos pos) {
        return Wrapper.getWorld().getBlockState(pos);
    }

    private static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = AutoNomadHut.getLegitRotations(vec);
        Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Wrapper.getPlayer().onGround));
    }

    private static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = AutoNomadHut.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Wrapper.getPlayer().rotationYaw + MathHelper.wrapDegrees((float)(yaw - Wrapper.getPlayer().rotationYaw)), Wrapper.getPlayer().rotationPitch + MathHelper.wrapDegrees((float)(pitch - Wrapper.getPlayer().rotationPitch))};
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
    }
}

