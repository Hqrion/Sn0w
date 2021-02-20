/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.poof.use;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;

public abstract class FramePoof<T extends Component, S extends PoofInfo>
extends Poof<T, S> {

    public static enum Action {
        MINIMIZE,
        MAXIMIZE,
        CLOSE;

    }

    public static class FramePoofInfo
    extends PoofInfo {
        private Action action;

        public FramePoofInfo(Action action) {
            this.action = action;
        }

        public Action getAction() {
            return this.action;
        }
    }
}

