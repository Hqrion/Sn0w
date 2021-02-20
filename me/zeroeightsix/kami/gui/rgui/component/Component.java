/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component;

import java.util.ArrayList;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.RenderListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.TickListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.UpdateListener;
import me.zeroeightsix.kami.gui.rgui.poof.IPoof;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.render.ComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;

public interface Component {
    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

    public void setX(int var1);

    public void setY(int var1);

    public void setWidth(int var1);

    public void setHeight(int var1);

    public Component setMinimumWidth(int var1);

    public Component setMaximumWidth(int var1);

    public Component setMinimumHeight(int var1);

    public Component setMaximumHeight(int var1);

    public int getMinimumWidth();

    public int getMaximumWidth();

    public int getMinimumHeight();

    public int getMaximumHeight();

    public float getOpacity();

    public void setOpacity(float var1);

    public boolean doAffectLayout();

    public void setAffectLayout(boolean var1);

    public Container getParent();

    public void setParent(Container var1);

    public boolean liesIn(Component var1);

    public boolean isVisible();

    public void setVisible(boolean var1);

    public void setFocussed(boolean var1);

    public boolean isFocussed();

    public ComponentUI getUI();

    public Theme getTheme();

    public void setTheme(Theme var1);

    public boolean isHovered();

    public boolean isPressed();

    public ArrayList<MouseListener> getMouseListeners();

    public void addMouseListener(MouseListener var1);

    public ArrayList<RenderListener> getRenderListeners();

    public void addRenderListener(RenderListener var1);

    public ArrayList<KeyListener> getKeyListeners();

    public void addKeyListener(KeyListener var1);

    public ArrayList<UpdateListener> getUpdateListeners();

    public void addUpdateListener(UpdateListener var1);

    public ArrayList<TickListener> getTickListeners();

    public void addTickListener(TickListener var1);

    public void addPoof(IPoof var1);

    public void callPoof(Class<? extends IPoof> var1, PoofInfo var2);

    public int getPriority();

    public void kill();
}

