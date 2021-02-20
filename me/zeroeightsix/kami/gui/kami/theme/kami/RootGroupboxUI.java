/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Groupbox;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class RootGroupboxUI
extends AbstractComponentUI<Groupbox> {
    private GuiManager guiManager;

    public RootGroupboxUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
    }

    @Override
    public void renderComponent(Groupbox component, FontRenderer fontRenderer) {
        GL11.glLineWidth((float)1.0f);
        fontRenderer.drawString(1, 1, component.getName());
        GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        GL11.glDisable((int)3553);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glEnd();
    }

    @Override
    public void handleMouseDown(Groupbox component, int x, int y, int button) {
    }

    @Override
    public void handleAddComponent(Groupbox component, Container container) {
        component.setWidth(100);
        component.setHeight(100);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
    }
}

