package com.obsidian.data;

import com.obsidian.base.BaseBiome;
import com.obsidian.init.ObsidianBiomeContainer;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public abstract class BaseBiomesContainer {
    public static void populate(FabricDynamicRegistryProvider.Entries entries) {
        Set<BaseBiome> biomeSet = ObsidianBiomeContainer.getBiomesForMod(getModId());
        if(biomeSet != null) {
            for(BaseBiome biome : biomeSet) {
                RegistryKey<Biome> biomeRegistryKey = ObsidianBiomeContainer.getAssociatedRegistryKey(biome);
                if(biomeRegistryKey == null) {
                    continue;
                }

                entries.add(biomeRegistryKey, biome.create(entries, entries.placedFeatures(), entries.configuredCarvers()));
            }
        }
    }

    public static String getModId() {
        return "";
    }
}
