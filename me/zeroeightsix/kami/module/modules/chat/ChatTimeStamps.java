/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 */
package me.zeroeightsix.kami.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

@Module.Info(name="ChatTimeStamps", category=Module.Category.CHAT)
public class ChatTimeStamps
extends Module {
    private Setting<Boolean> deco = this.register(Settings.b("Deco", true));
    @EventHandler
    public Listener<ClientChatReceivedEvent> listener = new Listener<ClientChatReceivedEvent>(event -> {
        TextComponentString newTextComponentString = new TextComponentString(ChatFormatting.GRAY + (this.deco.getValue() != false ? "<" : "") + new SimpleDateFormat("k:mm").format(new Date()) + (this.deco.getValue() != false ? ">" : "") + ChatFormatting.RESET + " ");
        newTextComponentString.func_150257_a(event.getMessage());
        event.setMessage((ITextComponent)newTextComponentString);
    }, new Predicate[0]);
}

