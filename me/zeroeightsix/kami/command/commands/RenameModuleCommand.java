/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.command.syntax.parsers.ModuleParser;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;

public class RenameModuleCommand
extends Command {
    public RenameModuleCommand() {
        super("renamemodule", new ChunkBuilder().append("module", true, new ModuleParser()).append("name").build());
    }

    @Override
    public void call(String[] args) {
        String name;
        if (args.length == 0) {
            RenameModuleCommand.sendChatMessage("Please specify a module!");
            return;
        }
        Module module = ModuleManager.getModuleByName(args[0]);
        if (module == null) {
            RenameModuleCommand.sendChatMessage("Unknown module '" + args[0] + "'!");
            return;
        }
        String string = name = args.length == 1 ? module.getOriginalName() : args[1];
        if (!name.matches("[a-zA-Z]+")) {
            RenameModuleCommand.sendChatMessage("Name must be alphabetic!");
            return;
        }
        RenameModuleCommand.sendChatMessage("&b" + module.getName() + "&r renamed to &b" + name);
        module.setName(name);
    }
}

