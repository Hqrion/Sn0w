/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Team
 */
package me.zeroeightsix.kami.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

@Module.Info(name="Tab Groups", description="Shows group name in tabs", category=Module.Category.RENDER)
public class TabGroups
extends Module {
    public static TabGroups INSTANCE;

    public TabGroups() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname;
        String string = dname = networkPlayerInfoIn.func_178854_k() != null ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), (String)networkPlayerInfoIn.func_178845_a().getName());
        if (Friends.isFriend(dname)) {
            return TabGroups.IsGroup(dname) + ChatFormatting.GREEN.toString() + dname;
        }
        return TabGroups.IsGroup(dname) + dname;
    }

    public static String IsGroup(String name) {
        if (name == "Sjnez") {
            return ChatFormatting.LIGHT_PURPLE + "(WAO) " + ChatFormatting.RESET;
        }
        if (name == "khan979") {
            return ChatFormatting.LIGHT_PURPLE + "(WAO) " + ChatFormatting.RESET;
        }
        if (name == "Holecamp") {
            return ChatFormatting.LIGHT_PURPLE + "(WAO) " + ChatFormatting.RESET;
        }
        if (name == "Skitttyy") {
            return ChatFormatting.BLUE + "\u2744 " + ChatFormatting.RESET;
        }
        if (name == "zPrestige_") {
            return ChatFormatting.BLUE + "\u2744 " + ChatFormatting.RESET;
        }
        if (name == "___Daniel") {
            return ChatFormatting.BLUE + "\u2744 " + ChatFormatting.RESET;
        }
        if (name == "Johnerr") {
            return ChatFormatting.BLUE + "\u2744 " + ChatFormatting.RESET;
        }
        if (name == "WomenAreObjects") {
            return ChatFormatting.RED + "(RUN BECAUSE 9v1) " + ChatFormatting.RESET;
        }
        if (name == "Derp0") {
            return ChatFormatting.BOLD + "(TEST) " + ChatFormatting.RESET;
        }
        return "";
    }
}

