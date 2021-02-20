/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.play.server.SPacketDisconnect
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package me.zeroeightsix.kami.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

@Module.Info(name="Anti32k", category=Module.Category.COMBAT)
public class kautolog
extends Module {
    private final Setting<Boolean> LogOut;
    private Set<EntityPlayer> sword = Collections.newSetFromMap(new WeakHashMap());
    public static final Minecraft mc = Minecraft.func_71410_x();

    public kautolog() {
        this.LogOut = this.register(Settings.b("Log", true));
    }

    private boolean is32k(EntityPlayer player, ItemStack stack) {
        NBTTagList enchants;
        if (stack.func_77973_b() instanceof ItemSword && (enchants = stack.func_77986_q()) != null) {
            for (int i = 0; i < enchants.func_74745_c(); ++i) {
                if (enchants.func_150305_b(i).func_74765_d("lvl") < Short.MAX_VALUE) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpdate() {
        for (EntityPlayer player : kautolog.mc.field_71441_e.field_73010_i) {
            int once = 0;
            int Distanc = (int)kautolog.mc.field_71439_g.func_70032_d((Entity)player);
            if (player.equals((Object)kautolog.mc.field_71439_g)) continue;
            if (this.is32k(player, player.field_184831_bT) && !this.sword.contains(player)) {
                Command.sendChatMessage(ChatFormatting.RED + player.getDisplayNameString() + " has a 32k");
                this.sword.add(player);
            }
            if (this.is32k(player, player.field_184831_bT)) {
                if (once > 0) {
                    return;
                }
                ++once;
                if (this.LogOut.getValue().booleanValue() && !Friends.isFriend(player.func_70005_c_()) && Distanc < 15) {
                    Minecraft.func_71410_x().func_147114_u().func_147253_a(new SPacketDisconnect((ITextComponent)new TextComponentString(ChatFormatting.BLUE + "Logged cuz nn tried to 32k you")));
                }
            }
            if (!this.sword.contains(player) || this.is32k(player, player.field_184831_bT)) continue;
            Command.sendChatMessage(ChatFormatting.GREEN + player.getDisplayNameString() + " 32k gone");
            this.sword.remove(player);
        }
    }
}

