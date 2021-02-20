/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.Vec3d
 */
package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.misc.AutoTool;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.LagCompensator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

@Module.Info(name="Aura", category=Module.Category.COMBAT, description="Hits entities around you")
public class Aura
extends Module {
    public static Aura INSTANCE;
    private Setting<Boolean> attackPlayers = this.register(Settings.b("Players", true));
    private Setting<Boolean> attackMobs = this.register(Settings.b("Mobs", false));
    private Setting<Boolean> attackAnimals = this.register(Settings.b("Animals", false));
    private Setting<Boolean> Rotate = this.register(Settings.b("Rotate", true));
    private Setting<Double> hitRange = this.register(Settings.d("Hit Range", 5.5));
    private Setting<Boolean> ignoreWalls = this.register(Settings.b("Ignore Walls", true));
    private Setting<WaitMode> waitMode = this.register(Settings.e("Mode", WaitMode.DYNAMIC));
    private Setting<Integer> waitTick = this.register(Settings.integerBuilder("Tick Delay").withMinimum(0).withValue(3).withVisibility(o -> this.waitMode.getValue().equals((Object)WaitMode.STATIC)).build());
    private Setting<Boolean> switchTo32k = this.register(Settings.b("32k Switch", true));
    private Setting<Boolean> onlyUse32k = this.register(Settings.b("32k Only", false));
    private static double yaw;
    private static double pitch;
    private static boolean isSpoofingAngles;
    private int waitCounter;
    public static Entity gamertarget;

    public Aura() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        boolean shield;
        if (Aura.mc.field_71439_g.field_70128_L) {
            return;
        }
        boolean bl = shield = Aura.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_185159_cQ) && Aura.mc.field_71439_g.func_184600_cs() == EnumHand.OFF_HAND;
        if (Aura.mc.field_71439_g.func_184587_cr() && !shield) {
            return;
        }
        if (this.waitMode.getValue().equals((Object)WaitMode.DYNAMIC)) {
            if (Aura.mc.field_71439_g.func_184825_o(this.getLagComp()) < 1.0f) {
                return;
            }
            if (Aura.mc.field_71439_g.field_70173_aa % 2 != 0) {
                return;
            }
        }
        if (this.waitMode.getValue().equals((Object)WaitMode.STATIC) && this.waitTick.getValue() > 0) {
            if (this.waitCounter < this.waitTick.getValue()) {
                ++this.waitCounter;
                return;
            }
            this.waitCounter = 0;
        }
        for (Entity target : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
            if (!EntityUtil.isLiving(target) || target == Aura.mc.field_71439_g || (double)Aura.mc.field_71439_g.func_70032_d(target) > this.hitRange.getValue() || ((EntityLivingBase)target).func_110143_aJ() <= 0.0f || this.waitMode.getValue().equals((Object)WaitMode.DYNAMIC) && ((EntityLivingBase)target).field_70737_aN != 0 || !this.ignoreWalls.getValue().booleanValue() && !Aura.mc.field_71439_g.func_70685_l(target) && !this.canEntityFeetBeSeen(target)) continue;
            if (target == null) {
                Aura.resetRotation();
            }
            gamertarget = target;
            if (this.attackPlayers.getValue().booleanValue() && target instanceof EntityPlayer && !Friends.isFriend(target.func_70005_c_())) {
                this.attack(target);
                if (this.Rotate.getValue().booleanValue()) {
                    this.lookAtPacket(target.field_70165_t, target.field_70163_u, target.field_70161_v, (EntityPlayer)Aura.mc.field_71439_g);
                }
                return;
            }
            if (!(EntityUtil.isPassive(target) ? this.attackAnimals.getValue() != false : EntityUtil.isMobAggressive(target) && this.attackMobs.getValue() != false)) continue;
            if (!this.switchTo32k.getValue().booleanValue() && ModuleManager.isModuleEnabled("AutoTool")) {
                AutoTool.equipBestWeapon();
            }
            this.attack(target);
            return;
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        Aura.resetRotation();
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = Aura.mc.field_71439_g.field_70177_z;
            pitch = Aura.mc.field_71439_g.field_70125_A;
            isSpoofingAngles = false;
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = Aura.calculateLookAt(px, py, pz, me);
        Aura.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
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

    private boolean checkSharpness(ItemStack stack) {
        if (stack.func_77978_p() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
        if (enchants == null) {
            return false;
        }
        for (int i = 0; i < enchants.func_74745_c(); ++i) {
            NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") != 16) continue;
            int lvl = enchant.func_74762_e("lvl");
            if (lvl < 42) break;
            return true;
        }
        return false;
    }

    private void attack(Entity e) {
        boolean holding32k = false;
        if (this.checkSharpness(Aura.mc.field_71439_g.func_184614_ca())) {
            holding32k = true;
        }
        if (this.switchTo32k.getValue().booleanValue() && !holding32k) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = Aura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack == ItemStack.field_190927_a || !this.checkSharpness(stack)) continue;
                newSlot = i;
                break;
            }
            if (newSlot != -1) {
                Aura.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
                holding32k = true;
            }
        }
        if (this.onlyUse32k.getValue().booleanValue() && !holding32k) {
            return;
        }
        Aura.mc.field_71442_b.func_78764_a((EntityPlayer)Aura.mc.field_71439_g, e);
        Aura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }

    private float getLagComp() {
        if (this.waitMode.getValue().equals((Object)WaitMode.DYNAMIC)) {
            return -(20.0f - LagCompensator.INSTANCE.getTickRate());
        }
        return 0.0f;
    }

    private boolean canEntityFeetBeSeen(Entity entityIn) {
        return Aura.mc.field_71441_e.func_147447_a(new Vec3d(Aura.mc.field_71439_g.field_70165_t, Aura.mc.field_71439_g.field_70163_u + (double)Aura.mc.field_71439_g.func_70047_e(), Aura.mc.field_71439_g.field_70161_v), new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u, entityIn.field_70161_v), false, true, false) == null;
    }

    private static enum WaitMode {
        DYNAMIC,
        STATIC;

    }
}

