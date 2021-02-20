/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.setting;

import java.util.HashMap;
import java.util.StringTokenizer;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.util.Pair;

public class SettingsRegister {
    public static final SettingsRegister ROOT = new SettingsRegister();
    public HashMap<String, SettingsRegister> registerHashMap = new HashMap();
    public HashMap<String, Setting> settingHashMap = new HashMap();

    public SettingsRegister subregister(String name) {
        if (this.registerHashMap.containsKey(name)) {
            return this.registerHashMap.get(name);
        }
        SettingsRegister register = new SettingsRegister();
        this.registerHashMap.put(name, register);
        return register;
    }

    private void put(String name, Setting setting) {
        this.settingHashMap.put(name, setting);
    }

    public static void register(String name, Setting setting) {
        Pair<String, SettingsRegister> pair = SettingsRegister.dig(name);
        pair.getValue().put(pair.getKey(), setting);
    }

    public Setting getSetting(String group) {
        return this.settingHashMap.get(group);
    }

    public static Setting get(String group) {
        Pair<String, SettingsRegister> pair = SettingsRegister.dig(group);
        return pair.getValue().getSetting(pair.getKey());
    }

    private static Pair<String, SettingsRegister> dig(String group) {
        SettingsRegister current = ROOT;
        StringTokenizer tokenizer = new StringTokenizer(group, ".");
        String previousToken = null;
        while (tokenizer.hasMoreTokens()) {
            if (previousToken == null) {
                previousToken = tokenizer.nextToken();
                continue;
            }
            String token = tokenizer.nextToken();
            current = current.subregister(previousToken);
            previousToken = token;
        }
        return new Pair<String, SettingsRegister>(previousToken == null ? "" : previousToken, current);
    }
}

