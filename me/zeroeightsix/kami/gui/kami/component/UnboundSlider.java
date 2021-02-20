/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.kami.component;

import me.zeroeightsix.kami.gui.rgui.component.AbstractComponent;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.use.Slider;

public class UnboundSlider
extends AbstractComponent {
    double value;
    String text;
    public int sensitivity = 5;
    int originX;
    double originValue;
    boolean integer;
    double max = Double.MAX_VALUE;
    double min = Double.MIN_VALUE;

    public UnboundSlider(double value, String text, boolean integer) {
        this.value = value;
        this.text = text;
        this.integer = integer;
        this.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                UnboundSlider.this.originX = event.getX();
                UnboundSlider.this.originValue = UnboundSlider.this.getValue();
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
                UnboundSlider.this.originValue = UnboundSlider.this.getValue();
                UnboundSlider.this.originX = event.getX();
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
                int diff = (UnboundSlider.this.originX - event.getX()) / UnboundSlider.this.sensitivity;
                UnboundSlider.this.setValue(Math.floor((UnboundSlider.this.originValue - (double)diff * (UnboundSlider.this.originValue == 0.0 ? 1.0 : Math.abs(UnboundSlider.this.originValue) / 10.0)) * 10.0) / 10.0);
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
                UnboundSlider.this.setValue(Math.round(UnboundSlider.this.getValue() + (double)(event.isUp() ? 1 : -1)));
                UnboundSlider.this.originValue = UnboundSlider.this.getValue();
            }
        });
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setValue(double value) {
        if (this.min != Double.MIN_VALUE) {
            value = Math.max(value, this.min);
        }
        if (this.max != Double.MAX_VALUE) {
            value = Math.min(value, this.max);
        }
        Slider.SliderPoof.SliderPoofInfo info = new Slider.SliderPoof.SliderPoofInfo(this.value, value);
        this.callPoof(Slider.SliderPoof.class, info);
        this.value = this.integer ? Math.floor(info.getNewValue()) : info.getNewValue();
    }

    public double getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }
}

