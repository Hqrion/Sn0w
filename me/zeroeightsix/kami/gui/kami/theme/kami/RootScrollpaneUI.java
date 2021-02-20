/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.gui.kami.RenderHelper;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Scrollpane;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.util.GuiManager;
import org.lwjgl.opengl.GL11;

public class RootScrollpaneUI
extends AbstractComponentUI<Scrollpane> {
    private GuiManager guiManager;
    long lastScroll;
    Component scrollComponent;
    float barLife;
    boolean dragBar;
    int dY;
    double hovering;

    public RootScrollpaneUI() {
        this.guiManager = KamiMod.getInstance().guiManager;
        this.lastScroll = 0L;
        this.scrollComponent = null;
        this.barLife = 1220.0f;
        this.dragBar = false;
        this.dY = 0;
        this.hovering = 0.0;
    }

    @Override
    public void renderComponent(Scrollpane component, FontRenderer fontRenderer) {
    }

    @Override
    public void handleAddComponent(final Scrollpane component, Container container) {
        component.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                if ((float)(System.currentTimeMillis() - RootScrollpaneUI.this.lastScroll) < RootScrollpaneUI.this.barLife && RootScrollpaneUI.this.scrollComponent.liesIn(component) && component.canScrollY()) {
                    double progress = (double)component.getScrolledY() / (double)component.getMaxScrollY();
                    int barHeight = 30;
                    int y = (int)((double)(component.getHeight() - barHeight) * progress);
                    if (event.getX() > component.getWidth() - 10 && event.getY() > y && event.getY() < y + barHeight) {
                        RootScrollpaneUI.this.dragBar = true;
                        RootScrollpaneUI.this.dY = event.getY() - y;
                        event.cancel();
                    }
                }
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                RootScrollpaneUI.this.dragBar = false;
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                if (RootScrollpaneUI.this.dragBar) {
                    double progress = (double)event.getY() / (double)component.getHeight();
                    progress = Math.max(Math.min(progress, 1.0), 0.0);
                    component.setScrolledY((int)((double)component.getMaxScrollY() * progress));
                    event.cancel();
                }
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
                RootScrollpaneUI.this.lastScroll = System.currentTimeMillis();
                RootScrollpaneUI.this.scrollComponent = event.getComponent();
            }
        });
        component.addRenderListener(new RenderListener(){

            @Override
            public void onPreRender() {
            }

            @Override
            public void onPostRender() {
                if (RootScrollpaneUI.this.dragBar) {
                    RootScrollpaneUI.this.lastScroll = System.currentTimeMillis();
                }
                if ((float)(System.currentTimeMillis() - RootScrollpaneUI.this.lastScroll) < RootScrollpaneUI.this.barLife && RootScrollpaneUI.this.scrollComponent.liesIn(component) && component.canScrollY()) {
                    float alpha = Math.min(1.0f, (RootScrollpaneUI.this.barLife - (float)(System.currentTimeMillis() - RootScrollpaneUI.this.lastScroll)) / 100.0f) / 3.0f;
                    if (RootScrollpaneUI.this.dragBar) {
                        alpha = 0.4f;
                    }
                    GL11.glColor4f((float)RootScrollpaneUI.this.guiManager.getGuiRed(), (float)RootScrollpaneUI.this.guiManager.getGuiGreen(), (float)RootScrollpaneUI.this.guiManager.getGuiBlue(), (float)alpha);
                    GL11.glDisable((int)3553);
                    int barHeight = 30;
                    double progress = (double)component.getScrolledY() / (double)component.getMaxScrollY();
                    int y = (int)((double)(component.getHeight() - barHeight) * progress);
                    RenderHelper.drawRoundedRectangle(component.getWidth() - 6, y, 4.0f, barHeight, 1.0f);
                }
            }
        });
    }
}

