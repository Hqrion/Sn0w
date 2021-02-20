/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.kami.component;

import me.zeroeightsix.kami.gui.kami.component.EnumButton;
import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.Bind;

class BindButton
extends EnumButton {
    private static String[] lookingFor = new String[]{"_"};
    private static String[] none = new String[]{"NONE"};
    private boolean waiting = false;
    private Module m;

    BindButton(String name, final Module m) {
        super(name, none);
        this.m = m;
        Bind bind = m.getBind();
        this.modes = new String[]{bind.toString()};
        this.addKeyListener(new KeyListener(){

            @Override
            public void onKeyDown(KeyListener.KeyEvent event) {
                if (!BindButton.this.waiting) {
                    return;
                }
                int key = event.getKey();
                if (key == 14) {
                    m.getBind().setKey(-1);
                    BindButton.this.modes = new String[]{m.getBind().toString()};
                    BindButton.this.waiting = false;
                } else {
                    m.getBind().setKey(key);
                    BindButton.this.modes = new String[]{m.getBind().toString()};
                    BindButton.this.waiting = false;
                }
            }

            @Override
            public void onKeyUp(KeyListener.KeyEvent event) {
            }
        });
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                BindButton.this.setModes(lookingFor);
                BindButton.this.waiting = true;
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
}

