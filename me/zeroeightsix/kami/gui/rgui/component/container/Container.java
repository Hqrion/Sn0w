/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.container;

import java.util.ArrayList;
import me.zeroeightsix.kami.gui.rgui.component.Component;

public interface Container
extends Component {
    public ArrayList<Component> getChildren();

    public Component getComponentAt(int var1, int var2);

    public Container addChild(Component ... var1);

    public Container removeChild(Component var1);

    public boolean hasChild(Component var1);

    public void renderChildren();

    public int getOriginOffsetX();

    public int getOriginOffsetY();

    public boolean penetrateTest(int var1, int var2);
}

