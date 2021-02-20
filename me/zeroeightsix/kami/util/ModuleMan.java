//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 */
package me.zeroeightsix.kami.util;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class ModuleMan {
    public Integer totems;
    private String holeType = "\u00c2\u00a74 0";
    private BlockPos pos;

    public ModuleMan() {
        this.getPlayerPos();
    }

    public Boolean getPlayerPos() {
        try {
            this.pos = new BlockPos(Math.floor(Minecraft.getMinecraft().player.posX), Math.floor(Minecraft.getMinecraft().player.posY), Math.floor(Minecraft.getMinecraft().player.posZ));
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }

    public String getHoleType() {
        if (this.getPlayerPos().booleanValue()) {
            return "\u00c2\u00a74 0";
        }
        this.getPlayerPos();
        if (Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            this.holeType = "\u00a7a Safe";
            return "\u00a7a Safe";
        }
        if (Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN && Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN && Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN && Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | Minecraft.getMinecraft().world.getBlockState(this.pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
            this.holeType = "\u00a73 Unsafe";
            return "\u00a73 Unsafe";
        }
        this.holeType = "\u00a74 None";
        return "\u00a74 None";
    }
}

