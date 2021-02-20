//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package me.zeroeightsix.kami.module.modules.misc;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

@Module.Info(name="FakePlayer", category=Module.Category.MISC, description="Spawns a fake Player")
public class FakePlayer
extends Module {
    private Setting<SpawnMode> spawnMode = this.register(Settings.e("Spawn Mode", SpawnMode.SINGLE));
    private List<Integer> fakePlayerIdList = null;
    private static final String[][] fakePlayerInfo = new String[][]{{"77777777-7777-7777-7777-777777777700", "derp0", "-3", "0"}, {"77777777-7777-7777-7777-777777777701", "derp1", "0", "-3"}, {"77777777-7777-7777-7777-777777777702", "derp2", "3", "0"}, {"77777777-7777-7777-7777-777777777703", "derp3", "0", "3"}, {"77777777-7777-7777-7777-777777777704", "derp4", "-6", "0"}, {"77777777-7777-7777-7777-777777777705", "derp5", "0", "-6"}, {"77777777-7777-7777-7777-777777777706", "derp6", "6", "0"}, {"77777777-7777-7777-7777-777777777707", "derp7", "0", "6"}, {"77777777-7777-7777-7777-777777777708", "derp8", "-9", "0"}, {"77777777-7777-7777-7777-777777777709", "derp9", "0", "-9"}, {"77777777-7777-7777-7777-777777777710", "derp10", "9", "0"}, {"77777777-7777-7777-7777-777777777711", "derp11", "0", "9"}};

    @Override
    protected void onEnable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.world == null) {
            this.disable();
            return;
        }
        this.fakePlayerIdList = new ArrayList<Integer>();
        int entityId = -101;
        for (String[] data : fakePlayerInfo) {
            if (this.spawnMode.getValue().equals((Object)SpawnMode.SINGLE)) {
                this.addFakePlayer(data[0], data[1], entityId, 0, 0);
                break;
            }
            this.addFakePlayer(data[0], data[1], entityId, Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            --entityId;
        }
    }

    private void addFakePlayer(String uuid, String name, int entityId, int offsetX, int offsetZ) {
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString(uuid), name));
        fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        fakePlayer.posX += (double)offsetX;
        fakePlayer.posZ += (double)offsetZ;
        FakePlayer.mc.world.addEntityToWorld(entityId, (Entity)fakePlayer);
        this.fakePlayerIdList.add(entityId);
    }

    @Override
    public void onUpdate() {
        if (this.fakePlayerIdList == null || this.fakePlayerIdList.isEmpty()) {
            this.disable();
        }
    }

    @Override
    protected void onDisable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.world == null) {
            return;
        }
        if (this.fakePlayerIdList != null) {
            for (int id : this.fakePlayerIdList) {
                FakePlayer.mc.world.removeEntityFromWorld(id);
            }
        }
    }

    private static enum SpawnMode {
        SINGLE,
        MULTI;

    }
}

