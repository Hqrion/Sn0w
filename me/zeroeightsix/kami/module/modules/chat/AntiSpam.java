/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.play.server.SPacketChat
 */
package me.zeroeightsix.kami.module.modules.chat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketChat;

@Module.Info(name="AntiSpam", category=Module.Category.CHAT)
public class AntiSpam
extends Module {
    private Setting<Boolean> greenText = this.register(Settings.b("Green Text", true));
    private Setting<Boolean> discordLinks = this.register(Settings.b("Discord Links", false));
    private Setting<Boolean> webLinks = this.register(Settings.b("Web Links", false));
    private Setting<Boolean> announcers = this.register(Settings.b("Announcers", false));
    private Setting<Boolean> spammers = this.register(Settings.b("Announcers", false));
    private Setting<Boolean> insulters = this.register(Settings.b("Insulters", false));
    private Setting<Boolean> greeters = this.register(Settings.b("Greeters", false));
    private Setting<Boolean> tradeChat = this.register(Settings.b("Trade Chat", false));
    private Setting<Boolean> numberSuffix = this.register(Settings.b("Number Suffix", false));
    private Setting<Boolean> duplicates = this.register(Settings.b("Duplicates", false));
    private Setting<Integer> duplicatesTimeout = this.register(Settings.integerBuilder("Duplicates Timeout").withMinimum(1).withValue(10).withMaximum(600).build());
    private Setting<Boolean> filterOwn = this.register(Settings.b("Filter Own", false));
    private Setting<Boolean> debug = this.register(Settings.b("Debug Messages", false));
    private ConcurrentHashMap<String, Long> messageHistory;
    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<PacketEvent.Receive>(event -> {
        if (AntiSpam.mc.field_71439_g == null || this.isDisabled()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketChat)) {
            return;
        }
        SPacketChat sPacketChat = (SPacketChat)event.getPacket();
        if (this.detectSpam(sPacketChat.func_148915_c().func_150260_c())) {
            event.cancel();
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        this.messageHistory = new ConcurrentHashMap();
    }

    @Override
    public void onDisable() {
        this.messageHistory = null;
    }

    private boolean detectSpam(String message) {
        if (!this.filterOwn.getValue().booleanValue() && this.findPatterns(FilterPatterns.OWN_MESSAGE, message)) {
            return false;
        }
        if (this.greenText.getValue().booleanValue() && this.findPatterns(FilterPatterns.GREEN_TEXT, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Green Text: " + message);
            }
            return true;
        }
        if (this.discordLinks.getValue().booleanValue() && this.findPatterns(FilterPatterns.DISCORD, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Discord Link: " + message);
            }
            return true;
        }
        if (this.webLinks.getValue().booleanValue() && this.findPatterns(FilterPatterns.WEB_LINK, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Web Link: " + message);
            }
            return true;
        }
        if (this.tradeChat.getValue().booleanValue() && this.findPatterns(FilterPatterns.TRADE_CHAT, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Trade Chat: " + message);
            }
            return true;
        }
        if (this.numberSuffix.getValue().booleanValue() && this.findPatterns(FilterPatterns.NUMBER_SUFFIX, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Number Suffix: " + message);
            }
            return true;
        }
        if (this.announcers.getValue().booleanValue() && this.findPatterns(FilterPatterns.ANNOUNCER, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Announcer: " + message);
            }
            return true;
        }
        if (this.spammers.getValue().booleanValue() && this.findPatterns(FilterPatterns.SPAMMER, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Spammers: " + message);
            }
            return true;
        }
        if (this.insulters.getValue().booleanValue() && this.findPatterns(FilterPatterns.INSULTER, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Insulter: " + message);
            }
            return true;
        }
        if (this.greeters.getValue().booleanValue() && this.findPatterns(FilterPatterns.GREETER, message)) {
            if (this.debug.getValue().booleanValue()) {
                Command.sendChatMessage("[AntiSpam] Greeter: " + message);
            }
            return true;
        }
        if (this.duplicates.getValue().booleanValue()) {
            if (this.messageHistory == null) {
                this.messageHistory = new ConcurrentHashMap();
            }
            boolean isDuplicate = false;
            if (this.messageHistory.containsKey(message) && (System.currentTimeMillis() - this.messageHistory.get(message)) / 1000L < (long)this.duplicatesTimeout.getValue().intValue()) {
                isDuplicate = true;
            }
            this.messageHistory.put(message, System.currentTimeMillis());
            if (isDuplicate) {
                if (this.debug.getValue().booleanValue()) {
                    Command.sendChatMessage("[AntiSpam] Duplicate: " + message);
                }
                return true;
            }
        }
        return false;
    }

    private boolean findPatterns(String[] patterns, String string) {
        for (String pattern : patterns) {
            if (!Pattern.compile(pattern).matcher(string).find()) continue;
            return true;
        }
        return false;
    }

    static /* synthetic */ Minecraft access$1000() {
        return mc;
    }

    private static class FilterPatterns {
        private static final String[] ANNOUNCER = new String[]{"I just walked .+ feet!", "I just placed a .+!", "I just attacked .+ with a .+!", "I just dropped a .+!", "I just opened chat!", "I just opened my console!", "I just opened my GUI!", "I just went into full screen mode!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just took a screen shot!", "I just swaped hands!", "I just ducked!", "I just changed perspectives!", "I just jumped!", "I just ate a .+!", "I just crafted .+ .+!", "I just picked up a .+!", "I just smelted .+ .+!", "I just respawned!", "I just attacked .+ with my hands", "I just broke a .+!", "I recently walked .+ blocks", "I just droped a .+ called, .+!", "I just placed a block called, .+!", "Im currently breaking a block called, .+!", "I just broke a block called, .+!", "I just opened chat!", "I just opened chat and typed a slash!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just changed perspectives, now im in .+!", "I just crouched!", "I just jumped!", "I just attacked a entity called, .+ with a .+", "Im currently eatting a peice of food called, .+!", "Im currently using a item called, .+!", "I just toggled full screen mode!", "I just took a screen shot!", "I just swaped hands and now theres a .+ in my main hand and a .+ in my off hand!", "I just used pick block on a block called, .+!", "Ra just completed his blazing ark", "Its a new day yes it is", "I just placed .+ thanks to (http:\\/\\/)?DotGod\\.CC!", "I just flew .+ meters like a butterfly thanks to (http:\\/\\/)?DotGod\\.CC!"};
        private static final String[] SPAMMER = new String[]{"WWE Client's spammer", "Lol get gud", "Future client is bad", "WWE > Future", "WWE > Impact", "Default Message", "IKnowImEZ is a god", "THEREALWWEFAN231 is a god", "WWE Client made by IKnowImEZ/THEREALWWEFAN231", "WWE Client was the first public client to have Path Finder/New Chunks", "WWE Client was the first public client to have color signs", "WWE Client was the first client to have Teleport Finder", "WWE Client was the first client to have Tunneller & Tunneller Back Fill"};
        private static final String[] INSULTER = new String[]{".+ Download WWE utility mod, Its free!", ".+ 4b4t is da best mintscreft serber", ".+ dont abouse", ".+ you cuck", ".+ https://www.youtube.com/channel/UCJGCNPEjvsCn0FKw3zso0TA", ".+ is my step dad", ".+ again daddy!", "dont worry .+ it happens to every one", ".+ dont buy future it's crap, compared to WWE!", "What are you, fucking gay, .+?", "Did you know? .+ hates you, .+", "You are literally 10, .+", ".+ finally lost their virginity, sadly they lost it to .+... yeah, that's unfortunate.", ".+, don't be upset, it's not like anyone cares about you, fag.", ".+, see that rubbish bin over there? Get your ass in it, or I'll get .+ to whoop your ass.", ".+, may I borrow that dirt block? that guy named .+ needs it...", "Yo, .+, btfo you virgin", "Hey .+ want to play some High School RP with me and .+?", ".+ is an Archon player. Why is he on here? Fucking factions player.", "Did you know? .+ just joined The Vortex Coalition!", ".+ has successfully conducted the cactus dupe and duped a itemhand!", ".+, are you even human? You act like my dog, holy shit.", ".+, you were never loved by your family.", "Come on .+, you hurt .+'s feelings. You meany.", "Stop trying to meme .+, you can't do that. kek", ".+, .+ is gay. Don't go near him.", "Whoa .+ didn't mean to offend you, .+.", ".+ im not pvping .+, im WWE'ing .+.", "Did you know? .+ just joined The Vortex Coalition!", ".+, are you even human? You act like my dog, holy shit."};
        private static final String[] GREETER = new String[]{"Bye, Bye .+", "Farwell, .+"};
        private static final String[] DISCORD = new String[]{"discord.gg"};
        private static final String[] NUMBER_SUFFIX = new String[]{".+-?\\d{3,100}$"};
        private static final String[] GREEN_TEXT = new String[]{"^<.+> >"};
        private static final String[] TRADE_CHAT = new String[]{"buy", "sell"};
        private static final String[] WEB_LINK = new String[]{"http:\\/\\/", "https:\\/\\/"};
        private static final String[] OWN_MESSAGE = new String[]{"^<" + AntiSpam.access$1000().field_71439_g.func_70005_c_() + "> ", "^To .+: "};

        private FilterPatterns() {
        }
    }
}

