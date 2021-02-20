//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.module.modules.player;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Module.Info(category=Module.Category.PLAYER, description="Prevents fall damage", name="NoFall")
public class NoFall
extends Module {
    private Setting<Boolean> packet = this.register(Settings.b("Packet", false));
    private Setting<Boolean> bucket = this.register(Settings.b("Bucket", true));
    private Setting<Integer> distance = this.register(Settings.i("Distance", 15));
    private long last = 0L;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayer && this.packet.getValue().booleanValue()) {
            ((CPacketPlayer)event.getPacket()).onGround = true;
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        Vec3d posVec;
        RayTraceResult result;
        if (this.bucket.getValue().booleanValue() && NoFall.mc.player.fallDistance >= (float)this.distance.getValue().intValue() && !EntityUtil.isAboveWater((Entity)NoFall.mc.player) && System.currentTimeMillis() - this.last > 100L && (result = NoFall.mc.world.rayTraceBlocks(posVec = NoFall.mc.player.getPositionVector(), posVec.add(0.0, (double)-5.33f, 0.0), true, true, false)) != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            EnumHand hand = EnumHand.MAIN_HAND;
            if (NoFall.mc.player.getHeldItemOffhand().getItem() == Items.WATER_BUCKET) {
                hand = EnumHand.OFF_HAND;
            } else if (NoFall.mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET) {
                for (int i = 0; i < 9; ++i) {
                    if (NoFall.mc.player.inventory.getStackInSlot(i).getItem() != Items.WATER_BUCKET) continue;
                    NoFall.mc.player.inventory.currentItem = i;
                    NoFall.mc.player.rotationPitch = 90.0f;
                    this.last = System.currentTimeMillis();
                    return;
                }
                return;
            }
            NoFall.mc.player.rotationPitch = 90.0f;
            NoFall.mc.playerController.processRightClick((EntityPlayer)NoFall.mc.player, (World)NoFall.mc.world, hand);
            this.last = System.currentTimeMillis();
        }
    }
}

