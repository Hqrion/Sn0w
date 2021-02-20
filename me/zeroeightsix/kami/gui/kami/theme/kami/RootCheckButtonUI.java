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
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class RootCheckButtonUI<T extends CheckButton>
extends AbstractComponentUI<CheckButton> {
    private GuiManager guiManager;
    protected Color idleColourNormal;
    protected Color downColourNormal;

    public RootCheckButtonUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
        this.idleColourNormal = new Color(200, 200, 200);
        this.downColourNormal = new Color(190, 190, 190);
    }

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        int c;
        GL11.glColor4f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue(), (float)component.getOpacity());
        if (component.isToggled()) {
            GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        }
        if (component.isHovered() || component.isPressed()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)component.getOpacity());
        }
        Color color = new Color(this.guiManager.getGuiRed(), this.guiManager.getGuiGreen(), this.guiManager.getGuiBlue());
        String text = component.getName();
        int n = component.isPressed() ? 0xAAAAAA : (c = component.isToggled() ? color.getRGB() : 0xDDDDDD);
        if (component.isHovered()) {
            c = color.brighter().getRGB();
        }
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        KamiGUI.fontRenderer.drawString(component.getWidth() / 18, KamiGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KamiGUI.fontRenderer.getStringWidth(component.getName()) + 13);
        component.setHeight(KamiGUI.fontRenderer.getFontHeight() + 2);
    }
}

