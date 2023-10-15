package com.obsidian.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.types.Func;
import com.obsidian.base.BaseBiome;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.apache.commons.lang3.function.TriFunction;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@SuppressWarnings("UnstableApiUsage")
public abstract class BaseDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public static BiMap<RegistryKey<Biome>, TriFunction<Entries, RegistryEntryLookup<PlacedFeature>, RegistryEntryLookup<ConfiguredCarver<?>>, Biome>> biomeBiMap = HashBiMap.create();
    public static BiMap<RegistryKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> configuredFeatureBiMap = HashBiMap.create();
    public static BiMap<RegistryKey<PlacedFeature>, Function<FabricDynamicRegistryProvider.Entries, PlacedFeature>> placedFeatureBiMap = HashBiMap.create();
    public BaseDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        configuredFeaturesStep(entries);
        placedFeaturesStep(entries);

        biomesStep(entries);
    }

    private void biomesStep(Entries entries) {
        for(RegistryKey<Biome> biomeRegistryKey : biomeBiMap.keySet()) {
            TriFunction<Entries, RegistryEntryLookup<PlacedFeature>, RegistryEntryLookup<ConfiguredCarver<?>>, Biome> biome =
                    biomeBiMap.get(biomeRegistryKey);
            if(biome != null) {
                entries.add(biomeRegistryKey,
                        biome.apply(entries, entries.placedFeatures(), entries.configuredCarvers()));
            }
        }
    }

    private void configuredFeaturesStep(Entries entries) {
        for(RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey : configuredFeatureBiMap.keySet()) {
            ConfiguredFeature<?, ?> configuredFeature = configuredFeatureBiMap.get(configuredFeatureRegistryKey);
            if(configuredFeature != null) {
                entries.add(configuredFeatureRegistryKey, configuredFeature);
            }
        }
    }

    private void placedFeaturesStep(Entries entries) {
        for(RegistryKey<PlacedFeature> placedFeatureRegistryKey : placedFeatureBiMap.keySet()) {
            Function<Entries, PlacedFeature> placedFeature = placedFeatureBiMap.get(placedFeatureRegistryKey);
            if(placedFeature != null) {
                entries.add(placedFeatureRegistryKey, placedFeature.apply(entries));
            }
        }
    }

}
