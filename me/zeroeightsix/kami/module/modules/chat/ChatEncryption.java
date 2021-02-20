//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.util.ChatAllowedCharacters
 *  net.minecraft.util.text.TextComponentString
 */
package me.zeroeightsix.kami.module.modules.chat;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;

@Module.Info(name="ChatEncryption", description="Encrypts and decrypts chat messages (Delimiter %)", category=Module.Category.HIDDEN)
public class ChatEncryption
extends Module {
    private static final char[] ORIGIN_CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '_', '/', ';', '=', '?', '+', '\u00b5', '\u00a3', '*', '^', '\u00f9', '$', '!', '{', '}', '\'', '\"', '|', '&'};
    private final Pattern CHAT_PATTERN = Pattern.compile("<.*?> ");
    private Setting<EncryptionMode> mode = this.register(Settings.e("Mode", EncryptionMode.SHUFFLE));
    private Setting<Integer> key = this.register(Settings.i("Key", 6));
    private Setting<Boolean> delim = this.register(Settings.b("Delimiter", true));
    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage)event.getPacket()).getMessage();
            if (this.delim.getValue().booleanValue()) {
                if (!s.startsWith("%")) {
                    return;
                }
                s = s.substring(1);
            }
            StringBuilder builder = new StringBuilder();
            switch (this.mode.getValue()) {
                case SHUFFLE: {
                    builder.append(this.shuffle(this.key.getValue(), s));
                    builder.append("\ud83d\ude4d");
                    break;
                }
                case SHIFT: {
                    s.chars().forEachOrdered(value -> builder.append((char)(value + (ChatAllowedCharacters.isAllowedCharacter((char)((char)(value + this.key.getValue()))) ? this.key.getValue() : 0))));
                    builder.append("\ud83d\ude48");
                }
            }
            s = builder.toString();
            if (s.length() > 256) {
                Command.sendChatMessage("Encrypted message length was too long, couldn't send!");
                event.cancel();
                return;
            }
            ((CPacketChatMessage)event.getPacket()).message = s;
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketChat) {
            String s = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText();
            Matcher matcher = this.CHAT_PATTERN.matcher(s);
            String username = "unnamed";
            if (matcher.find()) {
                username = matcher.group();
                username = username.substring(1, username.length() - 2);
                s = matcher.replaceFirst("");
            }
            StringBuilder builder = new StringBuilder();
            switch (this.mode.getValue()) {
                case SHUFFLE: {
                    if (!s.endsWith("\ud83d\ude4d")) {
                        return;
                    }
                    s = s.substring(0, s.length() - 2);
                    builder.append(this.unshuffle(this.key.getValue(), s));
                    break;
                }
                case SHIFT: {
                    if (!s.endsWith("\ud83d\ude48")) {
                        return;
                    }
                    s = s.substring(0, s.length() - 2);
                    s.chars().forEachOrdered(value -> builder.append((char)(value + (ChatAllowedCharacters.isAllowedCharacter((char)((char)value)) ? -this.key.getValue().intValue() : 0))));
                }
            }
            ((SPacketChat)event.getPacket()).chatComponent = new TextComponentString(Command.SECTIONSIGN() + "b" + username + Command.SECTIONSIGN() + "r: " + builder.toString());
        }
    }, new Predicate[0]);

    private static <K, V> Map<V, K> reverseMap(Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    private Map<Character, Character> generateShuffleMap(int seed) {
        Random r = new Random(seed);
        List characters = CharBuffer.wrap(ORIGIN_CHARS).chars().mapToObj(value -> Character.valueOf((char)value)).collect(Collectors.toList());
        ArrayList counter = new ArrayList(characters);
        Collections.shuffle(counter, r);
        LinkedHashMap<Character, Character> map = new LinkedHashMap<Character, Character>();
        for (int i = 0; i < characters.size(); ++i) {
            map.put((Character)characters.get(i), (Character)counter.get(i));
        }
        return map;
    }

    private String shuffle(int seed, String input) {
        Map<Character, Character> s = this.generateShuffleMap(seed);
        StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, s, builder);
        return builder.toString();
    }

    private String unshuffle(int seed, String input) {
        Map<Character, Character> s = this.generateShuffleMap(seed);
        StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, ChatEncryption.reverseMap(s), builder);
        return builder.toString();
    }

    private void swapCharacters(String input, Map<Character, Character> s, StringBuilder builder) {
        CharBuffer.wrap(input.toCharArray()).chars().forEachOrdered(value -> {
            char c = (char)value;
            if (s.containsKey(Character.valueOf(c))) {
                builder.append(s.get(Character.valueOf(c)));
            } else {
                builder.append(c);
            }
        });
    }

    private static enum EncryptionMode {
        SHUFFLE,
        SHIFT;

    }
}

