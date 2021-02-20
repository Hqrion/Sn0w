//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 */
package me.zeroeightsix.kami.module.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;

@Module.Info(name="Reverse Step", category=Module.Category.MOVEMENT, description="Step")
public class ReverseStep
extends Module {
    private Setting<Float> height = this.register(Settings.f("Height", 2.5f));
    private Setting<Boolean> ChatMsg = this.register(Settings.b("ChatMsg"));
    private Setting<Boolean> CancelOnSpeed = this.register(Settings.b("Cancel On Speed"));
    private int ticks = 0;

    @Override
    public void onUpdate() {
        if (ReverseStep.mc.world == null || ReverseStep.mc.player == null) {
            return;
        }
        if (ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (this.CancelOnSpeed.getValue().booleanValue() && ModuleManager.isModuleEnabled("Speed")) {
            return;
        }
        if (ReverseStep.mc.player != null && ReverseStep.mc.player.onGround && !ReverseStep.mc.player.isInWater() && !ReverseStep.mc.player.isOnLadder()) {
            for (double y = 0.0; y < (double)this.height.getValue().floatValue() + 0.5; y += 0.01) {
                if (ReverseStep.mc.world.getCollisionBoxes((Entity)ReverseStep.mc.player, ReverseStep.mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) continue;
                ReverseStep.mc.player.motionY = -10.0;
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.sendChatMessage("Reverse Step " + ChatFormatting.AQUA + "Enabled" + ChatFormatting.WHITE + "!");
        }
    }

    @Override
    public void onDisable() {
        if (this.ChatMsg.getValue().booleanValue()) {
            Command.sendChatMessage("Reverse Step " + ChatFormatting.BLUE + "Disabled" + ChatFormatting.WHITE + "!");
        }
    }
}

