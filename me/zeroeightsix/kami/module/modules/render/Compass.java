/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import net.minecraft.util.math.MathHelper;

@Module.Info(name="Compass", category=Module.Category.GUI)
public class Compass
extends Module {
    private Setting<Integer> scale = this.register(Settings.integerBuilder("Scale").withMinimum(0).withValue(3).withMaximum(3).build());
    private Setting<Integer> optionX = this.register(Settings.integerBuilder("X").withMinimum(0).withValue(400).withMaximum(2000).build());
    private Setting<Integer> optionY = this.register(Settings.integerBuilder("Y").withMinimum(0).withValue(400).withMaximum(2000).build());
    private static final double HALF_PI = 1.5707963267948966;
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());

    @Override
    public void onRender() {
        int drgb;
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int color = drgb = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        for (Direction dir : Direction.values()) {
            double rad = Compass.getPosOnCompass(dir);
            Compass.mc.field_71466_p.func_175063_a(dir.name(), (float)((double)this.optionX.getValue().intValue() + this.getX(rad)), (float)((double)this.optionY.getValue().intValue() + this.getY(rad)), dir == Direction.N ? color : ColourUtils.Colors.WHITE);
        }
    }

    private double getX(double rad) {
        return Math.sin(rad) * (double)(this.scale.getValue() * 10);
    }

    private double getY(double rad) {
        double epicPitch = MathHelper.func_76131_a((float)(Compass.mc.field_71439_g.field_70125_A + 30.0f), (float)-90.0f, (float)90.0f);
        double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * (double)(this.scale.getValue() * 10);
    }

    private static double getPosOnCompass(Direction dir) {
        double yaw = Math.toRadians(MathHelper.func_76142_g((float)Compass.mc.field_71439_g.field_70177_z));
        int index = dir.ordinal();
        return yaw + (double)index * 1.5707963267948966;
    }

    private static enum Direction {
        N,
        W,
        S,
        E;

    }
}

