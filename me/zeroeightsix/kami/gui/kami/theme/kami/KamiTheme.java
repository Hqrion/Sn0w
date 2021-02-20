/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.gui.kami.theme.kami;

import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiActiveModulesUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiEnumbuttonUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiFrameUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiSettingsPanelUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiUnboundSliderUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootButtonUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootChatUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootCheckButtonUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootColorizedCheckButtonUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootGroupboxUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootInputFieldUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootLabelUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootScrollpaneUI;
import me.zeroeightsix.kami.gui.kami.theme.kami.RootSliderUI;
import me.zeroeightsix.kami.gui.kami.theme.staticui.RadarUI;
import me.zeroeightsix.kami.gui.kami.theme.staticui.TabGuiUI;
import me.zeroeightsix.kami.gui.rgui.render.AbstractComponentUI;
import me.zeroeightsix.kami.gui.rgui.render.font.FontRenderer;
import me.zeroeightsix.kami.gui.rgui.render.theme.AbstractTheme;

public class KamiTheme
extends AbstractTheme {
    FontRenderer fontRenderer;

    public KamiTheme() {
        this.installUI(new RootButtonUI());
        this.installUI(new GUIUI());
        this.installUI(new RootGroupboxUI());
        this.installUI(new KamiFrameUI());
        this.installUI(new RootScrollpaneUI());
        this.installUI(new RootInputFieldUI());
        this.installUI(new RootLabelUI());
        this.installUI(new RootChatUI());
        this.installUI(new RootCheckButtonUI());
        this.installUI(new KamiActiveModulesUI());
        this.installUI(new KamiSettingsPanelUI());
        this.installUI(new RootSliderUI());
        this.installUI(new KamiEnumbuttonUI());
        this.installUI(new RootColorizedCheckButtonUI());
        this.installUI(new KamiUnboundSliderUI());
        this.installUI(new RadarUI());
        this.installUI(new TabGuiUI());
        this.fontRenderer = KamiGUI.fontRenderer;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public class GUIUI
    extends AbstractComponentUI<KamiGUI> {
    }
}

