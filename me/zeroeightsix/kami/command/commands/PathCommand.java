/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.pathfinding.PathPoint
 */
package me.zeroeightsix.kami.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.module.modules.render.Pathfind;
import net.minecraft.pathfinding.PathPoint;

public class PathCommand
extends Command {
    int x = Integer.MIN_VALUE;
    int y = Integer.MIN_VALUE;
    int z = Integer.MIN_VALUE;

    public PathCommand() {
        super("path", new ChunkBuilder().append("x").append("y").append("z").build());
    }

    @Override
    public void call(String[] args) {
        if (args[0] != null && args[0].equalsIgnoreCase("retry")) {
            if (this.x != Integer.MIN_VALUE) {
                PathPoint end = new PathPoint(this.x, this.y, this.z);
                Pathfind.createPath(end);
                if (!Pathfind.points.isEmpty()) {
                    Command.sendChatMessage("Path created!");
                }
                return;
            }
            Command.sendChatMessage("No location to retry pathfinding to.");
            return;
        }
        if (args.length <= 3) {
            Command.sendChatMessage("&cMissing arguments: x, y, z");
            return;
        }
        try {
            this.x = Integer.parseInt(args[0]);
            this.y = Integer.parseInt(args[1]);
            this.z = Integer.parseInt(args[2]);
            PathPoint end = new PathPoint(this.x, this.y, this.z);
            Pathfind.createPath(end);
            if (!Pathfind.points.isEmpty()) {
                Command.sendChatMessage("Path created!");
            }
        }
        catch (NumberFormatException e) {
            Command.sendChatMessage(ChatFormatting.RED.toString() + "Error: input must be numerical");
            return;
        }
    }
}

