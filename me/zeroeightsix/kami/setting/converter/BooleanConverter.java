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

public class BooleanConverter
extends Converter<Boolean, JsonElement> {
    protected JsonElement doForward(Boolean aBoolean) {
        return new JsonPrimitive(aBoolean);
    }

    protected Boolean doBackward(JsonElement s) {
        return s.getAsBoolean();
    }
}

