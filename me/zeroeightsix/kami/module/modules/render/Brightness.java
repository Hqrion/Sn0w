/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.Stack;
import java.util.function.Function;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;

@Module.Info(name="Brightness", description="Makes everything brighter!", category=Module.Category.RENDER)
public class Brightness
extends Module {
    private Setting<Boolean> transition = this.register(Settings.b("Transition", true));
    private Setting<Float> seconds = this.register(Settings.floatBuilder("Seconds").withMinimum(Float.valueOf(0.0f)).withMaximum(Float.valueOf(10.0f)).withValue(Float.valueOf(1.0f)).withVisibility(o -> this.transition.getValue()).build());
    private Setting<Transition> mode = this.register(Settings.enumBuilder(Transition.class).withName("Mode").withValue(Transition.SINE).withVisibility(o -> this.transition.getValue()).build());
    private Stack<Float> transitionStack = new Stack();
    private static float currentBrightness = 0.0f;
    private static boolean inTransition = false;

    private void addTransition(boolean isUpwards) {
        if (this.transition.getValue().booleanValue()) {
            float[] values;
            int length = (int)(this.seconds.getValue().floatValue() * 20.0f);
            switch (this.mode.getValue()) {
                case LINEAR: {
                    values = this.linear(length, isUpwards);
                    break;
                }
                case SINE: {
                    values = this.sine(length, isUpwards);
                    break;
                }
                default: {
                    values = new float[]{0.0f};
                }
            }
            for (float v : values) {
                this.transitionStack.add(Float.valueOf(v));
            }
            inTransition = true;
        }
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        this.addTransition(true);
    }

    @Override
    protected void onDisable() {
        this.setAlwaysListening(true);
        super.onDisable();
        this.addTransition(false);
    }

    @Override
    public void onUpdate() {
        if (inTransition) {
            if (this.transitionStack.isEmpty()) {
                inTransition = false;
                this.setAlwaysListening(false);
                currentBrightness = this.isEnabled() ? 1.0f : 0.0f;
            } else {
                currentBrightness = this.transitionStack.pop().floatValue();
            }
        }
    }

    private float[] createTransition(int length, boolean upwards, Function<Float, Float> function) {
        float[] transition = new float[length];
        for (int i = 0; i < length; ++i) {
            float v = function.apply(Float.valueOf((float)i / (float)length)).floatValue();
            if (upwards) {
                v = 1.0f - v;
            }
            transition[i] = v;
        }
        return transition;
    }

    private float[] linear(int length, boolean polarity) {
        return this.createTransition(length, polarity, d -> d);
    }

    private float sine(float x) {
        return ((float)Math.sin(Math.PI * (double)x - 1.5707963267948966) + 1.0f) / 2.0f;
    }

    private float[] sine(int length, boolean polarity) {
        return this.createTransition(length, polarity, this::sine);
    }

    public static float getCurrentBrightness() {
        return currentBrightness;
    }

    public static boolean isInTransition() {
        return inTransition;
    }

    public static boolean shouldBeActive() {
        return Brightness.isInTransition() || currentBrightness == 1.0f;
    }

    public static enum Transition {
        LINEAR,
        SINE;

    }
}

