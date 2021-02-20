/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.util;

import java.util.ArrayList;
import java.util.List;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.Container;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;

public class ContainerHelper {
    public static void setTheme(Container parent, Theme newTheme) {
        Theme old = parent.getTheme();
        parent.setTheme(newTheme);
        for (Component c : parent.getChildren()) {
            if (!c.getTheme().equals(old)) continue;
            c.setTheme(newTheme);
        }
    }

    public static void setAlignment(Container container, AlignedComponent.Alignment alignment) {
        for (Component component : container.getChildren()) {
            if (component instanceof Container) {
                ContainerHelper.setAlignment((Container)component, alignment);
            }
            if (!(component instanceof AlignedComponent)) continue;
            ((AlignedComponent)component).setAlignment(alignment);
        }
    }

    public static AlignedComponent.Alignment getAlignment(Container container) {
        for (Component component : container.getChildren()) {
            if (component instanceof Container) {
                return ContainerHelper.getAlignment((Container)component);
            }
            if (!(component instanceof AlignedComponent)) continue;
            return ((AlignedComponent)component).getAlignment();
        }
        return AlignedComponent.Alignment.LEFT;
    }

    public static Component getHighParent(Component child) {
        if (child.getParent() instanceof GUI || child.getParent() == null) {
            return child;
        }
        return ContainerHelper.getHighParent(child.getParent());
    }

    public static <T extends Component> T getFirstParent(Class<? extends T> parentClass, Component component) {
        if (component.getClass().equals(parentClass)) {
            return (T)component;
        }
        if (component == null) {
            return null;
        }
        return ContainerHelper.getFirstParent(parentClass, component.getParent());
    }

    public static <S extends Component> List<S> getAllChildren(Class<? extends S> childClass, Container parent) {
        ArrayList<Component> list = new ArrayList<Component>();
        for (Component c : parent.getChildren()) {
            if (childClass.isAssignableFrom(c.getClass())) {
                list.add(c);
            }
            if (!(c instanceof Container)) continue;
            list.addAll(ContainerHelper.getAllChildren(childClass, (Container)c));
        }
        return list;
    }
}

