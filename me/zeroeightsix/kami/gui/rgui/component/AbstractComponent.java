/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component;

import java.util.ArrayList;
import me.zeroeightsix.kami.gui.kami.DisplayGuiScreen;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.TickListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.UpdateListener;
import me.zeroeightsix.kami.gui.rgui.poof.IPoof;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.render.ComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

public abstract class AbstractComponent
implements Component {
    int x;
    int y;
    int width;
    int height;
    int minWidth = Integer.MIN_VALUE;
    int minHeight = Integer.MIN_VALUE;
    int maxWidth = Integer.MAX_VALUE;
    int maxHeight = Integer.MAX_VALUE;
    protected int priority = 0;
    private Setting<Boolean> visible = Settings.b("Visible", true);
    float opacity = 1.0f;
    private boolean focus = false;
    ComponentUI ui;
    Theme theme;
    Container parent;
    boolean hover = false;
    boolean press = false;
    boolean drag = false;
    boolean affectlayout = true;
    ArrayList<MouseListener> mouseListeners = new ArrayList();
    ArrayList<RenderListener> renderListeners = new ArrayList();
    ArrayList<KeyListener> keyListeners = new ArrayList();
    ArrayList<UpdateListener> updateListeners = new ArrayList();
    ArrayList<TickListener> tickListeners = new ArrayList();
    ArrayList<IPoof> poofs = new ArrayList();
    boolean workingy = false;
    boolean workingx = false;

    public AbstractComponent() {
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.press = true;
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.press = false;
                AbstractComponent.this.drag = false;
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                AbstractComponent.this.drag = true;
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
    }

    @Override
    public ComponentUI getUI() {
        if (this.ui == null) {
            this.ui = this.getTheme().getUIForComponent(this);
        }
        return this.ui;
    }

    @Override
    public Container getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public void setFocussed(boolean focus) {
        this.focus = focus;
    }

    @Override
    public boolean isFocussed() {
        return this.focus;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setY(int y) {
        int oldX = this.getX();
        int oldY = this.getY();
        this.y = y;
        if (!this.workingy) {
            this.workingy = true;
            this.getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            if (this.getParent() != null) {
                this.getParent().getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            }
            this.workingy = false;
        }
    }

    @Override
    public void setX(int x) {
        int oldX = this.getX();
        int oldY = this.getY();
        this.x = x;
        if (!this.workingx) {
            this.workingx = true;
            this.getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            if (this.getParent() != null) {
                this.getParent().getUpdateListeners().forEach(listener -> listener.updateLocation(this, oldX, oldY));
            }
            this.workingx = false;
        }
    }

    @Override
    public void setWidth(int width) {
        width = Math.max(this.getMinimumWidth(), Math.min(width, this.getMaximumWidth()));
        int oldWidth = this.getWidth();
        int oldHeight = this.getHeight();
        this.width = width;
        this.getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        if (this.getParent() != null) {
            this.getParent().getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        }
    }

    @Override
    public void setHeight(int height) {
        height = Math.max(this.getMinimumHeight(), Math.min(height, this.getMaximumHeight()));
        int oldWidth = this.getWidth();
        int oldHeight = this.getHeight();
        this.height = height;
        this.getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        if (this.getParent() != null) {
            this.getParent().getUpdateListeners().forEach(listener -> listener.updateSize(this, oldWidth, oldHeight));
        }
    }

    @Override
    public boolean isVisible() {
        return this.visible.getValue();
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible.setValue(visible);
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public void kill() {
        this.setVisible(false);
    }

    private boolean isMouseOver() {
        int[] real = GUI.calculateRealPosition(this);
        int mx = DisplayGuiScreen.mouseX;
        int my = DisplayGuiScreen.mouseY;
        return real[0] <= mx && real[1] <= my && real[0] + this.getWidth() >= mx && real[1] + this.getHeight() >= my;
    }

    @Override
    public boolean isHovered() {
        return this.isMouseOver() && !this.press;
    }

    @Override
    public boolean isPressed() {
        return this.press;
    }

    @Override
    public ArrayList<MouseListener> getMouseListeners() {
        return this.mouseListeners;
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        if (!this.mouseListeners.contains(listener)) {
            this.mouseListeners.add(listener);
        }
    }

    @Override
    public ArrayList<RenderListener> getRenderListeners() {
        return this.renderListeners;
    }

    @Override
    public void addRenderListener(RenderListener listener) {
        if (!this.renderListeners.contains(listener)) {
            this.renderListeners.add(listener);
        }
    }

    @Override
    public ArrayList<KeyListener> getKeyListeners() {
        return this.keyListeners;
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        if (!this.keyListeners.contains(listener)) {
            this.keyListeners.add(listener);
        }
    }

    @Override
    public ArrayList<UpdateListener> getUpdateListeners() {
        return this.updateListeners;
    }

    @Override
    public void addUpdateListener(UpdateListener listener) {
        if (!this.updateListeners.contains(listener)) {
            this.updateListeners.add(listener);
        }
    }

    @Override
    public ArrayList<TickListener> getTickListeners() {
        return this.tickListeners;
    }

    @Override
    public void addTickListener(TickListener listener) {
        if (!this.tickListeners.contains(listener)) {
            this.tickListeners.add(listener);
        }
    }

    @Override
    public void addPoof(IPoof poof) {
        this.poofs.add(poof);
    }

    @Override
    public void callPoof(Class<? extends IPoof> target, PoofInfo info) {
        for (IPoof poof : this.poofs) {
            if (!target.isAssignableFrom(poof.getClass()) || !poof.getComponentClass().isAssignableFrom(this.getClass())) continue;
            poof.execute(this, info);
        }
    }

    @Override
    public boolean liesIn(Component container) {
        if (container.equals(this)) {
            return true;
        }
        if (container instanceof Container) {
            for (Component component : ((Container)container).getChildren()) {
                if (component.equals(this)) {
                    return true;
                }
                boolean liesin = false;
                if (component instanceof Container) {
                    liesin = this.liesIn((Container)component);
                }
                if (!liesin) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public float getOpacity() {
        return this.opacity;
    }

    @Override
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public int getMaximumHeight() {
        return this.maxHeight;
    }

    @Override
    public int getMaximumWidth() {
        return this.maxWidth;
    }

    @Override
    public int getMinimumHeight() {
        return this.minHeight;
    }

    @Override
    public int getMinimumWidth() {
        return this.minWidth;
    }

    @Override
    public Component setMaximumWidth(int width) {
        this.maxWidth = width;
        return this;
    }

    @Override
    public Component setMaximumHeight(int height) {
        this.maxHeight = height;
        return this;
    }

    @Override
    public Component setMinimumWidth(int width) {
        this.minWidth = width;
        return this;
    }

    @Override
    public Component setMinimumHeight(int height) {
        this.minHeight = height;
        return this;
    }

    @Override
    public boolean doAffectLayout() {
        return this.affectlayout;
    }

    @Override
    public void setAffectLayout(boolean flag) {
        this.affectlayout = flag;
    }
}

