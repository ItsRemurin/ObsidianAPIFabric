package com.obsidian.mixin;

import com.obsidian.ImplMultiNoiseSampler;
import net.fabricmc.fabric.impl.biome.MultiNoiseSamplerHooks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseConfig.class)
public class NoiseConfigMixin {
    @Shadow
    @Final
    private MultiNoiseUtil.MultiNoiseSampler multiNoiseSampler;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(ChunkGeneratorSettings chunkGeneratorSettings, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> arg, long seed, CallbackInfo ci) {
        ((ImplMultiNoiseSampler) (Object) multiNoiseSampler).setSeed(seed);
    }
}
