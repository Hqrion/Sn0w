//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiEditSign
 *  net.minecraft.tileentity.TileEntitySign
 *  net.minecraft.util.text.TextComponentString
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.io.IOException;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;

@Module.Info(name="ColourSign", description="Allows ingame colouring of text on signs", category=Module.Category.MISC)
public class ColourSign
extends Module {
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> eventListener = new Listener<GuiScreenEvent.Displayed>(event -> {
        if (event.getScreen() instanceof GuiEditSign && this.isEnabled()) {
            event.setScreen((GuiScreen)new KamiGuiEditSign(((GuiEditSign)event.getScreen()).tileSign));
        }
    }, new Predicate[0]);

    private class KamiGuiEditSign
    extends GuiEditSign {
        public KamiGuiEditSign(TileEntitySign teSign) {
            super(teSign);
        }

        public void initGui() {
            super.initGui();
        }

        protected void actionPerformed(GuiButton button) throws IOException {
            if (button.id == 0) {
                this.tileSign.signText[this.editLine] = new TextComponentString(this.tileSign.signText[this.editLine].getFormattedText().replaceAll("(" + Command.SECTIONSIGN() + ")(.)", "$1$1$2$2"));
            }
            super.actionPerformed(button);
        }

        protected void keyTyped(char typedChar, int keyCode) throws IOException {
            super.keyTyped(typedChar, keyCode);
            String s = ((TextComponentString)this.tileSign.signText[this.editLine]).getText();
            s = s.replace("&", Command.SECTIONSIGN() + "");
            this.tileSign.signText[this.editLine] = new TextComponentString(s);
        }
    }
}

