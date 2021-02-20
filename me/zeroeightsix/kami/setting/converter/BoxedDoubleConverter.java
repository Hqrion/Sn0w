/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package me.zeroeightsix.kami.setting.converter;

import com.google.gson.JsonElement;
import me.zeroeightsix.kami.setting.converter.AbstractBoxedNumberConverter;

public class BoxedDoubleConverter
extends AbstractBoxedNumberConverter<Double> {
    protected Double doBackward(JsonElement s) {
        return s.getAsDouble();
    }
}

