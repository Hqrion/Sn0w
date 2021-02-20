/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component;

import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;

public class AlignedComponent
extends AbstractComponent {
    Alignment alignment;

    public Alignment getAlignment() {
        return this.alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public static enum Alignment {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        int index;

        private Alignment(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }
}

