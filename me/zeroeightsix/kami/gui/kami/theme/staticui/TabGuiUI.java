/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.staticui;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.kami.component.TabGUI;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.GuiManager;
import me.zeroeightsix.kami.util.Wrapper;
import org.lwjgl.opengl.GL11;

public class TabGuiUI
extends AbstractComponentUI<TabGUI> {
    long lastms = System.currentTimeMillis();
    private GuiManager guiManager;

    public TabGuiUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
    }

    @Override
    public void renderComponent(TabGUI component, FontRenderer fontRenderer) {
        boolean updatelerp = false;
        float difference = System.currentTimeMillis() - this.lastms;
        if (difference > 2.0f) {
            component.selectedLerpY += ((float)(component.selected * 10) - component.selectedLerpY) * difference * 0.02f;
            updatelerp = true;
            this.lastms = System.currentTimeMillis();
        }
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        int x = 2;
        int y = 2;
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        this.drawBox(0, 0, component.width, component.height);
        KamiGUI.primaryColour.setGLColour();
        GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)0.0, (double)component.selectedLerpY);
        GL11.glVertex2d((double)0.0, (double)(component.selectedLerpY + 10.0f));
        GL11.glVertex2d((double)component.width, (double)(component.selectedLerpY + 10.0f));
        GL11.glVertex2d((double)component.width, (double)component.selectedLerpY);
        GL11.glEnd();
        int textY = 1;
        for (int i = 0; i < component.tabs.size(); ++i) {
            String tabName = component.tabs.get((int)i).name;
            GL11.glEnable((int)3553);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Wrapper.getFontRenderer().drawStringWithShadow(2, textY, 255, 255, 255, "\u00a77" + tabName);
            textY += 10;
        }
        if (component.tabOpened) {
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            TabGUI.Tab tab = component.tabs.get(component.selected);
            GL11.glTranslatef((float)(component.width + 2), (float)0.0f, (float)0.0f);
            this.drawBox(0, 0, tab.width, tab.height);
            if (updatelerp) {
                tab.lerpSelectY += ((float)(tab.selected * 10) - tab.lerpSelectY) * difference * 0.02f;
            }
            GL11.glColor3f((float)0.0388f, (float)0.97f, (float)0.8303f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)0.0, (double)tab.lerpSelectY);
            GL11.glVertex2d((double)0.0, (double)(tab.lerpSelectY + 10.0f));
            GL11.glVertex2d((double)tab.width, (double)(tab.lerpSelectY + 10.0f));
            GL11.glVertex2d((double)tab.width, (double)tab.lerpSelectY);
            GL11.glEnd();
            int tabTextY = 1;
            for (int i = 0; i < tab.features.size(); ++i) {
                Module feature = tab.features.get(i);
                String fName = (feature.isEnabled() ? "\u00a7c" : "\u00a77") + feature.getName();
                GL11.glEnable((int)3553);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                Wrapper.getFontRenderer().drawStringWithShadow(2, tabTextY, 255, 255, 255, fName);
                tabTextY += 10;
            }
            GL11.glDisable((int)3089);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2884);
    }

    private void drawBox(int x1, int y1, int x2, int y2) {
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.6f);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glEnd();
        double xi1 = (double)x1 - 0.1;
        double xi2 = (double)x2 + 0.1;
        double yi1 = (double)y1 - 0.1;
        double yi2 = (double)y2 + 0.1;
        GL11.glLineWidth((float)1.5f);
        GL11.glColor3f((float)this.guiManager.getGuiRed(), (float)this.guiManager.getGuiGreen(), (float)this.guiManager.getGuiBlue());
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xi1, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi2);
        GL11.glVertex2d((double)xi1, (double)yi2);
        GL11.glEnd();
        GL11.glBegin((int)9);
        GL11.glColor4f((float)0.125f, (float)0.125f, (float)0.125f, (float)0.75f);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y1);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)(xi2 += 0.9), (double)(yi1 -= 0.9));
        GL11.glVertex2d((double)(xi1 -= 0.9), (double)yi1);
        GL11.glVertex2d((double)xi1, (double)(yi2 += 0.9));
        GL11.glColor4f((float)0.125f, (float)0.125f, (float)0.125f, (float)0.75f);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glEnd();
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y1);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)xi2, (double)yi1);
        GL11.glVertex2d((double)xi2, (double)yi2);
        GL11.glVertex2d((double)xi1, (double)yi2);
        GL11.glColor4f((float)0.125f, (float)0.125f, (float)0.125f, (float)0.75f);
        GL11.glVertex2d((double)x1, (double)y2);
        GL11.glEnd();
    }
}

