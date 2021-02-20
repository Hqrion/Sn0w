/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class KamiSettingsPanelUI
extends AbstractComponentUI<SettingsPanel> {
    private GuiManager guiManager;

    public KamiSettingsPanelUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
    }

    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.9f);
        RenderHelper.drawFilledRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight());
        GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        GL11.glLineWidth((float)1.5f);
        RenderHelper.drawRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight());
    }
}

