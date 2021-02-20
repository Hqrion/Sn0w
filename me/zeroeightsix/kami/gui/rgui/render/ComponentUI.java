/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;

public interface ComponentUI<T extends Component> {
    public void renderComponent(T var1, FontRenderer var2);

    public void handleMouseDown(T var1, int var2, int var3, int var4);

    public void handleMouseRelease(T var1, int var2, int var3, int var4);

    public void handleMouseDrag(T var1, int var2, int var3, int var4);

    public void handleScroll(T var1, int var2, int var3, int var4, boolean var5);

    public void handleKeyDown(T var1, int var2);

    public void handleKeyUp(T var1, int var2);

    public void handleAddComponent(T var1, Container var2);

    public void handleSizeComponent(T var1);

    public Class<? extends Component> getHandledClass();
}

