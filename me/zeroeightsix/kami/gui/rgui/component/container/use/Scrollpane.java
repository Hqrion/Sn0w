/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.gui.rgui.component.container.use;

import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.container.OrganisedContainer;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.UpdateListener;
import me.zeroeightsix.kami.gui.rgui.layout.Layout;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Scrollpane
extends OrganisedContainer {
    int scrolledX;
    int maxScrollX;
    int scrolledY;
    int maxScrollY;
    boolean doScrollX = false;
    boolean doScrollY = true;
    boolean canScrollX = false;
    boolean canScrollY = false;
    boolean lockWidth = false;
    boolean lockHeight = false;
    int step = 22;

    public Scrollpane(Theme theme, Layout layout, int width, int height) {
        super(theme, layout);
        this.setWidth(width);
        this.setHeight(height);
        this.scrolledX = 0;
        this.scrolledY = 0;
        this.addRenderListener(new RenderListener(){
            int translatex;
            int translatey;

            @Override
            public void onPreRender() {
                this.translatex = Scrollpane.this.scrolledX;
                this.translatey = Scrollpane.this.scrolledY;
                int[] real = GUI.calculateRealPosition(Scrollpane.this);
                int scale = DisplayGuiScreen.getScale();
                GL11.glScissor((int)(Scrollpane.this.getX() * scale + real[0] * scale - Scrollpane.this.getParent().getOriginOffsetX() - 1), (int)(Display.getHeight() - Scrollpane.this.getHeight() * scale - real[1] * scale - 1), (int)(Scrollpane.this.getWidth() * scale + Scrollpane.this.getParent().getOriginOffsetX() * scale + 1), (int)(Scrollpane.this.getHeight() * scale + 1));
                GL11.glEnable((int)3089);
            }

            @Override
            public void onPostRender() {
                GL11.glDisable((int)3089);
            }
        });
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                if (event.getY() > Scrollpane.this.getHeight() || event.getX() > Scrollpane.this.getWidth() || event.getX() < 0 || event.getY() < 0) {
                    event.cancel();
                }
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
                if (Scrollpane.this.canScrollY() && (!Scrollpane.this.canScrollX() || Scrollpane.this.scrolledX == 0 || !Scrollpane.this.isDoScrollX()) && Scrollpane.this.isDoScrollY()) {
                    if (event.isUp() && Scrollpane.this.getScrolledY() > 0) {
                        Scrollpane.this.setScrolledY(Math.max(0, Scrollpane.this.getScrolledY() - Scrollpane.this.step));
                        return;
                    }
                    if (!event.isUp() && Scrollpane.this.getScrolledY() < Scrollpane.this.getMaxScrollY()) {
                        Scrollpane.this.setScrolledY(Math.min(Scrollpane.this.getMaxScrollY(), Scrollpane.this.getScrolledY() + Scrollpane.this.step));
                        return;
                    }
                }
                if (Scrollpane.this.canScrollX() && Scrollpane.this.isDoScrollX()) {
                    if (event.isUp() && Scrollpane.this.getScrolledX() > 0) {
                        Scrollpane.this.setScrolledX(Math.max(0, Scrollpane.this.getScrolledX() - Scrollpane.this.step));
                        return;
                    }
                    if (!event.isUp() && Scrollpane.this.getScrolledX() < Scrollpane.this.getMaxScrollX()) {
                        Scrollpane.this.setScrolledX(Math.min(Scrollpane.this.getMaxScrollX(), Scrollpane.this.getScrolledX() + Scrollpane.this.step));
                        return;
                    }
                }
            }
        });
        this.addUpdateListener(new UpdateListener(){

            public void updateSize(Component component, int oldWidth, int oldHeight) {
                Scrollpane.this.performCalculations();
            }

            public void updateLocation(Component component, int oldX, int oldY) {
                Scrollpane.this.performCalculations();
            }
        });
    }

    @Override
    public void setWidth(int width) {
        if (!this.lockWidth) {
            super.setWidth(width);
        }
    }

    @Override
    public void setHeight(int height) {
        if (!this.lockHeight) {
            super.setHeight(height);
        }
    }

    @Override
    public Container addChild(Component ... component) {
        super.addChild(component);
        this.performCalculations();
        return this;
    }

    private void performCalculations() {
        int farX = 0;
        int farY = 0;
        for (Component c : this.getChildren()) {
            farX = Math.max(this.getScrolledX() + c.getX() + c.getWidth(), farX);
            farY = Math.max(this.getScrolledY() + c.getY() + c.getHeight(), farY);
        }
        this.canScrollX = farX > this.getWidth();
        this.maxScrollX = farX - this.getWidth();
        this.canScrollY = farY > this.getHeight();
        this.maxScrollY = farY - this.getHeight();
    }

    public boolean canScrollX() {
        return this.canScrollX;
    }

    public boolean canScrollY() {
        return this.canScrollY;
    }

    public boolean isDoScrollX() {
        return this.doScrollX;
    }

    public boolean isDoScrollY() {
        return this.doScrollY;
    }

    public void setDoScrollY(boolean doScrollY) {
        this.doScrollY = doScrollY;
    }

    public void setDoScrollX(boolean doScrollX) {
        this.doScrollX = doScrollX;
    }

    public void setScrolledX(int scrolledX) {
        int a = this.getScrolledX();
        this.scrolledX = scrolledX;
        int dif = this.getScrolledX() - a;
        for (Component component : this.getChildren()) {
            component.setX(component.getX() - dif);
        }
    }

    public void setScrolledY(int scrolledY) {
        int a = this.getScrolledY();
        this.scrolledY = scrolledY;
        int dif = this.getScrolledY() - a;
        for (Component component : this.getChildren()) {
            component.setY(component.getY() - dif);
        }
    }

    public int getScrolledX() {
        return this.scrolledX;
    }

    public int getScrolledY() {
        return this.scrolledY;
    }

    public int getMaxScrollX() {
        return this.maxScrollX;
    }

    public int getMaxScrollY() {
        return this.maxScrollY;
    }

    public Scrollpane setLockHeight(boolean lockHeight) {
        this.lockHeight = lockHeight;
        return this;
    }

    public Scrollpane setLockWidth(boolean lockWidth) {
        this.lockWidth = lockWidth;
        return this;
    }

    @Override
    public boolean penetrateTest(int x, int y) {
        return x > 0 && x < this.getWidth() && y > 0 && y < this.getHeight();
    }
}

