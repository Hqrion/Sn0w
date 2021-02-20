/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.listen;

import me.zeroeightsix.kami.gui.rgui.component.Component;

public interface UpdateListener<T extends Component> {
    public void updateSize(T var1, int var2, int var3);

    public void updateLocation(T var1, int var2, int var3);
}

