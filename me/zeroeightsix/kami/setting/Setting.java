/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 */
package me.zeroeightsix.kami.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.zeroeightsix.kami.setting.ISettingUnknown;
import me.zeroeightsix.kami.setting.converter.Convertable;

public abstract class Setting<T>
implements ISettingUnknown,
Convertable<T> {
    String name;
    T value;
    private Predicate<T> restriction;
    private Predicate<T> visibilityPredicate;
    private BiConsumer<T, T> consumer;
    private final Class valueType;

    public Setting(T value, Predicate<T> restriction, BiConsumer<T, T> consumer, String name, Predicate<T> visibilityPredicate) {
        this.value = value;
        this.valueType = value.getClass();
        this.restriction = restriction;
        this.consumer = consumer;
        this.name = name;
        this.visibilityPredicate = visibilityPredicate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public Class getValueClass() {
        return this.valueType;
    }

    public boolean setValue(T value) {
        T old = this.getValue();
        if (!this.restriction.test(value)) {
            return false;
        }
        this.value = value;
        this.consumer.accept(old, value);
        return true;
    }

    @Override
    public boolean isVisible() {
        return this.visibilityPredicate.test(this.getValue());
    }

    public BiConsumer<T, T> changeListener() {
        return this.consumer;
    }

    @Override
    public void setValueFromString(String value) {
        JsonParser jp = new JsonParser();
        this.setValue(this.converter().reverse().convert((Object)jp.parse(value)));
    }

    @Override
    public String getValueAsString() {
        return ((JsonElement)this.converter().convert(this.getValue())).toString();
    }
}

