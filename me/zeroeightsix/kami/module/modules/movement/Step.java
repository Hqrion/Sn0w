//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.text.DecimalFormat;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.MotionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(name="Step", category=Module.Category.MOVEMENT, description="Step")
public class Step
extends Module {
    private Setting<Boolean> timer = this.register(Settings.b("Timer", false));
    private Setting<Float> height = this.register(Settings.f("Height", 1.0f));
    private Setting<Boolean> antiStuck = this.register(Settings.b("AntiStuck"));
    private Setting<StepModes> StepMode = this.register(Settings.e("Step", StepModes.VANILLA));
    private Setting<Boolean> Reverse = this.register(Settings.b("Reverse", false));
    private Setting<Boolean> ChatMsg = this.register(Settings.b("ChatMsg"));
    private int ticks = 0;

    @Override
    public void onUpdate() {
        if (Step.mc.world == null || Step.mc.player == null || Step.mc.player.isInWater() || Step.mc.player.isInLava() || Step.mc.player.isOnLadder() || Step.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (this.StepMode.getValue().equals((Object)StepModes.NORMAL)) {
            if (this.timer.getValue().booleanValue()) {
                if (this.ticks == 0) {
                    EntityUtil.resetTimer();
                } else {
                    --this.ticks;
                }
            }
            if (Step.mc.player != null && Step.mc.player.onGround && !Step.mc.player.isInWater() && !Step.mc.player.isOnLadder() && this.Reverse.getValue().booleanValue()) {
                for (double y = 0.0; y < (double)this.height.getValue().floatValue() + 0.5; y += 0.01) {
                    if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) continue;
                    Step.mc.player.motionY = -10.0;
                    break;
                }
            }
            double[] dir = MotionUtils.forward(0.1);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.4, dir[1])).isEmpty()) {
                twofive = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.9, dir[1])).isEmpty()) {
                two = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.4, dir[1])).isEmpty()) {
                onefive = true;
            }
            if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 0.6, dir[1])).isEmpty()) {
                one = true;
            }
            if (Step.mc.player.collidedHorizontally && (Step.mc.player.moveForward != 0.0f || Step.mc.player.moveStrafing != 0.0f) && Step.mc.player.onGround) {
                int i;
                if (one && (double)this.height.getValue().floatValue() >= 1.0) {
                    double[] oneOffset = new double[]{0.42, 0.753};
                    for (i = 0; i < oneOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.6f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                    this.ticks = 1;
                }
                if (onefive && (double)this.height.getValue().floatValue() >= 1.5) {
                    double[] oneFiveOffset = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
                    for (i = 0; i < oneFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.35f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5, Step.mc.player.posZ);
                    this.ticks = 1;
                }
                if (two && (double)this.height.getValue().floatValue() >= 2.0) {
                    double[] twoOffset = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
                    for (i = 0; i < twoOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.25f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
                    this.ticks = 2;
                }
                if (twofive && (double)this.height.getValue().floatValue() >= 2.5) {
                    double[] twoFiveOffset = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }
                    if (this.timer.getValue().booleanValue()) {
                        EntityUtil.setTimer(0.15f);
                    }
                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5, Step.mc.player.posZ);
                    this.ticks = 2;
                }
            }
        }
        if (this.StepMode.getValue().equals((Object)StepModes.VANILLA)) {
            DecimalFormat df = new DecimalFormat("#");
            Step.mc.player.stepHeight = Float.parseFloat(df.format(this.height.getValue()));
        }
    }

    @Override
    public String getHudInfo() {
        String t = "";
        if (this.StepMode.getValue().equals((Object)StepModes.NORMAL)) {
            t = "Normal";
        }
        if (this.StepMode.getValue().equals((Object)StepModes.VANILLA)) {
            t = "Vanilla";
        }
        return t;
    }

    @Override
    public void onEnable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
    }

    @Override
    public void onDisable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.toggle_message(this);
        }
        Step.mc.player.stepHeight = 0.5f;
    }

    private static enum StepModes {
        VANILLA,
        NORMAL;

    }
}

