/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.modules.combat.CrystalAura;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

@Module.Info(name="BlockHighlight", description="Idk inspired by hack hack", category=Module.Category.RENDER)
public class BlockHighlight
extends Module {
    private Setting<Boolean> boundingbox = this.register(Settings.b("Bouding Box", true));
    private Setting<Boolean> box = this.register(Settings.b("Full Block Highlight", false));
    private Setting<Double> width = this.register(Settings.d("Width", 1.5));
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(1).withMaximum(255).withValue(30));
    private Setting<Integer> Red = this.register(Settings.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> Green = this.register(Settings.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> Blue = this.register(Settings.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue(255));
    private Setting<Integer> alpha2 = this.register(Settings.integerBuilder("Bounding Box Alpha").withMinimum(1).withMaximum(255).withValue(200));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Boolean> Stringlolol = this.register(Settings.b("Draw Coords", false));

    @Override
    public void onWorldRender(RenderEvent event) {
        BlockPos blockpos;
        IBlockState iblockstate;
        float[] hue = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        Minecraft mc = Minecraft.func_71410_x();
        RayTraceResult ray = mc.field_71476_x;
        if (ray.field_72313_a == RayTraceResult.Type.BLOCK && (iblockstate = mc.field_71441_e.func_180495_p(blockpos = ray.func_178782_a())).func_185904_a() != Material.field_151579_a && mc.field_71441_e.func_175723_af().func_177746_a(blockpos)) {
            if (this.box.getValue().booleanValue()) {
                KamiTessellator.prepare(7);
                if (this.rainbow.getValue().booleanValue()) {
                    KamiTessellator.drawBox(blockpos, r, g, b, (int)this.alpha.getValue(), 63);
                } else {
                    KamiTessellator.drawBox(blockpos, (int)this.Red.getValue(), (int)this.Green.getValue(), (int)this.Blue.getValue(), (int)this.alpha.getValue(), 63);
                }
                KamiTessellator.release();
            }
            if (this.boundingbox.getValue().booleanValue()) {
                KamiTessellator.prepare(7);
                if (this.rainbow.getValue().booleanValue()) {
                    KamiTessellator.drawBoundingBoxBlockPos(blockpos, this.width.getValue().floatValue(), r, g, b, this.alpha2.getValue());
                } else {
                    KamiTessellator.drawBoundingBoxBlockPos(blockpos, this.width.getValue().floatValue(), this.Red.getValue(), this.Green.getValue(), this.Blue.getValue(), this.alpha2.getValue());
                }
                if (this.Stringlolol.getValue().booleanValue()) {
                    this.drawString(blockpos, blockpos.field_177962_a + ", " + blockpos.field_177960_b + ", " + blockpos.field_177961_c);
                }
                KamiTessellator.release();
            }
        }
    }

    private void drawString(BlockPos blockPos, String str) {
        GlStateManager.func_179094_E();
        CrystalAura.glBillboardDistanceScaled((float)blockPos.field_177962_a + 0.5f, (float)blockPos.field_177960_b + 0.5f, (float)blockPos.field_177961_c + 0.5f, (EntityPlayer)BlockHighlight.mc.field_71439_g, 1.5f);
        GlStateManager.func_179097_i();
        GlStateManager.func_179137_b((double)(-((double)BlockHighlight.mc.field_71466_p.func_78256_a(str) / 2.0)), (double)0.0, (double)0.0);
        BlockHighlight.mc.field_71466_p.func_175063_a(str, 0.0f, 0.0f, 24063);
        GlStateManager.func_179121_F();
    }
}

