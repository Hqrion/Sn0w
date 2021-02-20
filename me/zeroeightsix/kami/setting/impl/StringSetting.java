/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting.impl;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.converter.StringConverter;

public class StringSetting
extends Setting<String> {
    private static final StringConverter converter = new StringConverter();

    public StringSetting(String value, Predicate<String> restriction, BiConsumer<String, String> consumer, String name, Predicate<String> visibilityPredicate) {
        super(value, restriction, consumer, name, visibilityPredicate);
    }

    public StringConverter converter() {
        return converter;
    }
}

