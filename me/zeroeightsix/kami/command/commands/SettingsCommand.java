/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command.commands;

import java.util.List;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.command.syntax.parsers.ModuleParser;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.impl.EnumSetting;

public class SettingsCommand
extends Command {
    public SettingsCommand() {
        super("settings", new ChunkBuilder().append("module", true, new ModuleParser()).build());
    }

    @Override
    public void call(String[] args) {
        if (args[0] == null) {
            Command.sendChatMessage("Please specify a module to display the settings of.");
            return;
        }
        Module m = ModuleManager.getModuleByName(args[0]);
        if (m == null) {
            Command.sendChatMessage("Couldn't find a module &b" + args[0] + "!");
            return;
        }
        List<Setting> settings = m.settingList;
        String[] result = new String[settings.size()];
        for (int i = 0; i < settings.size(); ++i) {
            Enum[] enums;
            Setting setting = settings.get(i);
            result[i] = "&b" + setting.getName() + "&3(=" + setting.getValue() + ")  &ftype: &3" + setting.getValue().getClass().getSimpleName();
            if (!(setting instanceof EnumSetting)) continue;
            int n = i;
            result[n] = result[n] + "  (";
            for (Enum e : enums = ((EnumSetting)setting).clazz.getEnumConstants()) {
                int n2 = i;
                result[n2] = result[n2] + e.name() + ", ";
            }
            result[i] = result[i].substring(0, result[i].length() - 2) + ")";
        }
        Command.sendStringChatMessage(result);
    }
}

