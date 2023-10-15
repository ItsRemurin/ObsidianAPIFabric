package com.obsidian.base;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

@SuppressWarnings("UnstableApiUsage")
public abstract class BaseBiome {
    public Biome create(FabricDynamicRegistryProvider.Entries entries, RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        return null;
    }

    public static float getFogStartMult() {
        return 1;
    }

    public static float getFogEndMult() {
        return 1;
    }
}
