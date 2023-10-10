package com.obsidian.mixin;

import com.obsidian.ImplMultiNoiseSampler;
import net.fabricmc.fabric.impl.biome.MultiNoiseSamplerHooks;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.noise.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ChunkNoiseSampler.class)
public class ChunkNoiseSamplerMixin {
    @Unique
    private long seed;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(int horizontalSize, NoiseConfig noiseConfig, int i, int j, GenerationShapeConfig generationShapeConfig, DensityFunctionTypes.Beardifying arg, ChunkGeneratorSettings chunkGeneratorSettings, AquiferSampler.FluidLevelSampler fluidLevelSampler, Blender blender, CallbackInfo ci) {
        seed = ((ImplMultiNoiseSampler) (Object) noiseConfig.getMultiNoiseSampler()).getSeed();
    }

    @Inject(method = "createMultiNoiseSampler", at = @At("RETURN"))
    private void createMultiNoiseSampler(NoiseRouter noiseRouter, List<MultiNoiseUtil.NoiseHypercube> list, CallbackInfoReturnable<MultiNoiseUtil.MultiNoiseSampler> cir) {
        ((ImplMultiNoiseSampler) (Object) cir.getReturnValue()).setSeed(seed);
    }
}
