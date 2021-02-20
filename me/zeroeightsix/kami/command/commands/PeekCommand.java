//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityShulkerBox
 */
package me.zeroeightsix.kami.command.commands;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class PeekCommand
extends Command {
    public static TileEntityShulkerBox sb;

    public PeekCommand() {
        super("peek", SyntaxChunk.EMPTY);
    }

    @Override
    public void call(String[] args) {
        ItemStack is = Wrapper.getPlayer().inventory.getCurrentItem();
        if (is.getItem() instanceof ItemShulkerBox) {
            TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            entityBox.blockType = ((ItemShulkerBox)is.getItem()).getBlock();
            entityBox.setWorld(Wrapper.getWorld());
            entityBox.readFromNBT(is.getTagCompound().getCompoundTag("BlockEntityTag"));
            sb = entityBox;
        } else {
            Command.sendChatMessage("You aren't carrying a shulker box.");
        }
    }
}

