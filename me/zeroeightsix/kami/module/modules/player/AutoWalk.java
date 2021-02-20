//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.pathfinding.PathPoint
 *  net.minecraftforge.client.event.InputUpdateEvent
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.render.Pathfind;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathPoint;
import net.minecraftforge.client.event.InputUpdateEvent;

@Module.Info(name="AutoWalk", category=Module.Category.PLAYER)
public class AutoWalk
extends Module {
    private Setting<AutoWalkMode> mode = this.register(Settings.e("Mode", AutoWalkMode.FORWARD));
    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<InputUpdateEvent>(event -> {
        switch (this.mode.getValue()) {
            case FORWARD: {
                event.getMovementInput().moveForward = 1.0f;
                break;
            }
            case BACKWARDS: {
                event.getMovementInput().moveForward = -1.0f;
                break;
            }
            case PATH: {
                if (Pathfind.points.isEmpty()) {
                    return;
                }
                event.getMovementInput().moveForward = 1.0f;
                if (AutoWalk.mc.player.isInWater() || AutoWalk.mc.player.isInLava()) {
                    AutoWalk.mc.player.movementInput.jump = true;
                } else if (AutoWalk.mc.player.collidedHorizontally && AutoWalk.mc.player.onGround) {
                    AutoWalk.mc.player.jump();
                }
                if (!ModuleManager.isModuleEnabled("Pathfind") || Pathfind.points.isEmpty()) {
                    return;
                }
                PathPoint next = Pathfind.points.get(0);
                this.lookAt(next);
            }
        }
    }, new Predicate[0]);

    private void lookAt(PathPoint pathPoint) {
        double[] v = EntityUtil.calculateLookAt((float)pathPoint.x + 0.5f, pathPoint.y, (float)pathPoint.z + 0.5f, (EntityPlayer)AutoWalk.mc.player);
        AutoWalk.mc.player.rotationYaw = (float)v[0];
        AutoWalk.mc.player.rotationPitch = (float)v[1];
    }

    private static enum AutoWalkMode {
        FORWARD,
        BACKWARDS,
        PATH;

    }
}

