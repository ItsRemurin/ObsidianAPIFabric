package com.obsidian.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obsidian.BiomeFogData;
import com.obsidian.ObsidianMod;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Shadow private static int waterFogColor;
    private static long lastFogDensityUpdateTime = -1;

    private static long fogStopwatch;
    private static float fogStartTarget = 1;
    private static float fogEndTarget = 1;
    private static float nextFogStartMult = 1;
    private static float nextFogEndMult = 1;
    private static float fogStartOrigin = 1;
    private static float fogEndOrigin = 1;

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"))
    private static void setShaderFogStart(float shaderFogStart) {
        RenderSystem.setShaderFogStart(shaderFogStart / nextFogStartMult);
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"))
    private static void setShaderFogEnd(float shaderFogEnd) {
        RenderSystem.setShaderFogEnd(shaderFogEnd / nextFogEndMult);
    }

    @Inject(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", shift = At.Shift.BEFORE), cancellable = true)
    private static void modifyShaderFogEndAndStart(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        long currentTime = Util.getMeasuringTimeMs();

        float newFogStart = 1;
        float newFogEnd = 1;
        Optional<RegistryKey<Biome>> biomeRegistryKey = camera.getFocusedEntity().getEntityWorld().getBiome(camera.getBlockPos()).getKey();
        if(biomeRegistryKey.isPresent()) {
            RegistryKey<Biome> biome = biomeRegistryKey.get();
            BiomeFogData.FogModificationData fogModificationData = BiomeFogData.getBiomeFogData(biome);
            if(fogModificationData != null) {
                newFogStart = fogModificationData.fogStartMult();
                newFogEnd = fogModificationData.fogEndMult();
            }
        }
        if(fogStartTarget != newFogStart ||
                fogEndTarget != newFogEnd) {
            fogStopwatch = 0;
            fogStartOrigin = nextFogStartMult;
            fogEndOrigin = nextFogEndMult;
            fogStartTarget = newFogStart;
            fogEndTarget = newFogEnd;
        }
        if(lastFogDensityUpdateTime < 0L) {
            lastFogDensityUpdateTime = currentTime;
            fogStartOrigin = newFogStart;
            fogEndOrigin = newFogEnd;
            fogStartTarget = newFogStart;
            fogEndTarget = newFogEnd;
        }



        long deltaTime = currentTime - lastFogDensityUpdateTime;
        lastFogDensityUpdateTime = currentTime;

        fogStopwatch += deltaTime;

        nextFogStartMult = MathHelper.clampedLerp(fogStartOrigin, fogStartTarget, (float) fogStopwatch / 5000);
        nextFogEndMult = MathHelper.clampedLerp(fogEndOrigin, fogEndTarget, (float) fogStopwatch / 5000);
    }
}