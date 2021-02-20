/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting.builder.primitive;

import me.zeroeightsix.kami.setting.builder.SettingBuilder;
import me.zeroeightsix.kami.setting.impl.BooleanSetting;

public class BooleanSettingBuilder
extends SettingBuilder<Boolean> {
    public BooleanSetting build() {
        return new BooleanSetting((Boolean)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate());
    }

    public BooleanSettingBuilder withName(String name) {
        return (BooleanSettingBuilder)super.withName(name);
    }
}

