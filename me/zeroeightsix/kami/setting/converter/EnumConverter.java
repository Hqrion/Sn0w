/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Converter
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package me.zeroeightsix.kami.setting.converter;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumConverter
extends Converter<Enum, JsonElement> {
    Class<? extends Enum> clazz;

    public EnumConverter(Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }

    protected JsonElement doForward(Enum anEnum) {
        return new JsonPrimitive(anEnum.toString());
    }

    protected Enum doBackward(JsonElement jsonElement) {
        return Enum.valueOf(this.clazz, jsonElement.getAsString());
    }
}

