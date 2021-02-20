/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command.syntax.parsers;

import java.util.HashMap;
import java.util.TreeMap;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;
import me.zeroeightsix.kami.command.syntax.parsers.AbstractParser;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;

public class ValueParser
extends AbstractParser {
    int moduleIndex;

    public ValueParser(int moduleIndex) {
        this.moduleIndex = moduleIndex;
    }

    @Override
    public String getChunk(SyntaxChunk[] chunks, SyntaxChunk thisChunk, String[] values, String chunkValue) {
        if (this.moduleIndex > values.length - 1 || chunkValue == null) {
            return this.getDefaultChunk(thisChunk);
        }
        String module = values[this.moduleIndex];
        Module m = ModuleManager.getModuleByName(module);
        if (m == null) {
            return "";
        }
        HashMap<String, Setting> possibilities = new HashMap<String, Setting>();
        for (Setting v : m.settingList) {
            if (!v.getName().toLowerCase().startsWith(chunkValue.toLowerCase())) continue;
            possibilities.put(v.getName(), v);
        }
        if (possibilities.isEmpty()) {
            return "";
        }
        TreeMap p = new TreeMap(possibilities);
        Setting aV = (Setting)p.firstEntry().getValue();
        return aV.getName().substring(chunkValue.length());
    }
}

