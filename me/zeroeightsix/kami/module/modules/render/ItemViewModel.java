/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.util.EnumHandSide
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.TransformSideFirstPersonEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.EnumHandSide;

@Module.Info(name="Item View Model WIP", category=Module.Category.RENDER)
public class ItemViewModel
extends Module {
    private static RenderItem itemRender = Minecraft.func_71410_x().func_175599_af();
    private Setting<Double> xLeft = this.register(Settings.d("Left X", -2.0));
    private Setting<Double> yLeft = this.register(Settings.d("Left Y", -2.0));
    private Setting<Double> zLeft = this.register(Settings.d("Left Z", -2.0));
    private Setting<Double> xRight = this.register(Settings.d("Right X", -2.0));
    private Setting<Double> yRight = this.register(Settings.d("Right Y", -2.0));
    private Setting<Double> zRight = this.register(Settings.d("Right Z", -2.0));
    @EventHandler
    private final Listener<TransformSideFirstPersonEvent> eventListener = new Listener<TransformSideFirstPersonEvent>(event -> {
        if (event.getHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.func_179137_b((double)this.xRight.getValue(), (double)this.yRight.getValue(), (double)this.zRight.getValue());
        } else if (event.getHandSide() == EnumHandSide.LEFT) {
            GlStateManager.func_179137_b((double)this.xLeft.getValue(), (double)this.yLeft.getValue(), (double)this.zLeft.getValue());
        }
    }, new Predicate[0]);

    public void setup() {
    }

    @Override
    public void onEnable() {
        KamiMod.EVENT_BUS.subscribe((Object)this);
    }

    @Override
    public void onDisable() {
        KamiMod.EVENT_BUS.unsubscribe((Object)this);
    }
}

