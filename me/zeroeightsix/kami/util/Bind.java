/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.zeroeightsix.kami.util;

import org.lwjgl.input.Keyboard;

public class Bind {
    private int key;

    public Bind(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isEmpty() {
        return this.key < 0;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String toString() {
        return this.isEmpty() ? "None" : (this.key < 0 ? "None" : this.capitalise(Keyboard.getKeyName((int)this.key)));
    }

    public boolean isDown() {
        return !this.isEmpty() && Keyboard.isKeyDown((int)this.getKey());
    }

    private String capitalise(String str) {
        if (str.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(str.charAt(0)) + (str.length() != 1 ? str.substring(1).toLowerCase() : "");
    }

    public static Bind none() {
        return new Bind(-1);
    }
}

