/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import java.awt.Color;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.kami.RootSmallFontRenderer;
import me.zeroeightsix.kami.gui.kami.component.ColorizedCheckButton;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootCheckButtonUI;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class RootColorizedCheckButtonUI
extends RootCheckButtonUI<ColorizedCheckButton> {
    RootSmallFontRenderer ff = new RootSmallFontRenderer();
    private GuiManager guiManager;

    public RootColorizedCheckButtonUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
        this.downColourNormal = new Color(190, 190, 190);
    }

    @Override
    public void renderComponent(CheckButton component, FontRenderer aa) {
        GL11.glColor4f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue(), (float)component.getOpacity());
        if (component.isHovered() || component.isPressed()) {
            GL11.glColor4f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue(), (float)component.getOpacity());
        }
        if (component.isToggled()) {
            GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        }
        GL11.glLineWidth((float)2.5f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glEnd();
        Color idleColour = component.isToggled() ? this.idleColourNormal.brighter() : this.idleColourNormal;
        Color downColour = component.isToggled() ? this.downColourNormal.brighter() : this.downColourNormal;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        this.ff.drawString(component.getWidth() / 2 - KamiGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? downColour : idleColour, component.getName());
        GL11.glDisable((int)3553);
    }
}

