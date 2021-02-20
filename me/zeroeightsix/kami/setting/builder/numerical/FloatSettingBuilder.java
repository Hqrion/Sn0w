/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting.builder.numerical;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.zeroeightsix.kami.setting.builder.numerical.NumericalSettingBuilder;
import me.zeroeightsix.kami.setting.impl.numerical.FloatSetting;
import me.zeroeightsix.kami.setting.impl.numerical.NumberSetting;

public class FloatSettingBuilder
extends NumericalSettingBuilder<Float> {
    @Override
    public NumberSetting build() {
        return new FloatSetting((Float)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), (Float)this.min, (Float)this.max);
    }

    public FloatSettingBuilder withMinimum(Float minimum) {
        return (FloatSettingBuilder)super.withMinimum(minimum);
    }

    @Override
    public FloatSettingBuilder withName(String name) {
        return (FloatSettingBuilder)super.withName(name);
    }

    public FloatSettingBuilder withListener(BiConsumer<Float, Float> consumer) {
        return (FloatSettingBuilder)super.withListener(consumer);
    }

    public FloatSettingBuilder withMaximum(Float maximum) {
        return (FloatSettingBuilder)super.withMaximum(maximum);
    }

    public FloatSettingBuilder withRange(Float minimum, Float maximum) {
        return (FloatSettingBuilder)super.withRange(minimum, maximum);
    }

    public FloatSettingBuilder withConsumer(BiConsumer<Float, Float> consumer) {
        return (FloatSettingBuilder)super.withConsumer(consumer);
    }

    public FloatSettingBuilder withValue(Float value) {
        return (FloatSettingBuilder)super.withValue(value);
    }

    public FloatSettingBuilder withVisibility(Predicate<Float> predicate) {
        return (FloatSettingBuilder)super.withVisibility(predicate);
    }

    public FloatSettingBuilder withRestriction(Predicate<Float> predicate) {
        return (FloatSettingBuilder)super.withRestriction(predicate);
    }
}

