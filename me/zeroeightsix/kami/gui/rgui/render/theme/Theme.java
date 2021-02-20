/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render.theme;

import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.render.ComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;

public interface Theme {
    public ComponentUI getUIForComponent(Component var1);

    public FontRenderer getFontRenderer();
}

