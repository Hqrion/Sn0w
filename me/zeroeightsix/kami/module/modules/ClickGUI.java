//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.zeroeightsix.kami.module.modules;

import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.gui.GuiScreen;

@Module.Info(name="clickGUI", description="Opens the Click GUI", category=Module.Category.HIDDEN)
public class ClickGUI
extends Module {
    public ClickGUI() {
        this.getBind().setKey(21);
    }

    @Override
    protected void onEnable() {
        if (!(ClickGUI.mc.currentScreen instanceof DisplayGuiScreen)) {
            mc.displayGuiScreen((GuiScreen)new DisplayGuiScreen(ClickGUI.mc.currentScreen));
        }
        this.disable();
    }
}

