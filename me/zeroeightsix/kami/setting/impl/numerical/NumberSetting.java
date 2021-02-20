/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting.impl.numerical;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.converter.AbstractBoxedNumberConverter;

public abstract class NumberSetting<T extends Number>
extends Setting<T> {
    private final T min;
    private final T max;

    public NumberSetting(T value, Predicate<T> restriction, BiConsumer<T, T> consumer, String name, Predicate<T> visibilityPredicate, T min, T max) {
        super(value, restriction, consumer, name, visibilityPredicate);
        this.min = min;
        this.max = max;
    }

    public boolean isBound() {
        return this.min != null && this.max != null;
    }

    public abstract AbstractBoxedNumberConverter converter();

    @Override
    public T getValue() {
        return (T)((Number)super.getValue());
    }

    public T getMax() {
        return this.max;
    }

    public T getMin() {
        return this.min;
    }
}

