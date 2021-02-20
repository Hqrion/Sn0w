/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ConcurrentSet
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.module.modules.render;

import io.netty.util.internal.ConcurrentSet;
import java.awt.Color;
import java.util.List;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.combat.CrystalAura;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="VoidESP", category=Module.Category.RENDER, description="Show void holes")
public class VoidESP
extends Module {
    private Setting<Integer> range = this.register(Settings.integerBuilder("Range").withMinimum(1).withValue(10).withMaximum(32).build());
    private Setting<Integer> activateAtY = this.register(Settings.integerBuilder("ActivateAtY").withMinimum(1).withValue(32).withMaximum(512).build());
    private Setting<HoleMode> holeMode = this.register(Settings.e("HoleMode", HoleMode.SIDES));
    private Setting<RenderMode> renderMode = this.register(Settings.e("RenderMode", RenderMode.BLOCK));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withMinimum(0).withValue(0).withMaximum(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withMinimum(0).withValue(0).withMaximum(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withMinimum(0).withValue(65).withMaximum(255).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(30).withMaximum(255).build());
    private ConcurrentSet<BlockPos> voidHoles;

    @Override
    public void onUpdate() {
        if (VoidESP.mc.field_71439_g == null) {
            return;
        }
        if (VoidESP.mc.field_71439_g.field_71093_bK == 1) {
            return;
        }
        if (VoidESP.mc.field_71439_g.func_180425_c().field_177960_b > this.activateAtY.getValue()) {
            return;
        }
        if (this.voidHoles == null) {
            this.voidHoles = new ConcurrentSet();
        } else {
            this.voidHoles.clear();
        }
        List<BlockPos> blockPosList = BlockInteractionHelper.getCircle(CrystalAura.getPlayerPos(), 0, this.range.getValue().intValue(), false);
        for (BlockPos pos : blockPosList) {
            if (VoidESP.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150357_h) || this.isAnyBedrock(pos, Offsets.center)) continue;
            boolean aboveFree = false;
            if (!this.isAnyBedrock(pos, Offsets.above)) {
                aboveFree = true;
            }
            if (this.holeMode.getValue().equals((Object)HoleMode.ABOVE)) {
                if (!aboveFree) continue;
                this.voidHoles.add((Object)pos);
                continue;
            }
            boolean sidesFree = false;
            if (!this.isAnyBedrock(pos, Offsets.north)) {
                sidesFree = true;
            }
            if (!this.isAnyBedrock(pos, Offsets.east)) {
                sidesFree = true;
            }
            if (!this.isAnyBedrock(pos, Offsets.south)) {
                sidesFree = true;
            }
            if (!this.isAnyBedrock(pos, Offsets.west)) {
                sidesFree = true;
            }
            if (!this.holeMode.getValue().equals((Object)HoleMode.SIDES) || !aboveFree && !sidesFree) continue;
            this.voidHoles.add((Object)pos);
        }
    }

    private boolean isAnyBedrock(BlockPos origin, BlockPos[] offset) {
        for (BlockPos pos : offset) {
            if (!VoidESP.mc.field_71441_e.func_180495_p(origin.func_177971_a((Vec3i)pos)).func_177230_c().equals(Blocks.field_150357_h)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (VoidESP.mc.field_71439_g == null || this.voidHoles == null || this.voidHoles.isEmpty()) {
            return;
        }
        KamiTessellator.prepare(7);
        this.voidHoles.forEach(blockPos -> this.drawBlock((BlockPos)blockPos, this.red.getValue(), this.green.getValue(), this.blue.getValue()));
        KamiTessellator.release();
    }

    private void drawBlock(BlockPos blockPos, int r, int g, int b) {
        Color color = new Color(r, g, b, this.alpha.getValue());
        int mask = 0;
        if (this.renderMode.getValue().equals((Object)RenderMode.BLOCK)) {
            mask = 63;
        }
        if (this.renderMode.getValue().equals((Object)RenderMode.DOWN)) {
            mask = 1;
        }
        KamiTessellator.drawBox(blockPos, color.getRGB(), mask);
    }

    @Override
    public String getHudInfo() {
        return this.holeMode.getValue().toString();
    }

    private static class Offsets {
        static final BlockPos[] center = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(0, 2, 0)};
        static final BlockPos[] above = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(0, 4, 0)};
        static final BlockPos[] aboveStep1 = new BlockPos[]{new BlockPos(0, 3, 0)};
        static final BlockPos[] aboveStep2 = new BlockPos[]{new BlockPos(0, 4, 0)};
        static final BlockPos[] north = new BlockPos[]{new BlockPos(0, 1, -1), new BlockPos(0, 2, -1)};
        static final BlockPos[] east = new BlockPos[]{new BlockPos(1, 1, 0), new BlockPos(1, 2, 0)};
        static final BlockPos[] south = new BlockPos[]{new BlockPos(0, 1, 1), new BlockPos(0, 2, 1)};
        static final BlockPos[] west = new BlockPos[]{new BlockPos(-1, 1, 0), new BlockPos(-1, 2, 0)};

        private Offsets() {
        }
    }

    private static enum HoleMode {
        SIDES,
        ABOVE;

    }

    private static enum RenderMode {
        DOWN,
        BLOCK;

    }
}

