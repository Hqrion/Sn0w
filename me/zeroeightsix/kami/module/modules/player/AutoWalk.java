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
                event.getMovementInput().field_192832_b = 1.0f;
                break;
            }
            case BACKWARDS: {
                event.getMovementInput().field_192832_b = -1.0f;
                break;
            }
            case PATH: {
                if (Pathfind.points.isEmpty()) {
                    return;
                }
                event.getMovementInput().field_192832_b = 1.0f;
                if (AutoWalk.mc.field_71439_g.func_70090_H() || AutoWalk.mc.field_71439_g.func_180799_ab()) {
                    AutoWalk.mc.field_71439_g.field_71158_b.field_78901_c = true;
                } else if (AutoWalk.mc.field_71439_g.field_70123_F && AutoWalk.mc.field_71439_g.field_70122_E) {
                    AutoWalk.mc.field_71439_g.func_70664_aZ();
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
        double[] v = EntityUtil.calculateLookAt((float)pathPoint.field_75839_a + 0.5f, pathPoint.field_75837_b, (float)pathPoint.field_75838_c + 0.5f, (EntityPlayer)AutoWalk.mc.field_71439_g);
        AutoWalk.mc.field_71439_g.field_70177_z = (float)v[0];
        AutoWalk.mc.field_71439_g.field_70125_A = (float)v[1];
    }

    private static enum AutoWalkMode {
        FORWARD,
        BACKWARDS,
        PATH;

    }
}

