/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.use;

import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;

public class Button
extends AbstractComponent {
    private String name;

    public Button(String name) {
        this(name, 0, 0);
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                Button.this.callPoof(ButtonPoof.class, new ButtonPoof.ButtonInfo(event.getButton(), event.getX(), event.getY()));
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

    public Button(String name, int x, int y) {
        this.name = name;
        this.setX(x);
        this.setY(y);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void kill() {
    }

    public static abstract class ButtonPoof<T extends Button, S extends ButtonInfo>
    extends Poof<T, S> {
        ButtonInfo info;

        public static class ButtonInfo
        extends PoofInfo {
            int button;
            int x;
            int y;

            public ButtonInfo(int button, int x, int y) {
                this.button = button;
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return this.x;
            }

            public int getY() {
                return this.y;
            }

            public int getButton() {
                return this.button;
            }
        }
    }
}

