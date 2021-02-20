/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.poof.use;

import java.lang.reflect.ParameterizedType;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.poof.IPoof;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;

public abstract class Poof<T extends Component, S extends PoofInfo>
implements IPoof<T, S> {
    private Class<T> componentclass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<S> infoclass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    @Override
    public Class getComponentClass() {
        return this.componentclass;
    }

    @Override
    public Class<S> getInfoClass() {
        return this.infoclass;
    }
}

