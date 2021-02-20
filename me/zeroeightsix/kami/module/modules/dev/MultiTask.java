//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

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
        if (Mouse.getEventButtonState() && MultiTask.mc.player != null && MultiTask.mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && MultiTask.mc.player.isHandActive() && (MultiTask.mc.gameSettings.keyBindAttack.isPressed() || Mouse.getEventButton() == MultiTask.mc.gameSettings.keyBindAttack.getKeyCode())) {
            MultiTask.mc.playerController.attackEntity((EntityPlayer)MultiTask.mc.player, MultiTask.mc.objectMouseOver.entityHit);
            MultiTask.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Override
    public void onDisable() {
        this.enable();
    }
}

