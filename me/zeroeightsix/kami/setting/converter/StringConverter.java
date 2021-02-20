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

public class StringConverter
extends Converter<String, JsonElement> {
    protected JsonElement doForward(String s) {
        return new JsonPrimitive(s);
    }

    protected String doBackward(JsonElement s) {
        return s.getAsString();
    }
}

