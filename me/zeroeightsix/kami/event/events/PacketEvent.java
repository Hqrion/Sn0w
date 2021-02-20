/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.KamiEvent;
import net.minecraft.network.Packet;

public class PacketEvent
extends KamiEvent {
    private final Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public static class Send
    extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

    public static class Receive
    extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
}

