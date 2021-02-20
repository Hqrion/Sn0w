/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.render.theme;

import java.util.HashMap;
import java.util.Map;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.layout.Layout;
import me.zeroeightsix.kami.gui.rgui.render.ComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;

public abstract class AbstractTheme
implements Theme {
    protected final Map<Class<? extends Component>, ComponentUI> uis = new HashMap<Class<? extends Component>, ComponentUI>();
    protected final Map<Class<? extends Layout>, Class<? extends Layout>> layoutMap = new HashMap<Class<? extends Layout>, Class<? extends Layout>>();

    protected void installUI(ComponentUI<?> ui) {
        this.uis.put(ui.getHandledClass(), ui);
    }

    @Override
    public ComponentUI getUIForComponent(Component component) {
        ComponentUI a = this.getComponentUIForClass(component.getClass());
        if (a == null) {
            throw new RuntimeException("No installed component UI for " + component.getClass().getName());
        }
        return a;
    }

    public ComponentUI getComponentUIForClass(Class<? extends Component> componentClass) {
        if (this.uis.containsKey(componentClass)) {
            return this.uis.get(componentClass);
        }
        if (componentClass == null) {
            return null;
        }
        for (Class<?> componentInterface : componentClass.getInterfaces()) {
            ComponentUI ui = this.uis.get(componentInterface);
            if (ui == null) continue;
            return ui;
        }
        return this.getComponentUIForClass(componentClass.getSuperclass());
    }
}

