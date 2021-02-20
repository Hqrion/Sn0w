//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBoat
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 */
package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.movement.EntitySpeed;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ModelBoat.class})
public class MixinModelBoat {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo info) {
        if (Wrapper.getPlayer().getRidingEntity() == entityIn && ModuleManager.isModuleEnabled("EntitySpeed")) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)EntitySpeed.getOpacity());
            GlStateManager.enableBlend();
        }
    }
}

