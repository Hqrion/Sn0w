/*
 * Decompiled with CFR 0.151.
 */
package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public class ObfuscationData<T>
implements Iterable<ObfuscationType> {
    private final Map<ObfuscationType, T> data = new HashMap<ObfuscationType, T>();
    private final T defaultValue;

    public ObfuscationData() {
        this(null);
    }

    public ObfuscationData(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Deprecated
    public void add(ObfuscationType type2, T value) {
        this.put(type2, value);
    }

    public void put(ObfuscationType type2, T value) {
        this.data.put(type2, value);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public T get(ObfuscationType type2) {
        T value = this.data.get(type2);
        return value != null ? value : this.defaultValue;
    }

    @Override
    public Iterator<ObfuscationType> iterator() {
        return this.data.keySet().iterator();
    }

    public String toString() {
        return String.format("ObfuscationData[%s,DEFAULT=%s]", this.listValues(), this.defaultValue);
    }

    public String values() {
        return "[" + this.listValues() + "]";
    }

    private String listValues() {
        StringBuilder sb = new StringBuilder();
        boolean delim = false;
        for (ObfuscationType type2 : this.data.keySet()) {
            if (delim) {
                sb.append(',');
            }
            sb.append(type2.getKey()).append('=').append(this.data.get(type2));
            delim = true;
        }
        return sb.toString();
    }
}

