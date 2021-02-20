/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBed
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
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="OLD ABB", category=Module.Category.COMBAT, description="Automatically places + detonates a bed.")
public class OldBOMBer
extends Module {
    private static final DecimalFormat df = new DecimalFormat("#.#");
    private Setting<Boolean> debugMessages = this.register(Settings.b("Debug Messages", false));
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", false));
    private Setting<Boolean> chainBed = this.register(Settings.b("Chain Bomb", false));
    private int stage;
    private BlockPos placeTarget;
    private int bedSlot;
    private int playerHotbarSlot = -1;
    private int beds;
    private boolean isSneaking;

    @Override
    protected void onEnable() {
        if (OldBOMBer.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            this.disable();
            return;
        }
        df.setRoundingMode(RoundingMode.CEILING);
        this.stage = 0;
        this.placeTarget = null;
        this.bedSlot = -1;
        this.isSneaking = false;
        for (int i = 0; i < 9 && this.bedSlot == -1; ++i) {
            ItemStack stack = OldBOMBer.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (!(stack.func_77973_b() instanceof ItemBed)) continue;
            this.bedSlot = i;
            break;
        }
        if (OldBOMBer.mc.field_71476_x == null || OldBOMBer.mc.field_71476_x.func_178782_a() == null || OldBOMBer.mc.field_71476_x.func_178782_a().func_177984_a() == null) {
            if (this.debugMessages.getValue().booleanValue()) {
                Command.sendChatMessage("[AutoBedBomb] Not a valid place target, disabling.");
            }
            this.disable();
            return;
        }
        this.placeTarget = OldBOMBer.mc.field_71476_x.func_178782_a().func_177984_a();
        if (this.debugMessages.getValue().booleanValue()) {
            // empty if block
        }
    }

    @Override
    public void onUpdate() {
        if (OldBOMBer.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (this.stage == 0 && !this.chainBed.getValue().booleanValue()) {
            if (this.bedSlot == -1) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoBedBomb] Bed(s) missing, disabling.");
                }
                this.disable();
                return;
            }
            OldBOMBer.mc.field_71439_g.field_71071_by.field_70461_c = this.bedSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            OldBOMBer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)OldBOMBer.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            this.stage = 1;
        }
        if (this.stage == 1) {
            OldBOMBer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.disable();
        }
        if (this.stage == 0 && this.chainBed.getValue().booleanValue()) {
            if (!(OldBOMBer.mc.field_71462_r != null && OldBOMBer.mc.field_71462_r instanceof GuiContainer || OldBOMBer.mc.field_71439_g.field_71071_by.func_70301_a(0).func_77973_b() == Items.field_151104_aV)) {
                for (int i = 9; i < 35; ++i) {
                    if (OldBOMBer.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() != Items.field_151104_aV) continue;
                    OldBOMBer.mc.field_71442_b.func_187098_a(OldBOMBer.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.SWAP, (EntityPlayer)OldBOMBer.mc.field_71439_g);
                    break;
                }
            }
            if (this.bedSlot == -1) {
                if (this.debugMessages.getValue().booleanValue()) {
                    Command.sendChatMessage("[AutoBedBomb] Bed(s) missing, disabling.");
                }
                this.disable();
                return;
            }
            OldBOMBer.mc.field_71439_g.field_71071_by.field_70461_c = this.bedSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            OldBOMBer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)OldBOMBer.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            OldBOMBer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.disable();
            this.enable();
        }
    }

    private void placeBlock(BlockPos pos, EnumFacing side) {
        BlockPos neighbour = pos.func_177972_a(side);
        EnumFacing opposite = side.func_176734_d();
        if (!this.isSneaking) {
            OldBOMBer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)OldBOMBer.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        if (this.rotate.getValue().booleanValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        OldBOMBer.mc.field_71442_b.func_187099_a(OldBOMBer.mc.field_71439_g, OldBOMBer.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        OldBOMBer.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
}

