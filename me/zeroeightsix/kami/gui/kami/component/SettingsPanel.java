/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.kami.component;

import java.util.Arrays;
import me.zeroeightsix.kami.gui.kami.Stretcherlayout;
import me.zeroeightsix.kami.gui.kami.component.BindButton;
import me.zeroeightsix.kami.gui.kami.component.EnumButton;
import me.zeroeightsix.kami.gui.kami.component.UnboundSlider;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.OrganisedContainer;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.component.use.Slider;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.impl.BooleanSetting;
import me.zeroeightsix.kami.setting.impl.EnumSetting;
import me.zeroeightsix.kami.setting.impl.numerical.DoubleSetting;
import me.zeroeightsix.kami.setting.impl.numerical.FloatSetting;
import me.zeroeightsix.kami.setting.impl.numerical.IntegerSetting;
import me.zeroeightsix.kami.setting.impl.numerical.NumberSetting;
import me.zeroeightsix.kami.util.Bind;

public class SettingsPanel
extends OrganisedContainer {
    Module module;

    public SettingsPanel(Theme theme, Module module) {
        super(theme, new Stretcherlayout(1));
        this.setAffectLayout(false);
        this.module = module;
        this.prepare();
    }

    @Override
    public void renderChildren() {
        super.renderChildren();
    }

    public Module getModule() {
        return this.module;
    }

    private void prepare() {
        this.getChildren().clear();
        if (this.module == null) {
            this.setVisible(false);
            return;
        }
        if (!this.module.settingList.isEmpty()) {
            for (final Setting setting : this.module.settingList) {
                if (!setting.isVisible()) continue;
                String name = setting.getName();
                boolean isNumber = setting instanceof NumberSetting;
                boolean isBoolean = setting instanceof BooleanSetting;
                boolean isEnum = setting instanceof EnumSetting;
                if (setting.getValue() instanceof Bind) {
                    this.addChild(new BindButton("Bind", this.module));
                }
                if (isNumber) {
                    NumberSetting numberSetting = (NumberSetting)setting;
                    boolean isBound = numberSetting.isBound();
                    double value = Double.parseDouble(numberSetting.getValue().toString());
                    if (!isBound) {
                        UnboundSlider slider = new UnboundSlider(value, name, setting instanceof IntegerSetting);
                        slider.addPoof(new Slider.SliderPoof<UnboundSlider, Slider.SliderPoof.SliderPoofInfo>(){

                            @Override
                            public void execute(UnboundSlider component, Slider.SliderPoof.SliderPoofInfo info) {
                                if (setting instanceof IntegerSetting) {
                                    setting.setValue((int)info.getNewValue());
                                } else if (setting instanceof FloatSetting) {
                                    setting.setValue(Float.valueOf((float)info.getNewValue()));
                                } else if (setting instanceof DoubleSetting) {
                                    setting.setValue(info.getNewValue());
                                }
                                SettingsPanel.this.setModule(SettingsPanel.this.module);
                            }
                        });
                        if (numberSetting.getMax() != null) {
                            slider.setMax(((Number)numberSetting.getMax()).doubleValue());
                        }
                        if (numberSetting.getMin() != null) {
                            slider.setMin(((Number)numberSetting.getMin()).doubleValue());
                        }
                        this.addChild(slider);
                        continue;
                    }
                    double min = Double.parseDouble(numberSetting.getMin().toString());
                    double max = Double.parseDouble(numberSetting.getMax().toString());
                    Slider slider = new Slider(value, min, max, Slider.getDefaultStep(min, max), name, setting instanceof IntegerSetting);
                    slider.addPoof(new Slider.SliderPoof<Slider, Slider.SliderPoof.SliderPoofInfo>(){

                        @Override
                        public void execute(Slider component, Slider.SliderPoof.SliderPoofInfo info) {
                            if (setting instanceof IntegerSetting) {
                                setting.setValue((int)info.getNewValue());
                            } else if (setting instanceof FloatSetting) {
                                setting.setValue(Float.valueOf((float)info.getNewValue()));
                            } else if (setting instanceof DoubleSetting) {
                                setting.setValue(info.getNewValue());
                            }
                        }
                    });
                    this.addChild(slider);
                    continue;
                }
                if (isBoolean) {
                    final CheckButton checkButton = new CheckButton(name);
                    checkButton.setToggled((Boolean)((BooleanSetting)setting).getValue());
                    checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>(){

                        @Override
                        public void execute(CheckButton checkButton1, CheckButton.CheckButtonPoof.CheckButtonPoofInfo info) {
                            if (info.getAction() == CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE) {
                                setting.setValue(checkButton.isToggled());
                                SettingsPanel.this.setModule(SettingsPanel.this.module);
                            }
                        }
                    });
                    this.addChild(checkButton);
                    continue;
                }
                if (!isEnum) continue;
                Class<? extends Enum> type2 = ((EnumSetting)setting).clazz;
                final Object[] con = type2.getEnumConstants();
                String[] modes = (String[])Arrays.stream(con).map(o -> o.toString().toUpperCase()).toArray(String[]::new);
                EnumButton enumbutton = new EnumButton(name, modes);
                enumbutton.addPoof(new EnumButton.EnumbuttonIndexPoof<EnumButton, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo>(){

                    @Override
                    public void execute(EnumButton component, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo info) {
                        setting.setValue(con[info.getNewIndex()]);
                        SettingsPanel.this.setModule(SettingsPanel.this.module);
                    }
                });
                enumbutton.setIndex(Arrays.asList(con).indexOf(setting.getValue()));
                this.addChild(enumbutton);
            }
        }
        if (this.children.isEmpty()) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    public void setModule(Module module) {
        this.module = module;
        this.setMinimumWidth((int)((float)this.getParent().getWidth() * 0.9f));
        this.prepare();
        this.setAffectLayout(false);
        for (Component component : this.children) {
            component.setWidth(this.getWidth() - 10);
            component.setX(5);
        }
    }
}

