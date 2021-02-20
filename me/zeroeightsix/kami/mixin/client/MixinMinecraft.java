//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.client.gui.GuiIngame
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.crash.CrashReport
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public class MixinMinecraft {
    @Shadow
    WorldClient world;
    @Shadow
    EntityPlayerSP player;
    @Shadow
    GuiScreen currentScreen;
    @Shadow
    GameSettings gameSettings;
    @Shadow
    GuiIngame ingameGUI;
    @Shadow
    boolean skipRenderWorld;
    @Shadow
    SoundHandler soundHandler;
    @Shadow
    public PlayerControllerMP playerController;

    @Inject(method={"displayGuiScreen"}, at={@At(value="HEAD")}, cancellable=true)
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
        GuiScreenEvent.Closed screenEvent = new GuiScreenEvent.Closed(Wrapper.getMinecraft().currentScreen);
        KamiMod.EVENT_BUS.post(screenEvent);
        GuiScreenEvent.Displayed screenEvent1 = new GuiScreenEvent.Displayed(guiScreenIn);
        KamiMod.EVENT_BUS.post(screenEvent1);
        guiScreenIn = screenEvent1.getScreen();
        if (guiScreenIn == null && this.world == null) {
            guiScreenIn = new GuiMainMenu();
        } else if (guiScreenIn == null && this.player.getHealth() <= 0.0f) {
            guiScreenIn = new GuiGameOver(null);
        }
        GuiScreen old = this.currentScreen;
        GuiOpenEvent event = new GuiOpenEvent(guiScreenIn);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        guiScreenIn = event.getGui();
        if (old != null && guiScreenIn != old) {
            old.onGuiClosed();
        }
        if (guiScreenIn instanceof GuiMainMenu || guiScreenIn instanceof GuiMultiplayer) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages(true);
        }
        this.currentScreen = guiScreenIn;
        if (guiScreenIn != null) {
            Minecraft.getMinecraft().setIngameNotInFocus();
            KeyBinding.unPressAllKeys();
            while (Mouse.next()) {
            }
            while (Keyboard.next()) {
            }
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            guiScreenIn.setWorldAndResolution(Minecraft.getMinecraft(), i, j);
            this.skipRenderWorld = false;
        } else {
            this.soundHandler.resumeSounds();
            Minecraft.getMinecraft().setIngameFocus();
        }
        info.cancel();
    }

    @Redirect(method={"run"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
        this.save();
    }

    @Inject(method={"shutdown"}, at={@At(value="HEAD")})
    public void shutdown(CallbackInfo info) {
        this.save();
    }

    @Redirect(method={"sendClickBlockToController"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActive(EntityPlayerSP player) {
        if (ModuleManager.isModuleEnabled("BreakTweaks")) {
            return false;
        }
        return this.player.isHandActive();
    }

    @Redirect(method={"rightClickMouse"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean isHittingBlock(PlayerControllerMP playerControllerMP) {
        if (ModuleManager.isModuleEnabled("BreakTweaks")) {
            return false;
        }
        return this.playerController.getIsHittingBlock();
    }

    private void save() {
        System.out.println("Shutting down: saving KAMI configuration");
        KamiMod.saveConfiguration();
        System.out.println("Configuration saved.");
    }
}

