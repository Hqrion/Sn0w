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
            this.pos = new BlockPos(Math.floor(Minecraft.func_71410_x().field_71439_g.field_70165_t), Math.floor(Minecraft.func_71410_x().field_71439_g.field_70163_u), Math.floor(Minecraft.func_71410_x().field_71439_g.field_70161_v));
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
        if (Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h) {
            this.holeType = "\u00a7a Safe";
            return "\u00a7a Safe";
        }
        if (Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h | Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150343_Z && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h | Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h | Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h | Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z && Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h | Minecraft.func_71410_x().field_71441_e.func_180495_p(this.pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z) {
            this.holeType = "\u00a73 Unsafe";
            return "\u00a73 Unsafe";
        }
        this.holeType = "\u00a74 None";
        return "\u00a74 None";
    }
}

