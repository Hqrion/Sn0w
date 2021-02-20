/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.commands.BindCommand;
import me.zeroeightsix.kami.util.ClassFinder;

public class CommandManager {
    private ArrayList<Command> commands = new ArrayList();

    public CommandManager() {
        Set<Class> classList = ClassFinder.findClasses(BindCommand.class.getPackage().getName(), Command.class);
        for (Class s : classList) {
            if (!Command.class.isAssignableFrom(s)) continue;
            try {
                Command command = (Command)s.getConstructor(new Class[0]).newInstance(new Object[0]);
                this.commands.add(command);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Couldn't initiate command " + s.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
        }
        KamiMod.LOGGER.info("Commands initialised");
    }

    public void callCommand(String command) {
        String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String label = parts[0].substring(1);
        String[] args = CommandManager.removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) continue;
            args[i] = CommandManager.strip(args[i], "\"");
        }
        for (Command c : this.commands) {
            if (!c.getLabel().equalsIgnoreCase(label)) continue;
            c.call(parts);
            return;
        }
        Command.sendChatMessage("Unknown command. try 'commands' for a list of commands.");
    }

    public static String[] removeElement(String[] input, int indexToDelete) {
        LinkedList<String> result = new LinkedList<String>();
        for (int i = 0; i < input.length; ++i) {
            if (i == indexToDelete) continue;
            result.add(input[i]);
        }
        return result.toArray(input);
    }

    private static String strip(String str, String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }

    public Command getCommandByLabel(String commandLabel) {
        for (Command c : this.commands) {
            if (!c.getLabel().equals(commandLabel)) continue;
            return c;
        }
        return null;
    }

    public ArrayList<Command> getCommands() {
        return this.commands;
    }
}

