/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting.builder.numerical;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.zeroeightsix.kami.setting.builder.numerical.NumericalSettingBuilder;
import me.zeroeightsix.kami.setting.impl.numerical.DoubleSetting;
import me.zeroeightsix.kami.setting.impl.numerical.NumberSetting;

public class DoubleSettingBuilder
extends NumericalSettingBuilder<Double> {
    @Override
    public NumberSetting build() {
        return new DoubleSetting((Double)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), (Double)this.min, (Double)this.max);
    }

    public DoubleSettingBuilder withVisibility(Predicate<Double> predicate) {
        return (DoubleSettingBuilder)super.withVisibility(predicate);
    }

    public DoubleSettingBuilder withRestriction(Predicate<Double> predicate) {
        return (DoubleSettingBuilder)super.withRestriction(predicate);
    }

    public DoubleSettingBuilder withConsumer(BiConsumer<Double, Double> consumer) {
        return (DoubleSettingBuilder)super.withConsumer(consumer);
    }

    public DoubleSettingBuilder withValue(Double value) {
        return (DoubleSettingBuilder)super.withValue(value);
    }

    public DoubleSettingBuilder withRange(Double minimum, Double maximum) {
        return (DoubleSettingBuilder)super.withRange(minimum, maximum);
    }

    public DoubleSettingBuilder withMaximum(Double maximum) {
        return (DoubleSettingBuilder)super.withMaximum(maximum);
    }

    public DoubleSettingBuilder withListener(BiConsumer<Double, Double> consumer) {
        return (DoubleSettingBuilder)super.withListener(consumer);
    }

    @Override
    public DoubleSettingBuilder withName(String name) {
        return (DoubleSettingBuilder)super.withName(name);
    }

    public DoubleSettingBuilder withMinimum(Double minimum) {
        return (DoubleSettingBuilder)super.withMinimum(minimum);
    }
}

