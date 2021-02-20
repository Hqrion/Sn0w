/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.BlockWeb
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Mouse
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.ProcessRightClickBlockEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.HoleUtils;
import me.zeroeightsix.kami.util.InventoryUtil;
import me.zeroeightsix.kami.util.Timer;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

@Module.Info(name="Offhand", category=Module.Category.COMBAT)
public class Offhand
extends Module {
    public Setting<Boolean> crystal;
    public Setting<Boolean> crystalauracheck;
    public Setting<Float> crystalHealth;
    public Setting<Float> crystalHoleHealth;
    public Setting<Boolean> gapple;
    public Setting<Boolean> armorCheck;
    public Setting<Integer> actions;
    public Mode2 currentMode;
    public int totems = 0;
    public int crystals = 0;
    public int gapples = 0;
    public int lastTotemSlot = -1;
    public int lastGappleSlot = -1;
    public int lastCrystalSlot = -1;
    public int lastObbySlot = -1;
    public int lastWebSlot = -1;
    public boolean holdingCrystal = false;
    public boolean holdingTotem = false;
    public boolean holdingGapple = false;
    public boolean didSwitchThisTick = false;
    private final Queue<InventoryUtil.Task> taskList;
    private static Offhand instance;
    private Timer timer;
    private Timer secondTimer;
    private boolean second = false;
    private boolean switchedForHealthReason = false;
    @EventHandler
    private Listener<ProcessRightClickBlockEvent> eventListener = new Listener<ProcessRightClickBlockEvent>(event -> {
        if (event.hand == EnumHand.MAIN_HAND && event.stack.func_77973_b() == Items.field_185158_cP && Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao && Offhand.mc.field_71476_x != null && event.pos == Offhand.mc.field_71476_x.func_178782_a()) {
            event.cancel();
            Offhand.mc.field_71439_g.func_184598_c(EnumHand.OFF_HAND);
            Offhand.mc.field_71442_b.func_187101_a((EntityPlayer)Offhand.mc.field_71439_g, (World)Offhand.mc.field_71441_e, EnumHand.OFF_HAND);
        }
    }, new Predicate[0]);

    public Offhand() {
        this.crystal = this.register(Settings.b("Crystal"));
        this.crystalauracheck = this.register(Settings.b("Crystal Aura Check"));
        this.crystalHealth = this.register(Settings.f("Crystal Health", 1.0f));
        this.crystalHoleHealth = this.register(Settings.f("Crystal Hole Health", 1.0f));
        this.gapple = this.register(Settings.b("Gapple"));
        this.armorCheck = this.register(Settings.b("armorCheck"));
        this.actions = this.register(Settings.integerBuilder("Packets").withRange(0, 10).withValue(4).build());
        this.currentMode = Mode2.TOTEMS;
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.timer = new Timer();
        this.secondTimer = new Timer();
        instance = this;
    }

    public static Offhand getInstance() {
        if (instance == null) {
            instance = new Offhand();
        }
        return instance;
    }

    @Override
    public void onUpdate() {
        if (this.timer.passedMs(50L)) {
            if (Offhand.mc.field_71439_g != null && Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao && Offhand.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP && Mouse.isButtonDown((int)1)) {
                Offhand.mc.field_71439_g.func_184598_c(EnumHand.OFF_HAND);
                Offhand.mc.field_71474_y.field_74313_G.field_74513_e = Mouse.isButtonDown((int)1);
            }
        } else if (Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao && Offhand.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) {
            Offhand.mc.field_71474_y.field_74313_G.field_74513_e = false;
        }
        if (Offhand.nullCheck()) {
            return;
        }
        this.doOffhand();
        if (this.secondTimer.passedMs(50L) && this.second) {
            this.second = false;
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent event) {
        if (!Offhand.fullNullCheck() && Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao && Offhand.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP && Offhand.mc.field_71474_y.field_74313_G.func_151470_d()) {
            CPacketPlayerTryUseItem packet2;
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (packet.func_187022_c() == EnumHand.MAIN_HAND) {
                    if (this.timer.passedMs(50L)) {
                        Offhand.mc.field_71439_g.func_184598_c(EnumHand.OFF_HAND);
                        Offhand.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    }
                    event.cancel();
                }
            } else if (event.getPacket() instanceof CPacketPlayerTryUseItem && (packet2 = (CPacketPlayerTryUseItem)event.getPacket()).func_187028_a() == EnumHand.OFF_HAND && !this.timer.passedMs(50L)) {
                event.cancel();
            }
        }
    }

    @Override
    public String getHudInfo() {
        if (Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
            return "Crystals";
        }
        if (Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
            return "Totems";
        }
        if (Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao) {
            return "Gapples";
        }
        return null;
    }

    public void doOffhand() {
        this.didSwitchThisTick = false;
        this.holdingCrystal = Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP;
        this.holdingTotem = Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY;
        this.holdingGapple = Offhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao;
        this.totems = Offhand.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingTotem) {
            this.totems += Offhand.mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_190929_cY).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.crystals = Offhand.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_185158_cP).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingCrystal) {
            this.crystals += Offhand.mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_185158_cP).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.gapples = Offhand.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_151153_ao).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingGapple) {
            this.gapples += Offhand.mc.field_71439_g.field_71071_by.field_184439_c.stream().filter(itemStack -> itemStack.func_77973_b() == Items.field_151153_ao).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.doSwitch();
    }

    public void doSwitch() {
        this.currentMode = Mode2.TOTEMS;
        if (this.gapple.getValue().booleanValue() && Offhand.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && Offhand.mc.field_71474_y.field_74313_G.func_151470_d()) {
            this.currentMode = Mode2.GAPPLES;
        } else if (this.currentMode != Mode2.CRYSTALS && this.crystal.getValue().booleanValue() && (HoleUtils.isInHole((Entity)Offhand.mc.field_71439_g) && EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) > this.crystalHoleHealth.getValue().floatValue() || EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) > this.crystalHealth.getValue().floatValue())) {
            if (this.crystalauracheck.getValue().booleanValue()) {
                if (ModuleManager.getModuleByName("Crystal Aura").isEnabled()) {
                    this.currentMode = Mode2.CRYSTALS;
                }
            } else {
                this.currentMode = Mode2.CRYSTALS;
            }
        }
        if (this.currentMode == Mode2.CRYSTALS && this.crystals == 0) {
            this.setMode(Mode2.TOTEMS);
        }
        if (this.currentMode == Mode2.CRYSTALS && (!HoleUtils.isInHole((Entity)Offhand.mc.field_71439_g) && EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) <= this.crystalHealth.getValue().floatValue() || EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) <= this.crystalHoleHealth.getValue().floatValue())) {
            if (this.currentMode == Mode2.CRYSTALS) {
                this.switchedForHealthReason = true;
            }
            this.setMode(Mode2.TOTEMS);
        }
        if (this.switchedForHealthReason && (HoleUtils.isInHole((Entity)Offhand.mc.field_71439_g) && EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) > this.crystalHoleHealth.getValue().floatValue() || EntityUtil.getHealth((Entity)Offhand.mc.field_71439_g, true) > this.crystalHealth.getValue().floatValue())) {
            this.setMode(Mode2.CRYSTALS);
            this.switchedForHealthReason = false;
        }
        if (this.currentMode == Mode2.CRYSTALS && this.armorCheck.getValue().booleanValue() && (Offhand.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_190931_a || Offhand.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.HEAD).func_77973_b() == Items.field_190931_a || Offhand.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.LEGS).func_77973_b() == Items.field_190931_a || Offhand.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.FEET).func_77973_b() == Items.field_190931_a)) {
            this.setMode(Mode2.TOTEMS);
        }
        if (Offhand.mc.field_71462_r instanceof GuiContainer && !(Offhand.mc.field_71462_r instanceof GuiInventory)) {
            return;
        }
        Item currentOffhandItem = Offhand.mc.field_71439_g.func_184592_cb().func_77973_b();
        switch (this.currentMode) {
            case TOTEMS: {
                if (this.totems <= 0 || this.holdingTotem) break;
                this.lastTotemSlot = InventoryUtil.findItemInventorySlot(Items.field_190929_cY, false);
                int lastSlot = this.getLastSlot(currentOffhandItem, this.lastTotemSlot);
                this.putItemInOffhand(this.lastTotemSlot, lastSlot);
                break;
            }
            case GAPPLES: {
                if (this.gapples <= 0 || this.holdingGapple) break;
                this.lastGappleSlot = InventoryUtil.findItemInventorySlot(Items.field_151153_ao, false);
                int lastSlot = this.getLastSlot(currentOffhandItem, this.lastGappleSlot);
                this.putItemInOffhand(this.lastGappleSlot, lastSlot);
                break;
            }
            default: {
                if (this.crystals <= 0 || this.holdingCrystal) break;
                this.lastCrystalSlot = InventoryUtil.findItemInventorySlot(Items.field_185158_cP, false);
                int lastSlot = this.getLastSlot(currentOffhandItem, this.lastCrystalSlot);
                this.putItemInOffhand(this.lastCrystalSlot, lastSlot);
                break;
            }
        }
        for (int i = 0; i < this.actions.getValue(); ++i) {
            InventoryUtil.Task task = this.taskList.poll();
            if (task == null) continue;
            task.run();
            if (!task.isSwitching()) continue;
            this.didSwitchThisTick = true;
        }
    }

    private int getLastSlot(Item item, int slotIn) {
        if (item == Items.field_185158_cP) {
            return this.lastCrystalSlot;
        }
        if (item == Items.field_151153_ao) {
            return this.lastGappleSlot;
        }
        if (item == Items.field_190929_cY) {
            return this.lastTotemSlot;
        }
        if (InventoryUtil.isBlock(item, BlockObsidian.class)) {
            return this.lastObbySlot;
        }
        if (InventoryUtil.isBlock(item, BlockWeb.class)) {
            return this.lastWebSlot;
        }
        if (item == Items.field_190931_a) {
            return -1;
        }
        return slotIn;
    }

    private void putItemInOffhand(int slotIn, int slotOut) {
        if (slotIn != -1 && this.taskList.isEmpty()) {
            this.taskList.add(new InventoryUtil.Task(slotIn));
            this.taskList.add(new InventoryUtil.Task(45));
            this.taskList.add(new InventoryUtil.Task(slotOut));
            this.taskList.add(new InventoryUtil.Task());
        }
    }

    public void setMode(Mode2 mode) {
        this.currentMode = this.currentMode == mode ? Mode2.TOTEMS : mode;
    }

    public static enum Mode2 {
        TOTEMS,
        GAPPLES,
        CRYSTALS;

    }
}

