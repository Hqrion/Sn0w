/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$MouseInputEvent
 *  org.lwjgl.input.Mouse
 */
package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

@Module.Info(name="MultiTask", description="Lets you mine and eat simultaneously", category=Module.Category.DEV)
public class MultiTask
extends Module {
    private Setting<Boolean> description = this.register(Settings.b("This module is always enabled", true));

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState() && MultiTask.mc.field_71439_g != null && MultiTask.mc.field_71476_x.field_72313_a.equals((Object)RayTraceResult.Type.ENTITY) && MultiTask.mc.field_71439_g.func_184587_cr() && (MultiTask.mc.field_71474_y.field_74312_F.func_151468_f() || Mouse.getEventButton() == MultiTask.mc.field_71474_y.field_74312_F.func_151463_i())) {
            MultiTask.mc.field_71442_b.func_78764_a((EntityPlayer)MultiTask.mc.field_71439_g, MultiTask.mc.field_71476_x.field_72308_g);
            MultiTask.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        }
    }

    @Override
    public void onDisable() {
        this.enable();
    }
}

