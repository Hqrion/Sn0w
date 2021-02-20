/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.kami.component;

import me.zeroeightsix.kami.gui.rgui.component.use.Button;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;

public class EnumButton
extends Button {
    String[] modes;
    int index;

    public EnumButton(String name, String[] modes) {
        super(name);
        this.modes = modes;
        this.index = 0;
        this.addPoof(new Button.ButtonPoof<EnumButton, Button.ButtonPoof.ButtonInfo>(){

            @Override
            public void execute(EnumButton component, Button.ButtonPoof.ButtonInfo info) {
                if (info.getButton() == 0) {
                    double p = (double)info.getX() / (double)component.getWidth();
                    if (p <= 0.5) {
                        EnumButton.this.increaseIndex(-1);
                    } else {
                        EnumButton.this.increaseIndex(1);
                    }
                }
            }
        });
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }

    protected void increaseIndex(int amount) {
        int old = this.index;
        int newI = this.index + amount;
        if (newI < 0) {
            newI = this.modes.length - Math.abs(newI);
        } else if (newI >= this.modes.length) {
            newI = Math.abs(newI - this.modes.length);
        }
        this.index = Math.min(this.modes.length, Math.max(0, newI));
        this.callPoof(EnumbuttonIndexPoof.class, new EnumbuttonIndexPoof.EnumbuttonInfo(old, this.index));
    }

    public int getIndex() {
        return this.index;
    }

    public String[] getModes() {
        return this.modes;
    }

    public String getIndexMode() {
        return this.modes[this.index];
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static abstract class EnumbuttonIndexPoof<T extends Button, S extends EnumbuttonInfo>
    extends Poof<T, S> {
        Button.ButtonPoof.ButtonInfo info;

        public static class EnumbuttonInfo
        extends PoofInfo {
            int oldIndex;
            int newIndex;

            public EnumbuttonInfo(int oldIndex, int newIndex) {
                this.oldIndex = oldIndex;
                this.newIndex = newIndex;
            }

            public int getNewIndex() {
                return this.newIndex;
            }

            public void setNewIndex(int newIndex) {
                this.newIndex = newIndex;
            }

            public int getOldIndex() {
                return this.oldIndex;
            }
        }
    }
}

