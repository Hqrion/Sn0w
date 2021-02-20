/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package me.zeroeightsix.kami.gui.rgui.component.use;

import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.poof.PoofInfo;
import me.zeroeightsix.kami.gui.rgui.poof.use.Poof;
import net.minecraft.util.math.MathHelper;

public class Slider
extends AbstractComponent {
    double value;
    double minimum;
    double maximum;
    double step;
    String text;
    boolean integer;

    public Slider(double value, double minimum, double maximum, double step, String text, boolean integer) {
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.text = text;
        this.integer = integer;
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                Slider.this.setValue(Slider.this.calculateValue(event.getX()));
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                Slider.this.setValue(Slider.this.calculateValue(event.getX()));
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
    }

    public Slider(double value, double minimum, double maximum, String text) {
        this(value, minimum, maximum, Slider.getDefaultStep(minimum, maximum), text, false);
    }

    private double calculateValue(double x) {
        double d1 = x / (double)this.getWidth();
        double d2 = this.maximum - this.minimum;
        double s = d1 * d2 + this.minimum;
        return MathHelper.func_151237_a((double)(Math.floor((double)Math.round(s / this.step) * this.step * 100.0) / 100.0), (double)this.minimum, (double)this.maximum);
    }

    public static double getDefaultStep(double min, double max) {
        double s = Slider.gcd(min, max);
        if (s == max) {
            s = max / 20.0;
        }
        if (max > 10.0) {
            s = Math.round(s);
        }
        if (s == 0.0) {
            s = max;
        }
        return s;
    }

    public String getText() {
        return this.text;
    }

    public double getStep() {
        return this.step;
    }

    public double getValue() {
        return this.value;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public double getMinimum() {
        return this.minimum;
    }

    public void setValue(double value) {
        SliderPoof.SliderPoofInfo info = new SliderPoof.SliderPoofInfo(this.value, value);
        this.callPoof(SliderPoof.class, info);
        double newValue = info.getNewValue();
        this.value = this.integer ? (double)((int)newValue) : newValue;
    }

    public static double gcd(double a, double b) {
        a = Math.floor(a);
        b = Math.floor(b);
        if (a == 0.0 || b == 0.0) {
            return a + b;
        }
        return Slider.gcd(b, a % b);
    }

    public static abstract class SliderPoof<T extends Component, S extends SliderPoofInfo>
    extends Poof<T, S> {

        public static class SliderPoofInfo
        extends PoofInfo {
            double oldValue;
            double newValue;

            public SliderPoofInfo(double oldValue, double newValue) {
                this.oldValue = oldValue;
                this.newValue = newValue;
            }

            public double getOldValue() {
                return this.oldValue;
            }

            public double getNewValue() {
                return this.newValue;
            }

            public void setNewValue(double newValue) {
                this.newValue = newValue;
            }
        }
    }
}

