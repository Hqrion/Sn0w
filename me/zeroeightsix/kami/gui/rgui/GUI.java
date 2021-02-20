/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 */
package me.zeroeightsix.kami.gui.rgui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.AbstractContainer;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class GUI
extends AbstractContainer {
    Component focus = null;
    boolean press = false;
    int x = 0;
    int y = 0;
    int button = 0;
    int mx = 0;
    int my = 0;
    long lastMS = System.currentTimeMillis();

    public GUI(Theme theme) {
        super(theme);
    }

    public abstract void initializeGUI();

    public abstract void destroyGUI();

    public void updateGUI() {
        this.catchMouse();
        this.catchKey();
    }

    public void handleKeyDown(int key) {
        if (this.focus == null) {
            return;
        }
        this.focus.getTheme().getUIForComponent(this.focus).handleKeyDown(this.focus, key);
        ArrayList<Component> l = new ArrayList<Component>();
        for (Component p = this.focus; p != null; p = p.getParent()) {
            l.add(0, p);
        }
        KeyListener.KeyEvent event = new KeyListener.KeyEvent(key);
        for (Component a : l) {
            a.getKeyListeners().forEach(keyListener -> keyListener.onKeyDown(event));
        }
    }

    public void handleKeyUp(int key) {
        if (this.focus == null) {
            return;
        }
        this.focus.getTheme().getUIForComponent(this.focus).handleKeyUp(this.focus, key);
        ArrayList<Component> l = new ArrayList<Component>();
        for (Component p = this.focus; p != null; p = p.getParent()) {
            l.add(0, p);
        }
        KeyListener.KeyEvent event = new KeyListener.KeyEvent(key);
        for (Component a : l) {
            a.getKeyListeners().forEach(keyListener -> keyListener.onKeyUp(event));
        }
    }

    public void catchKey() {
        if (this.focus == null) {
            return;
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                this.handleKeyDown(Keyboard.getEventKey());
                continue;
            }
            this.handleKeyUp(Keyboard.getEventKey());
        }
    }

    public void handleMouseDown(int x, int y) {
        Component c = this.getComponentAt(x, y);
        int[] real = GUI.calculateRealPosition(c);
        if (this.focus != null) {
            this.focus.setFocussed(false);
        }
        this.focus = c;
        if (!c.equals(this)) {
            Component upperParent = c;
            while (!this.hasChild(upperParent)) {
                upperParent = upperParent.getParent();
            }
            this.children.remove(upperParent);
            this.children.add(upperParent);
            Collections.sort(this.children, new Comparator<Component>(){

                @Override
                public int compare(Component o1, Component o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });
        }
        this.focus.setFocussed(true);
        this.press = true;
        this.x = x;
        this.y = y;
        this.button = Mouse.getEventButton();
        this.getTheme().getUIForComponent(c).handleMouseDown(c, x - real[0], y - real[1], Mouse.getEventButton());
        ArrayList<Component> l = new ArrayList<Component>();
        for (Component p = this.focus; p != null; p = p.getParent()) {
            l.add(0, p);
        }
        int ex = x;
        int ey = y;
        MouseListener.MouseButtonEvent event = new MouseListener.MouseButtonEvent(ex, ey, this.button, this.focus);
        for (Component a : l) {
            event.setX(event.getX() - a.getX());
            event.setY(event.getY() - a.getY());
            if (a instanceof Container) {
                event.setX(event.getX() - ((Container)a).getOriginOffsetX());
                event.setY(event.getY() - ((Container)a).getOriginOffsetY());
            }
            a.getMouseListeners().forEach(listener -> listener.onMouseDown(event));
            if (!event.isCancelled()) continue;
            break;
        }
    }

    public void handleMouseRelease(int x, int y) {
        int button = Mouse.getEventButton();
        if (this.focus != null && button != -1) {
            int[] real = GUI.calculateRealPosition(this.focus);
            this.getTheme().getUIForComponent(this.focus).handleMouseRelease(this.focus, x - real[0], y - real[1], button);
            ArrayList<Component> l = new ArrayList<Component>();
            for (Component p = this.focus; p != null; p = p.getParent()) {
                l.add(0, p);
            }
            int ex = x;
            int ey = y;
            MouseListener.MouseButtonEvent event = new MouseListener.MouseButtonEvent(ex, ey, button, this.focus);
            for (Component a : l) {
                event.setX(event.getX() - a.getX());
                event.setY(event.getY() - a.getY());
                if (a instanceof Container) {
                    event.setX(event.getX() - ((Container)a).getOriginOffsetX());
                    event.setY(event.getY() - ((Container)a).getOriginOffsetY());
                }
                a.getMouseListeners().forEach(listener -> listener.onMouseRelease(event));
                if (!event.isCancelled()) continue;
                break;
            }
            this.press = false;
            return;
        }
        if (button != -1) {
            Component c = this.getComponentAt(x, y);
            int[] real = GUI.calculateRealPosition(c);
            this.getTheme().getUIForComponent(c).handleMouseRelease(c, x - real[0], y - real[1], button);
            ArrayList<Component> l = new ArrayList<Component>();
            for (Component p = c; p != null; p = p.getParent()) {
                l.add(0, p);
            }
            int ex = x;
            int ey = y;
            MouseListener.MouseButtonEvent event = new MouseListener.MouseButtonEvent(ex, ey, button, c);
            for (Component a : l) {
                event.setX(event.getX() - a.getX());
                event.setY(event.getY() - a.getY());
                if (a instanceof Container) {
                    event.setX(event.getX() - ((Container)a).getOriginOffsetX());
                    event.setY(event.getY() - ((Container)a).getOriginOffsetY());
                }
                a.getMouseListeners().forEach(listener -> listener.onMouseRelease(event));
                if (!event.isCancelled()) continue;
                break;
            }
            this.press = false;
        }
    }

    public void handleWheel(int x, int y, int step) {
        int intMouseMovement = step;
        if (intMouseMovement == 0) {
            return;
        }
        Component c = this.getComponentAt(x, y);
        int[] real = GUI.calculateRealPosition(c);
        this.getTheme().getUIForComponent(c).handleScroll(c, x - real[0], y - real[1], intMouseMovement, intMouseMovement > 0);
        ArrayList<Component> l = new ArrayList<Component>();
        for (Component p = c; p != null; p = p.getParent()) {
            l.add(0, p);
        }
        int ex = x;
        int ey = y;
        MouseListener.MouseScrollEvent event = new MouseListener.MouseScrollEvent(ex, ey, intMouseMovement > 0, c);
        for (Component a : l) {
            event.setX(event.getX() - a.getX());
            event.setY(event.getY() - a.getY());
            if (a instanceof Container) {
                event.setX(event.getX() - ((Container)a).getOriginOffsetX());
                event.setY(event.getY() - ((Container)a).getOriginOffsetY());
            }
            a.getMouseListeners().forEach(listener -> listener.onScroll(event));
            if (!event.isCancelled()) continue;
            break;
        }
    }

    public void handleMouseDrag(int x, int y) {
        int[] real = GUI.calculateRealPosition(this.focus);
        int ex = x - real[0];
        int ey = y - real[1];
        this.getTheme().getUIForComponent(this.focus).handleMouseDrag(this.focus, ex, ey, this.button);
        ArrayList<Component> l = new ArrayList<Component>();
        for (Component p = this.focus; p != null; p = p.getParent()) {
            l.add(0, p);
        }
        ex = x;
        ey = y;
        MouseListener.MouseButtonEvent event = new MouseListener.MouseButtonEvent(ex, ey, this.button, this.focus);
        for (Component a : l) {
            event.setX(event.getX() - a.getX());
            event.setY(event.getY() - a.getY());
            if (a instanceof Container) {
                event.setX(event.getX() - ((Container)a).getOriginOffsetX());
                event.setY(event.getY() - ((Container)a).getOriginOffsetY());
            }
            a.getMouseListeners().forEach(listener -> listener.onMouseDrag(event));
            if (!event.isCancelled()) continue;
            break;
        }
    }

    private void catchMouse() {
        while (Mouse.next()) {
            int x = Mouse.getX();
            int y = Mouse.getY();
            y = Display.getHeight() - y;
            if (this.press && this.focus != null && (this.x != x || this.y != y)) {
                this.handleMouseDrag(x, y);
            }
            if (Mouse.getEventButtonState()) {
                this.handleMouseDown(x, y);
            } else {
                this.handleMouseRelease(x, y);
            }
            if (!Mouse.hasWheel()) continue;
            this.handleWheel(x, y, Mouse.getDWheel());
        }
    }

    public void callTick(Container container) {
        container.getTickListeners().forEach(tickListener -> tickListener.onTick());
        for (Component c : container.getChildren()) {
            if (c instanceof Container) {
                this.callTick((Container)c);
                continue;
            }
            c.getTickListeners().forEach(tickListener -> tickListener.onTick());
        }
    }

    public void update() {
        if (System.currentTimeMillis() - this.lastMS > 50L) {
            this.callTick(this);
            this.lastMS = System.currentTimeMillis();
        }
    }

    public void drawGUI() {
        this.renderChildren();
    }

    public Component getFocus() {
        return this.focus;
    }

    public static int[] calculateRealPosition(Component c) {
        int realX = c.getX();
        int realY = c.getY();
        if (c instanceof Container) {
            realX += ((Container)c).getOriginOffsetX();
            realY += ((Container)c).getOriginOffsetY();
        }
        for (Container parent = c.getParent(); parent != null; parent = parent.getParent()) {
            realX += parent.getX();
            realY += parent.getY();
            if (!(parent instanceof Container)) continue;
            realX += parent.getOriginOffsetX();
            realY += parent.getOriginOffsetY();
        }
        return new int[]{realX, realY};
    }
}

