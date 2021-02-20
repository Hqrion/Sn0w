/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  org.lwjgl.opengl.GL11
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

@Module.Info(name="HoleEsp", category=Module.Category.RENDER)
public class HoleESP
extends Module {
    private Setting<Boolean> hideOwn = this.register(Settings.b("HideOwn", false));
    private Setting<Double> range = this.register(Settings.d("Range", 8.0));
    private Setting<Boolean> highlight = this.register(Settings.b("Block Highlight", true));
    private Setting<Boolean> box = this.register(Settings.b("Bouding Box", true));
    private Setting<Boolean> bottom = this.register(Settings.b("Bottom Highlight", false));
    private Setting<Boolean> bottomBox = this.register(Settings.b("Bottom Boudning Box", false));
    private Setting<Double> chromaSpeed = this.register(Settings.d("Chroma Speed", 3.0));
    private Setting<Boolean> obbyChroma = this.register(Settings.b("Obbi Chroma", false));
    private Setting<Integer> obbyRed = this.register(Settings.integerBuilder("Obbi Red").withMinimum(0).withMaximum(255).withValue(143).build());
    private Setting<Integer> obbyGreen = this.register(Settings.integerBuilder("Obbi Green").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> obbyBlue = this.register(Settings.integerBuilder("Obbi Blue").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Boolean> bRockChroma = this.register(Settings.b("BRock Chroma", false));
    private Setting<Integer> bRockRed = this.register(Settings.integerBuilder("BRock Red").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> bRockGreen = this.register(Settings.integerBuilder("BRock Green").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Integer> bRockBlue = this.register(Settings.integerBuilder("BRock Blue").withMinimum(0).withMaximum(255).withValue(0).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(26).build());
    private Setting<Integer> alpha2 = this.register(Settings.integerBuilder("Bounding Box Alpha").withMinimum(0).withMaximum(255).withValue(255).build());
    private Setting<Float> width = this.register(Settings.floatBuilder("Line Width").withMinimum(Float.valueOf(0.0f)).withMaximum(Float.valueOf(7.0f)).withValue(Float.valueOf(1.04f)).build());
    private BlockPos render;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener = new Listener<PacketEvent.Send>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).field_149476_e = (float)yaw;
            ((CPacketPlayer)packet).field_149473_f = (float)pitch;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        BlockPos blockPos;
        List<BlockPos> bRockHoles = this.findBRockHoles();
        List<BlockPos> obbyHoles = this.findObbyHoles();
        BlockPos q = null;
        Iterator<BlockPos> iterator = bRockHoles.iterator();
        while (iterator.hasNext()) {
            q = blockPos = iterator.next();
        }
        iterator = obbyHoles.iterator();
        while (iterator.hasNext()) {
            q = blockPos = iterator.next();
        }
        this.render = q;
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f * this.chromaSpeed.getValue().floatValue()};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        GL11.glEnable((int)2884);
        if (this.render != null) {
            for (BlockPos hole : this.findObbyHoles()) {
                if (this.hideOwn.getValue().booleanValue() && hole.equals((Object)new BlockPos(HoleESP.mc.field_71439_g.field_70165_t, HoleESP.mc.field_71439_g.field_70163_u, HoleESP.mc.field_71439_g.field_70161_v))) continue;
                if (this.obbyChroma.getValue().booleanValue()) {
                    if (this.highlight.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBox(hole, red, green, blue, (int)this.alpha.getValue(), 63);
                        KamiTessellator.release();
                    }
                    if (this.box.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                        KamiTessellator.release();
                    }
                    if (this.bottom.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoxBottom(hole, red, green, blue, this.alpha.getValue());
                        KamiTessellator.release();
                    }
                    if (!this.bottomBox.getValue().booleanValue()) continue;
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                    KamiTessellator.release();
                    continue;
                }
                if (this.highlight.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBox(hole, (int)this.obbyRed.getValue(), (int)this.obbyGreen.getValue(), (int)this.obbyBlue.getValue(), (int)this.alpha.getValue(), 63);
                    KamiTessellator.release();
                }
                if (this.box.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha2.getValue());
                    KamiTessellator.release();
                }
                if (this.bottom.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoxBottom(hole, this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha.getValue());
                    KamiTessellator.release();
                }
                if (!this.bottomBox.getValue().booleanValue()) continue;
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), this.obbyRed.getValue(), this.obbyGreen.getValue(), this.obbyBlue.getValue(), this.alpha2.getValue());
                KamiTessellator.release();
            }
            for (BlockPos hole : this.findBRockHoles()) {
                if (this.hideOwn.getValue().booleanValue() && hole.equals((Object)new BlockPos(HoleESP.mc.field_71439_g.field_70165_t, HoleESP.mc.field_71439_g.field_70163_u, HoleESP.mc.field_71439_g.field_70161_v))) continue;
                if (this.bRockChroma.getValue().booleanValue()) {
                    if (this.highlight.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBox(hole, red, green, blue, (int)this.alpha.getValue(), 63);
                        KamiTessellator.release();
                    }
                    if (this.box.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                        KamiTessellator.release();
                    }
                    if (this.bottom.getValue().booleanValue()) {
                        KamiTessellator.prepare(7);
                        KamiTessellator.drawBoxBottom(hole, red, green, blue, this.alpha.getValue());
                        KamiTessellator.release();
                    }
                    if (!this.bottomBox.getValue().booleanValue()) continue;
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), red, green, blue, this.alpha2.getValue());
                    KamiTessellator.release();
                    continue;
                }
                if (this.highlight.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBox(hole, (int)this.bRockRed.getValue(), (int)this.bRockGreen.getValue(), (int)this.bRockBlue.getValue(), (int)this.alpha.getValue(), 63);
                    KamiTessellator.release();
                }
                if (this.box.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoundingBoxBlockPos(hole, this.width.getValue().floatValue(), this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha2.getValue());
                    KamiTessellator.release();
                }
                if (this.bottom.getValue().booleanValue()) {
                    KamiTessellator.prepare(7);
                    KamiTessellator.drawBoxBottom(hole, this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha.getValue());
                    KamiTessellator.release();
                }
                if (!this.bottomBox.getValue().booleanValue()) continue;
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBottomBlockPos(hole, this.width.getValue().floatValue(), this.bRockRed.getValue(), this.bRockGreen.getValue(), this.bRockBlue.getValue(), this.alpha2.getValue());
                KamiTessellator.release();
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        HoleESP.setYawAndPitch((float)v[0], (float)v[1]);
    }

    private boolean IsObbyHole(BlockPos blockPos) {
        BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        BlockPos boost2 = blockPos.func_177982_a(0, 0, 0);
        BlockPos boost3 = blockPos.func_177982_a(0, 0, -1);
        BlockPos boost4 = blockPos.func_177982_a(1, 0, 0);
        BlockPos boost5 = blockPos.func_177982_a(-1, 0, 0);
        BlockPos boost6 = blockPos.func_177982_a(0, 0, 1);
        BlockPos boost7 = blockPos.func_177982_a(0, 2, 0);
        BlockPos boost8 = blockPos.func_177963_a(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.func_177982_a(0, -1, 0);
        return !(HoleESP.mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || this.IsBRockHole(blockPos) || HoleESP.mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a || HoleESP.mc.field_71441_e.func_180495_p(boost7).func_177230_c() != Blocks.field_150350_a || HoleESP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() != Blocks.field_150343_Z && HoleESP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() != Blocks.field_150357_h || HoleESP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() != Blocks.field_150343_Z && HoleESP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() != Blocks.field_150357_h || HoleESP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() != Blocks.field_150343_Z && HoleESP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() != Blocks.field_150357_h || HoleESP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() != Blocks.field_150343_Z && HoleESP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() != Blocks.field_150357_h || HoleESP.mc.field_71441_e.func_180495_p(boost8).func_177230_c() != Blocks.field_150350_a || HoleESP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() != Blocks.field_150343_Z && HoleESP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() != Blocks.field_150357_h);
    }

    private boolean IsBRockHole(BlockPos blockPos) {
        BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        BlockPos boost2 = blockPos.func_177982_a(0, 0, 0);
        BlockPos boost3 = blockPos.func_177982_a(0, 0, -1);
        BlockPos boost4 = blockPos.func_177982_a(1, 0, 0);
        BlockPos boost5 = blockPos.func_177982_a(-1, 0, 0);
        BlockPos boost6 = blockPos.func_177982_a(0, 0, 1);
        BlockPos boost7 = blockPos.func_177982_a(0, 2, 0);
        BlockPos boost8 = blockPos.func_177963_a(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.func_177982_a(0, -1, 0);
        return HoleESP.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && HoleESP.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && HoleESP.mc.field_71441_e.func_180495_p(boost7).func_177230_c() == Blocks.field_150350_a && HoleESP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150357_h && HoleESP.mc.field_71441_e.func_180495_p(boost8).func_177230_c() == Blocks.field_150350_a && HoleESP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150357_h;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.field_71439_g.field_70165_t), Math.floor(HoleESP.mc.field_71439_g.field_70163_u), Math.floor(HoleESP.mc.field_71439_g.field_70161_v));
    }

    private List<BlockPos> findObbyHoles() {
        NonNullList positions = NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsObbyHole).collect(Collectors.toList()));
        return positions;
    }

    private List<BlockPos> findBRockHoles() {
        NonNullList positions = NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(HoleESP.getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter(this::IsBRockHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.func_177958_n();
        int cy = loc.func_177956_o();
        int cz = loc.func_177952_p();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f;
                    float f2 = f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = HoleESP.mc.field_71439_g.field_70177_z;
            pitch = HoleESP.mc.field_71439_g.field_70125_A;
            isSpoofingAngles = false;
        }
    }

    @Override
    public void onDisable() {
        this.render = null;
        HoleESP.resetRotation();
    }
}

