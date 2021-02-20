//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.mojang.util.UUIDTypeAdapter
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package me.zeroeightsix.kami.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.command.syntax.parsers.EnumParser;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend", new ChunkBuilder().append("mode", true, new EnumParser(new String[]{"add", "del"})).append("name").build());
    }

    @Override
    public void call(String[] args) {
        if (args[0] == null) {
            if (Friends.friends.getValue().isEmpty()) {
                Command.sendChatMessage("You currently don't have any friends added. &bfriend add <name>&r to add one.");
                return;
            }
            String f = "";
            for (Friends.Friend friend : Friends.friends.getValue()) {
                f = f + friend.getUsername() + ", ";
            }
            f = f.substring(0, f.length() - 2);
            Command.sendChatMessage("Your friends: " + f);
            return;
        }
        if (args[1] == null) {
            Command.sendChatMessage(String.format(Friends.isFriend(args[0]) ? "Yes, %s is your friend." : "No, %s isn't a friend of yours.", args[0]));
            Command.sendChatMessage(String.format(Friends.isFriend(args[0]) ? "Yes, %s is your friend." : "No, %s isn't a friend of yours.", args[0]));
            return;
        }
        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("new")) {
            if (Friends.isFriend(args[1])) {
                Command.sendChatMessage("That player is already your friend.");
                return;
            }
            new Thread(() -> {
                Friends.Friend f = this.getFriendByName(args[1]);
                if (f == null) {
                    Command.sendChatMessage("Failed to find UUID of " + args[1]);
                    return;
                }
                Friends.friends.getValue().add(f);
                Command.sendChatMessage("&b" + f.getUsername() + "&r has been friended.");
            }).start();
            return;
        }
        if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
            if (!Friends.isFriend(args[1])) {
                Command.sendChatMessage("That player isn't your friend.");
                return;
            }
            Friends.Friend friend = Friends.friends.getValue().stream().filter(friend1 -> friend1.getUsername().equalsIgnoreCase(args[1])).findFirst().get();
            Friends.friends.getValue().remove(friend);
            Command.sendChatMessage("&b" + friend.getUsername() + "&r has been unfriended.");
            return;
        }
        Command.sendChatMessage("Please specify either &6add&r or &6remove");
    }

    private Friends.Friend getFriendByName(String input) {
        ArrayList infoMap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(input)).findFirst().orElse(null);
        if (profile == null) {
            Command.sendChatMessage("Player isn't online. Looking up UUID..");
            String s = FriendCommand.requestIDs("[\"" + input + "\"]");
            if (s == null || s.isEmpty()) {
                Command.sendChatMessage("Couldn't find player ID. Are you connected to the internet? (0)");
            } else {
                JsonElement element = new JsonParser().parse(s);
                if (element.getAsJsonArray().size() == 0) {
                    Command.sendChatMessage("Couldn't find player ID. (1)");
                } else {
                    try {
                        String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                        String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        Friends.Friend friend = new Friends.Friend(username, UUIDTypeAdapter.fromString((String)id));
                        return friend;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Command.sendChatMessage("Couldn't find player ID. (2)");
                    }
                }
            }
            return null;
        }
        Friends.Friend f = new Friends.Friend(profile.getGameProfile().getName(), profile.getGameProfile().getId());
        return f;
    }

    private static String requestIDs(String data) {
        try {
            String query = "https://api.mojang.com/profiles/minecraft";
            String json = data;
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            String res = FriendCommand.convertStreamToString(in);
            ((InputStream)in).close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String r = s.hasNext() ? s.next() : "/";
        return r;
    }
}

