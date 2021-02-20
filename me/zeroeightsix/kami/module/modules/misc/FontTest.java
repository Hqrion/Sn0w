/*
 * Decompiled with CFR 0.151.
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.awt.GraphicsEnvironment;
import me.zeroeightsix.kami.module.Module;

@Module.Info(name="FONT TEST", description="Fast Bow Release", category=Module.Category.MISC)
public class FontTest
extends Module {
    @Override
    public void onEnable() {
        if (FontTest.mc.field_71439_g != null) {
            for (int i = 0; i < GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts().length; ++i) {
                FontTest.mc.field_71439_g.func_71165_d(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[i].getFontName());
            }
        }
    }
}

