/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting;

public interface ISettingUnknown {
    public String getName();

    public Class getValueClass();

    public String getValueAsString();

    public boolean isVisible();

    public void setValueFromString(String var1);
}

