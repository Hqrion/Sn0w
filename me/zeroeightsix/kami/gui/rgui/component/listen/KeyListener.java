/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.listen;

public interface KeyListener {
    public void onKeyDown(KeyEvent var1);

    public void onKeyUp(KeyEvent var1);

    public static class KeyEvent {
        int key;

        public KeyEvent(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }
}

