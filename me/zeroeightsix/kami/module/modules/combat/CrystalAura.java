/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.chat.AutoGG;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.HoleUtils;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Module.Info(name="Crystal Aura", category=Module.Category.COMBAT)
public class CrystalAura
extends Module {
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    EnumFacing f;
    private Setting<Page> p = this.register(Settings.enumBuilder(Page.class).withName("Page").withValue(Page.ONE).build());
    private Setting<BreakModes> breakmode = this.register(Settings.enumBuilder(BreakModes.class).withName("BreakMode").withValue(BreakModes.ALL).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> place = this.register(Settings.booleanBuilder("Place").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> explode = this.register(Settings.booleanBuilder("Explode").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> autoSwitch = this.register(Settings.booleanBuilder("Auto Switch").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> antiWeakness = this.register(Settings.booleanBuilder("AntiWeakness").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> noGappleSwitch = this.register(Settings.booleanBuilder("NoGappleSwitch").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> nodesync = this.register(Settings.booleanBuilder("Nodesync").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> spoofRotations = this.register(Settings.booleanBuilder("SpoofRotations").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> endtocrystalrange = this.register(Settings.doubleBuilder("Enemy Range").withMinimum(0.0).withValue(13.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> CancelCrystallol = this.register(Settings.booleanBuilder("Cancel Crystal").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> thirteenmode = this.register(Settings.booleanBuilder("1.13 mode").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> chat = this.register(Settings.booleanBuilder("Chat").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> rotate = this.register(Settings.booleanBuilder("Rotate").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> raytrace = this.register(Settings.booleanBuilder("Raytrace").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Integer> waitTick = this.register(Settings.integerBuilder("WaitTick").withValue(1).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> range = this.register(Settings.doubleBuilder("Range").withMinimum(0.0).withValue(6.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> placeRange = this.register(Settings.doubleBuilder("Place Range").withMinimum(0.0).withValue(6.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> maxSelfDmg = this.register(Settings.doubleBuilder("MaxSelfDmg").withMinimum(0.0).withValue(15.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> minDmg = this.register(Settings.doubleBuilder("MinDmg").withMinimum(0.0).withValue(9.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> facePlace = this.register(Settings.doubleBuilder("FacePlaceHP").withMinimum(0.0).withValue(6.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Double> walls = this.register(Settings.doubleBuilder("WallsRange").withMinimum(0.0).withValue(3.6).withMaximum(20.0).withVisibility(v -> this.p.getValue().equals((Object)Page.ONE)).build());
    private Setting<Boolean> customColours = this.register(Settings.booleanBuilder("RGB").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO)).build());
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withMinimum(0).withValue(0).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(52).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Boolean> rainbow = this.register(Settings.booleanBuilder("Rainbow ").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO)).build());
    private Setting<Integer> redB = this.register(Settings.integerBuilder("RedBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> greenB = this.register(Settings.integerBuilder("GreenBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> blueB = this.register(Settings.integerBuilder("BlueBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Integer> alphabounding = this.register(Settings.integerBuilder("BoundingBoxAlpha").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.customColours.getValue() != false).build());
    private Setting<Boolean> DynamicColor = this.register(Settings.booleanBuilder("Dynamic DMG Color").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO)).build());
    private Setting<Boolean> RenderDamage = this.register(Settings.booleanBuilder("Render DMG").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO)).build());
    private Setting<Integer> damagered = this.register(Settings.integerBuilder("Red").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.RenderDamage.getValue() != false).build());
    private Setting<Integer> damagegreen = this.register(Settings.integerBuilder("Green").withMinimum(0).withValue(0).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.RenderDamage.getValue() != false).build());
    private Setting<Integer> damageblue = this.register(Settings.integerBuilder("Blue").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.RenderDamage.getValue() != false).build());
    private Setting<Boolean> damagerainbow = this.register(Settings.booleanBuilder("Rainbow").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO) && this.RenderDamage.getValue() != false).build());
    private Setting<Boolean> OffhandBreak = this.register(Settings.booleanBuilder("OffhandBreak").withValue(true).withVisibility(v -> this.p.getValue().equals((Object)Page.THREE)).build());
    private Setting<Boolean> AntiSuicide = this.register(Settings.booleanBuilder("AntiSuicide").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.THREE)).build());
    private Setting<Double> AntiSuicideValue = this.register(Settings.doubleBuilder("AntiSuicide Health").withMinimum(0.0).withValue(5.0).withVisibility(v -> this.p.getValue().equals((Object)Page.THREE)).build());
    private Setting<Boolean> ExplodingOrPlacingHudinfo = this.register(Settings.booleanBuilder("Exploding/Placing HudIndo").withValue(false).withVisibility(v -> this.p.getValue().equals((Object)Page.TWO)).build());
    private BlockPos renderBlock;
    private EntityPlayer target;
    public boolean isActive = false;
    public String isPlacing = null;
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int newSlot;
    private BlockPos render;
    private String renderDmg;
    private int crystals;
    private Entity renderEnt;
    public int hacker;
    private final ArrayList<BlockPos> PlacedCrystals = new ArrayList();
    private int waitCounter;
    private int hitDelayCounter;
    Item item;
    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<PacketEvent.Send>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && this.spoofRotations.getValue().booleanValue() && isSpoofingAngles) {
            ((CPacketPlayer)packet).field_149476_e = (float)yaw;
            ((CPacketPlayer)packet).field_149473_f = (float)pitch;
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener = new Listener<PacketEvent.Receive>(event -> {
        SPacketSoundEffect packet;
        if (event.getPacket() instanceof SPacketSoundEffect && this.nodesync.getValue().booleanValue() && (packet = (SPacketSoundEffect)event.getPacket()).func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB) {
            for (Entity e : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
                if (!(e instanceof EntityEnderCrystal) || !(e.func_70011_f(packet.func_149207_d(), packet.func_149211_e(), packet.func_149210_f()) <= 6.0)) continue;
                e.func_70106_y();
            }
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        int crystalSlot;
        if (this.AntiSuicide.getValue().booleanValue() && (double)(CrystalAura.mc.field_71439_g.func_110143_aJ() + CrystalAura.mc.field_71439_g.func_110139_bj()) <= this.AntiSuicideValue.getValue()) {
            return;
        }
        this.crystals = CrystalAura.mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> itemStack.func_77973_b() == this.item).mapToInt(ItemStack::func_190916_E).sum();
        this.isActive = false;
        if (CrystalAura.mc.field_71439_g == null || CrystalAura.mc.field_71439_g.field_70128_L) {
            return;
        }
        EntityEnderCrystal crystal = CrystalAura.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(e -> (double)CrystalAura.mc.field_71439_g.func_70032_d(e) <= this.range.getValue()).filter(e -> this.breakModeCheck((Entity)e)).map(entity -> (EntityEnderCrystal)entity).min(Comparator.comparing(c -> Float.valueOf(CrystalAura.mc.field_71439_g.func_70032_d((Entity)c)))).orElse(null);
        if (this.explode.getValue().booleanValue() && crystal != null) {
            if (!CrystalAura.mc.field_71439_g.func_70685_l((Entity)crystal) && (double)CrystalAura.mc.field_71439_g.func_70032_d((Entity)crystal) > this.walls.getValue()) {
                return;
            }
            if (this.waitTick.getValue() > 0) {
                if (this.waitCounter < this.waitTick.getValue()) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
            }
            if (this.antiWeakness.getValue().booleanValue() && CrystalAura.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
                if (!this.isAttacking) {
                    this.oldSlot = CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c;
                    this.isAttacking = true;
                }
                this.newSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    ItemStack stack = CrystalAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                    if (stack == ItemStack.field_190927_a) continue;
                    if (stack.func_77973_b() instanceof ItemSword) {
                        this.newSlot = i;
                        break;
                    }
                    if (!(stack.func_77973_b() instanceof ItemTool)) continue;
                    this.newSlot = i;
                    break;
                }
                if (this.newSlot != -1) {
                    CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c = this.newSlot;
                    this.switchCooldown = true;
                }
            }
            this.isActive = true;
            if (this.rotate.getValue().booleanValue()) {
                this.lookAtPacket(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (EntityPlayer)CrystalAura.mc.field_71439_g);
            }
            CrystalAura.mc.field_71442_b.func_78764_a((EntityPlayer)CrystalAura.mc.field_71439_g, (Entity)crystal);
            this.isPlacing = "Exploding";
            if (this.OffhandBreak.getValue().booleanValue()) {
                CrystalAura.mc.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
            } else {
                CrystalAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            }
            if (this.CancelCrystallol.getValue().booleanValue()) {
                crystal.func_70106_y();
                CrystalAura.mc.field_71441_e.func_73022_a();
                CrystalAura.mc.field_71441_e.func_72910_y();
            }
            this.isActive = false;
            return;
        }
        CrystalAura.resetRotation();
        if (this.oldSlot != -1) {
            CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c = this.oldSlot;
            this.oldSlot = -1;
        }
        this.isAttacking = false;
        this.isActive = false;
        int n = crystalSlot = CrystalAura.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP ? CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (CrystalAura.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() != Items.field_185158_cP) continue;
                crystalSlot = l;
                break;
            }
        }
        boolean offhand = false;
        if (CrystalAura.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
            offhand = true;
        } else if (crystalSlot == -1) {
            return;
        }
        List<BlockPos> blocks = this.findCrystalBlocks();
        ArrayList entities = new ArrayList();
        entities.addAll(CrystalAura.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.func_70005_c_())).sorted(Comparator.comparing(e -> Float.valueOf(CrystalAura.mc.field_71439_g.func_70032_d((Entity)e)))).collect(Collectors.toList()));
        BlockPos q = null;
        double damage = 0.5;
        for (Entity entity2 : entities) {
            if (entity2 == CrystalAura.mc.field_71439_g || ((EntityLivingBase)entity2).func_110143_aJ() <= 0.0f || entity2.field_70128_L || CrystalAura.mc.field_71439_g == null) continue;
            for (BlockPos blockPos : blocks) {
                double self;
                double b = entity2.func_174818_b(blockPos);
                if (b >= this.endtocrystalrange.getValue() * Math.pow(3.6, 2.0)) continue;
                double d = CrystalAura.calculateDamage((double)blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1, (double)blockPos.func_177952_p() + 0.5, entity2);
                if (HoleUtils.isInHole((Entity)((EntityLivingBase)entity2)) && (double)(((EntityLivingBase)entity2).func_110143_aJ() + ((EntityLivingBase)entity2).func_110139_bj()) > this.facePlace.getValue() || !(d > damage) || !(d > this.minDmg.getValue()) || (self = (double)CrystalAura.calculateDamage((double)blockPos.func_177958_n() + 0.5, blockPos.func_177956_o() + 1, (double)blockPos.func_177952_p() + 0.5, (Entity)CrystalAura.mc.field_71439_g)) > d && !(d < (double)((EntityLivingBase)entity2).func_110143_aJ()) || self - 0.5 > (double)CrystalAura.mc.field_71439_g.func_110143_aJ() || self > this.maxSelfDmg.getValue()) continue;
                damage = d;
                q = blockPos;
                this.renderEnt = entity2;
                this.renderDmg = String.valueOf((int)damage);
                this.hacker = (int)damage;
            }
        }
        if (damage == 0.5) {
            this.render = null;
            this.renderEnt = null;
            CrystalAura.resetRotation();
            return;
        }
        this.render = q;
        if (this.place.getValue().booleanValue()) {
            if (CrystalAura.mc.field_71439_g == null) {
                return;
            }
            this.isActive = true;
            if (this.rotate.getValue().booleanValue()) {
                this.lookAtPacket((double)q.func_177958_n() + 0.5, (double)q.func_177956_o() - 0.5, (double)q.func_177952_p() + 0.5, (EntityPlayer)CrystalAura.mc.field_71439_g);
            }
            RayTraceResult result = CrystalAura.mc.field_71441_e.func_72933_a(new Vec3d(CrystalAura.mc.field_71439_g.field_70165_t, CrystalAura.mc.field_71439_g.field_70163_u + (double)CrystalAura.mc.field_71439_g.func_70047_e(), CrystalAura.mc.field_71439_g.field_70161_v), new Vec3d((double)q.func_177958_n() + 0.5, (double)q.func_177956_o() - 0.5, (double)q.func_177952_p() + 0.5));
            if (this.raytrace.getValue().booleanValue()) {
                if (result == null || result.field_178784_b == null) {
                    q = null;
                    this.f = null;
                    this.render = null;
                    CrystalAura.resetRotation();
                    this.isActive = false;
                    return;
                }
                this.f = result.field_178784_b;
            }
            if (!offhand && CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c != crystalSlot) {
                if (this.autoSwitch.getValue().booleanValue()) {
                    if (this.noGappleSwitch.getValue().booleanValue() && this.isEatingGap()) {
                        this.isActive = false;
                        CrystalAura.resetRotation();
                        return;
                    }
                    this.isActive = true;
                    CrystalAura.mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                    CrystalAura.resetRotation();
                    this.switchCooldown = true;
                }
                return;
            }
            if (this.switchCooldown) {
                this.switchCooldown = false;
                return;
            }
            if (q != null && CrystalAura.mc.field_71439_g != null) {
                this.isActive = true;
                if (this.raytrace.getValue().booleanValue() && this.f != null) {
                    CrystalAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(q, this.f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    this.isPlacing = "Placing";
                    this.PlacedCrystals.add(q);
                } else {
                    CrystalAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(q, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    this.isPlacing = "Placing";
                    this.PlacedCrystals.add(q);
                }
                if (ModuleManager.isModuleEnabled("AutoEZ")) {
                    AutoGG.INSTANCE.addTargetedPlayer(this.renderEnt.func_70005_c_());
                }
            }
        }
        this.isActive = false;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.render != null) {
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            if (this.rainbow.getValue().booleanValue()) {
                KamiTessellator.prepare(7);
                KamiTessellator.drawBox(this.render, r, g, b, (int)this.alpha.getValue(), 63);
                KamiTessellator.release();
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBlockPos(this.render, 1.0f, r, g, b, this.alphabounding.getValue());
            } else {
                KamiTessellator.prepare(7);
                KamiTessellator.drawBox(this.render, (int)this.red.getValue(), (int)this.green.getValue(), (int)this.blue.getValue(), (int)this.alpha.getValue(), 63);
                KamiTessellator.release();
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBlockPos(this.render, 1.0f, this.redB.getValue(), this.greenB.getValue(), this.blueB.getValue(), this.alphabounding.getValue());
            }
            KamiTessellator.release();
            this.drawString(this.render, this.renderDmg);
        }
    }

    private boolean isEatingGap() {
        return CrystalAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemAppleGold && CrystalAura.mc.field_71439_g.func_184587_cr();
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = CrystalAura.calculateLookAt(px, py, pz, me);
        CrystalAura.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        if (!this.thirteenmode.getValue().booleanValue()) {
            return (CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && CrystalAura.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && CrystalAura.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && CrystalAura.mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost)).isEmpty() && CrystalAura.mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
        return (CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || CrystalAura.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && CrystalAura.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && CrystalAura.mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(CrystalAura.mc.field_71439_g.field_70165_t), Math.floor(CrystalAura.mc.field_71439_g.field_70163_u), Math.floor(CrystalAura.mc.field_71439_g.field_70161_v));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList positions = NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(CrystalAura.getPlayerPos(), this.placeRange.getValue().floatValue(), this.placeRange.getValue().intValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.func_177958_n();
        int cy = loc.func_177956_o();
        int cz = loc.func_177952_p();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f = y;
                    float f2 = sphere ? (float)cy + r : (float)(cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static void glBillboard(float x, float y, float z) {
        float scale = 0.048f;
        GlStateManager.func_179137_b((double)((double)x - CrystalAura.mc.func_175598_ae().field_78725_b), (double)((double)y - CrystalAura.mc.func_175598_ae().field_78726_c), (double)((double)z - CrystalAura.mc.func_175598_ae().field_78723_d));
        GlStateManager.func_187432_a((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(-CrystalAura.mc.field_71439_g.field_70177_z), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)CrystalAura.mc.field_71439_g.field_70125_A, (float)(CrystalAura.mc.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)(-scale), (float)(-scale), (float)scale);
    }

    public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
        CrystalAura.glBillboard(x, y, z);
        int distance = (int)player.func_70011_f((double)x, (double)y, (double)z);
        float scaleDistance = (float)distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.func_179152_a((float)scaleDistance, (float)scaleDistance, (float)scaleDistance);
    }

    private void drawString(BlockPos blockPos, String str) {
        int hacker11;
        int asred = this.damagered.getValue();
        int bsgreen = this.damagegreen.getValue();
        int csblue = this.damageblue.getValue();
        int hackerrgb = hacker11 = ColourUtils.toRGBA(asred, bsgreen, csblue, 255);
        if (this.damagerainbow.getValue().booleanValue()) {
            float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int red = rgb >> 16 & 0xFF;
            int green = rgb >> 8 & 0xFF;
            int blue = rgb & 0xFF;
            int n = hacker11 = ColourUtils.toRGBA(red, green, blue, 255);
        }
        GlStateManager.func_179094_E();
        CrystalAura.glBillboardDistanceScaled((float)blockPos.field_177962_a + 0.5f, (float)blockPos.field_177960_b + 0.5f, (float)blockPos.field_177961_c + 0.5f, (EntityPlayer)CrystalAura.mc.field_71439_g, 1.5f);
        GlStateManager.func_179097_i();
        GlStateManager.func_179137_b((double)(-((double)CrystalAura.mc.field_71466_p.func_78256_a(str) / 2.0)), (double)0.0, (double)0.0);
        if (this.RenderDamage.getValue().booleanValue()) {
            if (this.DynamicColor.getValue().booleanValue()) {
                if (this.hacker > 15) {
                    CrystalAura.mc.field_71466_p.func_175063_a(str, 0.0f, -1.0f, 16515843);
                }
                if (this.hacker > 10 && this.hacker < 15) {
                    CrystalAura.mc.field_71466_p.func_175063_a(str, 0.0f, -1.0f, 0xFFFF55);
                }
                if (this.hacker < 10) {
                    CrystalAura.mc.field_71466_p.func_175063_a(str, 0.0f, -1.0f, 4586499);
                }
            } else {
                CrystalAura.mc.field_71466_p.func_175063_a(str, 0.0f, -1.0f, hacker11);
            }
        }
        GlStateManager.func_179121_F();
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0f;
        double distancedsize = entity.func_70011_f(posX, posY, posZ) / (double)doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        double v = (1.0 - distancedsize) * blockDensity;
        float damage = (int)((v * v + v) / 2.0 * 7.0 * (double)doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = CrystalAura.getBlastReduction((EntityLivingBase)entity, CrystalAura.getDamageMultiplied(damage), new Explosion((World)CrystalAura.mc.field_71441_e, null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer)entity;
            DamageSource ds = DamageSource.func_94539_a((Explosion)explosion);
            damage = CombatRules.func_189427_a((float)damage, (float)ep.func_70658_aO(), (float)((float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e()));
            int k = EnchantmentHelper.func_77508_a((Iterable)ep.func_184193_aE(), (DamageSource)ds);
            float f = MathHelper.func_76131_a((float)k, (float)0.0f, (float)20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.func_70644_a(Potion.func_188412_a((int)11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.func_189427_a((float)damage, (float)entity.func_70658_aO(), (float)((float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e()));
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = CrystalAura.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * (diff == 0 ? 0.0f : (diff == 2 ? 1.0f : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return CrystalAura.calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = CrystalAura.mc.field_71439_g.field_70177_z;
            pitch = CrystalAura.mc.field_71439_g.field_70125_A;
            isSpoofingAngles = false;
        }
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.field_70165_t - px;
        double diry = me.field_70163_u - py;
        double dirz = me.field_70161_v - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        double pitch = Math.asin(diry /= len);
        double yaw = Math.atan2(dirz /= len, dirx /= len);
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        return new double[]{yaw += 90.0, pitch};
    }

    @Override
    public void onEnable() {
        KamiMod.EVENT_BUS.subscribe((Object)this);
        this.isActive = false;
        if (this.chat.getValue().booleanValue() && CrystalAura.mc.field_71439_g != null) {
            Command.toggle_message(this);
        }
        this.PlacedCrystals.clear();
    }

    @Override
    public void onDisable() {
        KamiMod.EVENT_BUS.unsubscribe((Object)this);
        this.render = null;
        this.renderEnt = null;
        CrystalAura.resetRotation();
        this.isActive = false;
        if (this.chat.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
        this.PlacedCrystals.clear();
    }

    @Override
    public String getHudInfo() {
        if (!this.ExplodingOrPlacingHudinfo.getValue().booleanValue()) {
            if (this.renderEnt == null) {
                return null;
            }
            return this.renderEnt.func_70005_c_();
        }
        if (this.renderEnt == null) {
            return null;
        }
        return this.isPlacing;
    }

    private boolean breakModeCheck(Entity crystal) {
        if (!(crystal instanceof EntityEnderCrystal)) {
            return false;
        }
        if (this.breakmode.getValue().equals((Object)BreakModes.ALL)) {
            return true;
        }
        if (this.breakmode.getValue().equals((Object)BreakModes.OnlyOwn)) {
            for (BlockPos pos : new ArrayList<BlockPos>(this.PlacedCrystals)) {
                if (pos == null || !(pos.func_185332_f((int)crystal.field_70165_t, (int)crystal.field_70163_u, (int)crystal.field_70161_v) <= 3.0)) continue;
                return true;
            }
        }
        return false;
    }

    private static enum BreakModes {
        OnlyOwn,
        ALL;

    }

    private static enum Page {
        ONE,
        TWO,
        THREE;

    }
}

