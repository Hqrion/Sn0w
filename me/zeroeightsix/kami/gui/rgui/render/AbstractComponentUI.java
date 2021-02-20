/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render;

import java.lang.reflect.ParameterizedType;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.render.ComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;

public abstract class AbstractComponentUI<T extends Component>
implements ComponentUI<T> {
    private Class<T> persistentClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public void renderComponent(T component, FontRenderer fontRenderer) {
    }

    @Override
    public void handleMouseDown(T component, int x, int y, int button) {
    }

    @Override
    public void handleMouseRelease(T component, int x, int y, int button) {
    }

    @Override
    public void handleMouseDrag(T component, int x, int y, int button) {
    }

    @Override
    public void handleScroll(T component, int x, int y, int amount, boolean up) {
    }

    @Override
    public void handleAddComponent(T component, Container container) {
    }

    @Override
    public void handleKeyDown(T component, int key) {
    }

    @Override
    public void handleKeyUp(T component, int key) {
    }

    @Override
    public void handleSizeComponent(T component) {
    }

    @Override
    public Class<? extends Component> getHandledClass() {
        return this.persistentClass;
    }
}

