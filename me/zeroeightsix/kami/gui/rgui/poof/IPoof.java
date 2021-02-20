/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.poof;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;

public interface IPoof<T extends Component, S extends PoofInfo> {
    public void execute(T var1, S var2);

    public Class getComponentClass();

    public Class getInfoClass();
}

