//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.network.play.server.SPacketDisconnect
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.entity.living.LivingDamageEvent
 */
package me.zeroeightsix.kami.module.modules.misc;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.combat.CrystalAura;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

@Module.Info(name="AutoLog", description="Automatically log when in danger or on low health", category=Module.Category.MISC)
public class AutoLog
extends Module {
    private Setting<Integer> health = this.register(Settings.integerBuilder("Health").withRange(0, 36).withValue(6).build());
    private boolean shouldLog = false;
    long lastLog = System.currentTimeMillis();
    @EventHandler
    private Listener<LivingDamageEvent> livingDamageEventListener = new Listener<LivingDamageEvent>(event -> {
        if (AutoLog.mc.player == null) {
            return;
        }
        if (event.getEntity() == AutoLog.mc.player && AutoLog.mc.player.getHealth() - event.getAmount() < (float)this.health.getValue().intValue()) {
            this.log();
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<EntityJoinWorldEvent> entityJoinWorldEventListener = new Listener<EntityJoinWorldEvent>(event -> {
        if (AutoLog.mc.player == null) {
            return;
        }
        if (event.getEntity() instanceof EntityEnderCrystal) {
            Vec3d crystalPos = event.getEntity().getPositionVector();
            if (AutoLog.mc.player.getHealth() - CrystalAura.calculateDamage(crystalPos.x, crystalPos.y, crystalPos.z, (Entity)AutoLog.mc.player) < (float)this.health.getValue().intValue()) {
                this.log();
            }
        }
    }, new Predicate[0]);

    @Override
    public void onUpdate() {
        if (this.shouldLog) {
            this.shouldLog = false;
            if (System.currentTimeMillis() - this.lastLog < 2000L) {
                return;
            }
            Minecraft.getMinecraft().getConnection().handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
        }
    }

    private void log() {
        ModuleManager.getModuleByName("AutoReconnect").disable();
        this.shouldLog = true;
        this.lastLog = System.currentTimeMillis();
    }
}

