//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5.0f;
            event.getMovementInput().moveForward *= 5.0f;
        }
    }, new Predicate[0]);
}

