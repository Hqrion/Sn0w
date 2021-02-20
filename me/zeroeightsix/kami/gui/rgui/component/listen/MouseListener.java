/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.listen;

import me.zeroeightsix.kami.gui.rgui.component.Component;

public interface MouseListener {
    public void onMouseDown(MouseButtonEvent var1);

    public void onMouseRelease(MouseButtonEvent var1);

    public void onMouseDrag(MouseButtonEvent var1);

    public void onMouseMove(MouseMoveEvent var1);

    public void onScroll(MouseScrollEvent var1);

    public static class MouseScrollEvent {
        int x;
        int y;
        boolean up;
        Component component;
        private boolean cancelled;

        public MouseScrollEvent(int x, int y, boolean up, Component component) {
            this.x = x;
            this.y = y;
            this.up = up;
            this.component = component;
        }

        public Component getComponent() {
            return this.component;
        }

        public boolean isUp() {
            return this.up;
        }

        public void setUp(boolean up) {
            this.up = up;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getX() {
            return this.x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getY() {
            return this.y;
        }

        public void cancel() {
            this.cancelled = true;
        }

        public boolean isCancelled() {
            return this.cancelled;
        }
    }

    public static class MouseButtonEvent {
        int x;
        int y;
        int button;
        Component component;
        boolean cancelled = false;

        public MouseButtonEvent(int x, int y, int button, Component component) {
            this.x = x;
            this.y = y;
            this.button = button;
            this.component = component;
        }

        public Component getComponent() {
            return this.component;
        }

        public void setButton(int button) {
            this.button = button;
        }

        public int getButton() {
            return this.button;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getX() {
            return this.x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getY() {
            return this.y;
        }

        public void cancel() {
            this.cancelled = true;
        }

        public boolean isCancelled() {
            return this.cancelled;
        }
    }

    public static class MouseMoveEvent {
        boolean cancelled = false;
        int x;
        int y;
        int oldX;
        int oldY;
        Component component;

        public MouseMoveEvent(int x, int y, int oldX, int oldY, Component component) {
            this.x = x;
            this.y = y;
            this.oldX = oldX;
            this.oldY = oldY;
            this.component = component;
        }

        public Component getComponent() {
            return this.component;
        }

        public int getOldX() {
            return this.oldX;
        }

        public int getOldY() {
            return this.oldY;
        }

        public int getY() {
            return this.y;
        }

        public int getX() {
            return this.x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean isCancelled() {
            return this.cancelled;
        }
    }
}

