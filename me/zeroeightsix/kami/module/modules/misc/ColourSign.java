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
            event.setScreen((GuiScreen)new KamiGuiEditSign(((GuiEditSign)event.getScreen()).field_146848_f));
        }
    }, new Predicate[0]);

    private class KamiGuiEditSign
    extends GuiEditSign {
        public KamiGuiEditSign(TileEntitySign teSign) {
            super(teSign);
        }

        public void func_73866_w_() {
            super.func_73866_w_();
        }

        protected void func_146284_a(GuiButton button) throws IOException {
            if (button.field_146127_k == 0) {
                this.field_146848_f.field_145915_a[this.field_146851_h] = new TextComponentString(this.field_146848_f.field_145915_a[this.field_146851_h].func_150254_d().replaceAll("(" + Command.SECTIONSIGN() + ")(.)", "$1$1$2$2"));
            }
            super.func_146284_a(button);
        }

        protected void func_73869_a(char typedChar, int keyCode) throws IOException {
            super.func_73869_a(typedChar, keyCode);
            String s = ((TextComponentString)this.field_146848_f.field_145915_a[this.field_146851_h]).func_150265_g();
            s = s.replace("&", Command.SECTIONSIGN() + "");
            this.field_146848_f.field_145915_a[this.field_146851_h] = new TextComponentString(s);
        }
    }
}

