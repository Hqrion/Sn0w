/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.use;

import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.use.Button;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;

public class CheckButton
extends Button {
    boolean toggled;

    public CheckButton(String name) {
        this(name, 0, 0);
    }

    public CheckButton(String name, int x, int y) {
        super(name, x, y);
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                if (event.getButton() != 0) {
                    return;
                }
                CheckButton.this.toggled = !CheckButton.this.toggled;
                CheckButton.this.callPoof(CheckButtonPoof.class, new CheckButtonPoof.CheckButtonPoofInfo(CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE));
                if (CheckButton.this.toggled) {
                    CheckButton.this.callPoof(CheckButtonPoof.class, new CheckButtonPoof.CheckButtonPoofInfo(CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.ENABLE));
                } else {
                    CheckButton.this.callPoof(CheckButtonPoof.class, new CheckButtonPoof.CheckButtonPoofInfo(CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.DISABLE));
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
            }
        });
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public static abstract class CheckButtonPoof<T extends CheckButton, S extends CheckButtonPoofInfo>
    extends Poof<T, S> {
        CheckButtonPoofInfo info;

        public static class CheckButtonPoofInfo
        extends PoofInfo {
            CheckButtonPoofInfoAction action;

            public CheckButtonPoofInfo(CheckButtonPoofInfoAction action) {
                this.action = action;
            }

            public CheckButtonPoofInfoAction getAction() {
                return this.action;
            }

            public static enum CheckButtonPoofInfoAction {
                TOGGLE,
                ENABLE,
                DISABLE;

            }
        }
    }
}

