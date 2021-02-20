/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.layout;

import java.util.ArrayList;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.layout.Layout;

public class GridbagLayout
implements Layout {
    private static final int COMPONENT_OFFSET = 10;
    int blocks;
    int maxrows = -1;

    public GridbagLayout(int blocks) {
        this.blocks = blocks;
    }

    public GridbagLayout(int blocks, int fixrows) {
        this.blocks = blocks;
        this.maxrows = fixrows;
    }

    @Override
    public void organiseContainer(Container container) {
        int width = 0;
        int height = 0;
        int i = 0;
        int w = 0;
        int h = 0;
        ArrayList<Component> children = container.getChildren();
        for (Component c : children) {
            if (!c.doAffectLayout()) continue;
            w += c.getWidth() + 10;
            h = Math.max(h, c.getHeight());
            if (++i < this.blocks) continue;
            width = Math.max(width, w);
            height += h + 10;
            i = 0;
            h = 0;
            w = 0;
        }
        int x = 0;
        int y = 0;
        for (Component c : children) {
            if (!c.doAffectLayout()) continue;
            c.setX(x + 3);
            c.setY(y + 3);
            h = Math.max(c.getHeight(), h);
            if ((x += width / this.blocks) < width) continue;
            y += h + 10;
            x = 0;
        }
        container.setWidth(width);
        container.setHeight(height);
    }
}

