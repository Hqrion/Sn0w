/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.InputUpdateEvent
 */
package me.zeroeightsix.kami.module.modules.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;

@Module.Info(name="NoSlowDown", category=Module.Category.MOVEMENT)
public class NoSlowDown
extends Module {
    @EventHandler
    private Listener<InputUpdateEvent> eventListener = new Listener<InputUpdateEvent>(event -> {
        if (NoSlowDown.mc.field_71439_g.func_184587_cr() && !NoSlowDown.mc.field_71439_g.func_184218_aH()) {
            event.getMovementInput().field_78902_a *= 5.0f;
            event.getMovementInput().field_192832_b *= 5.0f;
        }
    }, new Predicate[0]);
}

