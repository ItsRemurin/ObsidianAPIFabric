package com.obsidian.mixin;

import com.obsidian.ImplTheEndBiomeSource;
import com.obsidian.ObsidianMod;
import com.obsidian.endbiomes.TheEndBiomeData;
import com.obsidian.util.WeightedNoiseSelection;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(TheEndBiomeSource.class)
public abstract class TheEndBiomeSourceMixin extends BiomeSource implements ImplTheEndBiomeSource{

    @Shadow @Final private RegistryEntry<Biome> midlandsBiome;
    @Shadow @Final private RegistryEntry<Biome> highlandsBiome;

    @Shadow @Final private RegistryEntry<Biome> smallIslandsBiome;
    @Shadow @Final private RegistryEntry<Biome> centerBiome;

    @Shadow @Final private RegistryEntry<Biome> barrensBiome;

    @Inject(method = "createVanilla", at = @At("TAIL"))
    private static void createVanilla(RegistryEntryLookup<Biome> biomeLookup, CallbackInfoReturnable<TheEndBiomeSource> cir) {
        TheEndBiomeData.mapEntriesToKeys(biomeLookup);
        TheEndBiomeData.landNoiseSelection.resetPerlinNoiseSampler();
        TheEndBiomeData.voidNoiseSelection.resetPerlinNoiseSampler();
    }


    @Override
    public Set<RegistryEntry<Biome>> getBiomes() {
        Set<RegistryEntry<Biome>> set = new java.util.HashSet<>(Set.of(centerBiome, smallIslandsBiome));
        set.addAll(TheEndBiomeData.getBiomeEntries());
        return set;
    }

    @Inject(method = "getBiome", at = @At("RETURN"), cancellable = true)
    public void getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise, CallbackInfoReturnable<RegistryEntry<Biome>> cir) {
        RegistryEntry<Biome> returnValue = cir.getReturnValue();
        if(returnValue == midlandsBiome || returnValue == highlandsBiome || returnValue == barrensBiome) {
            RegistryKey<Biome> replacementValue = TheEndBiomeData.landNoiseSelection.pickFromNoise(noise, (double) x / 64, 0, (double) z / 64);
            cir.setReturnValue(replacementValue != null ? TheEndBiomeData.getBiomeEntry(replacementValue) : returnValue);
        } else if(returnValue == smallIslandsBiome) {
            RegistryKey<Biome> replacementValue = TheEndBiomeData.voidNoiseSelection.pickFromNoise(noise, (double) x / 64, 0, (double) z / 64);
            cir.setReturnValue(replacementValue != null ? TheEndBiomeData.getBiomeEntry(replacementValue) : returnValue);
        }
    }
}
