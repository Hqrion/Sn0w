/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.rgui.component.use;

import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;

public class Label
extends AlignedComponent {
    String text;
    boolean multiline;
    boolean shadow;
    FontRenderer fontRenderer;

    public Label(String text) {
        this(text, false);
    }

    public Label(String text, boolean multiline) {
        this.text = text;
        this.multiline = multiline;
        this.setAlignment(AlignedComponent.Alignment.LEFT);
    }

    public String getText() {
        return this.text;
    }

    public String[] getLines() {
        String[] lines = this.isMultiline() ? this.getText().split(System.lineSeparator()) : new String[]{this.getText()};
        return lines;
    }

    public void setText(String text) {
        this.text = text;
        this.getTheme().getUIForComponent(this).handleSizeComponent(this);
    }

    public void addText(String add) {
        this.setText(this.getText() + add);
    }

    public void addLine(String add) {
        if (this.getText().isEmpty()) {
            this.setText(add);
        } else {
            this.setText(this.getText() + System.lineSeparator() + add);
            this.multiline = true;
        }
    }

    public boolean isMultiline() {
        return this.multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    public boolean isShadow() {
        return this.shadow;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public void setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void setTheme(Theme theme) {
        super.setTheme(theme);
        this.setFontRenderer(theme.getFontRenderer());
        this.getTheme().getUIForComponent(this).handleSizeComponent(this);
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}

