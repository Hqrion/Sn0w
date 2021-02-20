/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.command.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.command.syntax.parsers.DependantParser;
import me.zeroeightsix.kami.command.syntax.parsers.EnumParser;
import me.zeroeightsix.kami.gui.kami.KamiGUI;

public class ConfigCommand
extends Command {
    public ConfigCommand() {
        super("config", new ChunkBuilder().append("mode", true, new EnumParser(new String[]{"reload", "save", "path"})).append("path", true, new DependantParser(0, new DependantParser.Dependency(new String[][]{{"path", "path"}}, ""))).build());
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void call(String[] args) {
        if (args[0] == null) {
            Command.sendChatMessage("Missing argument &bmode&r: Choose from reload, save or path");
            return;
        }
        var2_2 = args[0].toLowerCase();
        var3_3 = -1;
        switch (var2_2.hashCode()) {
            case -934641255: {
                if (!var2_2.equals("reload")) break;
                var3_3 = 0;
                break;
            }
            case 3522941: {
                if (!var2_2.equals("save")) break;
                var3_3 = 1;
                break;
            }
            case 3433509: {
                if (!var2_2.equals("path")) break;
                var3_3 = 2;
            }
        }
        switch (var3_3) {
            case 0: {
                this.reload();
                break;
            }
            case 1: {
                try {
                    KamiMod.saveConfigurationUnsafe();
                    Command.sendChatMessage("Saved configuration!");
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Command.sendChatMessage("Failed to save! " + e.getMessage());
                }
                break;
            }
            case 2: {
                if (args[1] == null) {
                    file = Paths.get(KamiMod.getConfigName(), new String[0]);
                    Command.sendChatMessage("Path to configuration: &b" + file.toAbsolutePath().toString());
                    break;
                }
                newPath = args[1];
                if (!KamiMod.isFilenameValid(newPath)) {
                    Command.sendChatMessage("&b" + newPath + "&r is not a valid path");
                    break;
                }
                try {
                    writer = Files.newBufferedWriter(Paths.get("KAMILastConfig.txt", new String[0]), new OpenOption[0]);
                    var6_9 = null;
                    writer.write(newPath);
                    this.reload();
                    Command.sendChatMessage("Configuration path set to &b" + newPath + "&r!");
                    if (writer == null) break;
                    if (var6_9 == null) ** GOTO lbl55
                    try {
                        writer.close();
                    }
                    catch (Throwable var7_10) {
                        var6_9.addSuppressed(var7_10);
                    }
                    break;
lbl55:
                    // 1 sources

                    writer.close();
                    ** break;
                    catch (Throwable var7_11) {
                        try {
                            var6_9 = var7_11;
                            throw var7_11;
                        }
                        catch (Throwable var8_12) {
                            if (writer != null) {
                                if (var6_9 != null) {
                                    try {
                                        writer.close();
                                    }
                                    catch (Throwable var9_13) {
                                        var6_9.addSuppressed(var9_13);
                                    }
                                } else {
                                    writer.close();
                                }
                            }
                            throw var8_12;
lbl72:
                            // 1 sources

                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Command.sendChatMessage("Couldn't set path: " + e.getMessage());
                }
                break;
            }
            default: {
                Command.sendChatMessage("Incorrect mode, please choose from: reload, save or path");
            }
        }
    }

    private void reload() {
        KamiMod.getInstance().kamiGUI = new KamiGUI();
        KamiMod.getInstance().kamiGUI.initializeGUI();
        KamiMod.loadConfiguration();
        Command.sendChatMessage("Configuration reloaded!");
    }
}

