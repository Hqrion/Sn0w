/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.RootSmallFontRenderer;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.use.Slider;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class RootSliderUI
extends AbstractComponentUI<Slider> {
    RootSmallFontRenderer smallFontRenderer = new RootSmallFontRenderer();
    private GuiManager guiManager;

    public RootSliderUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
    }

    @Override
    public void renderComponent(Slider component, FontRenderer aa) {
        GL11.glColor4f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue(), (float)component.getOpacity());
        GL11.glLineWidth((float)2.5f);
        int height = component.getHeight();
        double value = component.getValue();
        double w = (double)component.getWidth() * ((value - component.getMinimum()) / (component.getMaximum() - component.getMinimum()));
        float downscale = 1.1f;
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)0.0, (double)((float)height / downscale));
        GL11.glVertex2d((double)w, (double)((float)height / downscale));
        GL11.glColor3f((float)0.33f, (float)0.33f, (float)0.33f);
        GL11.glVertex2d((double)w, (double)((float)height / downscale));
        GL11.glVertex2d((double)component.getWidth(), (double)((float)height / downscale));
        GL11.glEnd();
        GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        RenderHelper.drawCircle((int)w, (float)height / downscale, 2.0f);
        String s = value + "";
        if (component.isPressed()) {
            w -= (double)(this.smallFontRenderer.getStringWidth(s) / 2);
            w = Math.max(0.0, Math.min(w, (double)(component.getWidth() - this.smallFontRenderer.getStringWidth(s))));
            this.smallFontRenderer.drawString((int)w, 0, s);
        } else {
            this.smallFontRenderer.drawString(0, 0, component.getText());
            this.smallFontRenderer.drawString(component.getWidth() - this.smallFontRenderer.getStringWidth(s), 0, s);
        }
        GL11.glDisable((int)3553);
    }

    @Override
    public void handleAddComponent(Slider component, Container container) {
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight() + 2);
        component.setWidth(this.smallFontRenderer.getStringWidth(component.getText()) + this.smallFontRenderer.getStringWidth(component.getMaximum() + "") + 3);
    }
}

