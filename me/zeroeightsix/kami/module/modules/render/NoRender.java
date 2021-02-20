/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.SPacketSpawnExperienceOrb
 *  net.minecraft.network.play.server.SPacketSpawnGlobalEntity
 *  net.minecraft.network.play.server.SPacketSpawnMob
 *  net.minecraft.network.play.server.SPacketSpawnObject
 *  net.minecraft.network.play.server.SPacketSpawnPainting
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType
 */
package me.zeroeightsix.kami.module.modules.render;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

@Module.Info(name="NoRender", category=Module.Category.RENDER, description="Ignore entity spawn packets")
public class NoRender
extends Module {
    private Setting<Boolean> mobs = this.register(Settings.b("Mobs", false));
    private Setting<Boolean> globalEntities = this.register(Settings.b("Global Entities", false));
    private Setting<Boolean> objects = this.register(Settings.b("Objects", false));
    private Setting<Boolean> xpOrbs = this.register(Settings.b("XP Orbs", false));
    private Setting<Boolean> paintings = this.register(Settings.b("Paintings", false));
    public static Setting<Boolean> fire = Settings.b("Fire", true);
    public static Setting<Boolean> portalOverlay = Settings.b("PortalOverlay", true);
    private static NoRender INSTANCE = new NoRender();
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketSpawnMob && this.mobs.getValue() != false || packet instanceof SPacketSpawnGlobalEntity && this.globalEntities.getValue() != false || packet instanceof SPacketSpawnObject && this.objects.getValue() != false || packet instanceof SPacketSpawnExperienceOrb && this.xpOrbs.getValue() != false || packet instanceof SPacketSpawnPainting && this.paintings.getValue().booleanValue()) {
            event.cancel();
        }
    }, new Predicate[0]);
    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener = new Listener<RenderBlockOverlayEvent>(event -> {
        if (fire.getValue().booleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    }, new Predicate[0]);

    public NoRender() {
        INSTANCE = this;
        this.register(fire);
        this.register(portalOverlay);
    }

    public static boolean enabled() {
        return INSTANCE.isEnabled();
    }
}

