//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ChunkRenderContainer
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.util.BlockRenderLayer
 */
package me.zeroeightsix.kami.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderGlobal.class})
public class MixinRenderGlobal {
    @Shadow
    Minecraft mc;
    @Shadow
    public ChunkRenderContainer renderContainer;

    @Inject(method={"renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderBlockLayer(BlockRenderLayer blockLayerIn, CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }
}

