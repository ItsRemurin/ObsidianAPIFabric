package com.obsidian.endbiomes;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.obsidian.ObsidianMod;
import com.obsidian.util.WeightedNoiseSelection;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class TheEndBiomeData {
    private static boolean hasInitialized;

    private static final Set<RegistryKey<Biome>> biomeKeys = new HashSet<>();
    private static final BiMap<RegistryKey<Biome>, RegistryEntry<Biome>> keyToEntry = HashBiMap.create();
    public static final WeightedNoiseSelection<RegistryKey<Biome>> landNoiseSelection = new WeightedNoiseSelection<RegistryKey<Biome>>();
    public static final WeightedNoiseSelection<RegistryKey<Biome>> voidNoiseSelection = new WeightedNoiseSelection<RegistryKey<Biome>>();

    /**
     * Adds a land biome to The End with a default weight of 1.
     *
     * @param biomeRegistryKey The key of the biome to add.
     */
    public static void addLandBiome(RegistryKey<Biome> biomeRegistryKey) {
        addLandBiome(biomeRegistryKey, 1);
    }

    /**
     * Adds a void biome to The End with a default weight of 1.
     *
     * @param biomeRegistryKey The key of the biome to add.
     */
    public static void addVoidBiome(RegistryKey<Biome> biomeRegistryKey) {
        addVoidBiome(biomeRegistryKey, 1);
    }

    /**
     * Adds a land biome to The End with a specified weight.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param weight           The weight of the biome.
     */
    public static void addLandBiome(RegistryKey<Biome> biomeRegistryKey, double weight) {
        addBiome(biomeRegistryKey, EndBiomeType.LAND, weight);
    }

    /**
     * Adds a void biome to The End with a specified weight.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param weight           The weight of the biome.
     */
    public static void addVoidBiome(RegistryKey<Biome> biomeRegistryKey, double weight) {
        addBiome(biomeRegistryKey, EndBiomeType.VOID, weight);
    }

    /**
     * Adds a biome to The End with a default weight of 1.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param biomeType        The type of the biome.
     */
    public static void addBiome(RegistryKey<Biome> biomeRegistryKey, EndBiomeType biomeType) {
        addBiome(biomeRegistryKey, biomeType, 1);
    }

    /**
     * Adds a biome to The End with a specified weight.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param biomeType        The type of the biome.
     * @param weight           The weight of the biome.
     */
    public static void addBiome(RegistryKey<Biome> biomeRegistryKey, EndBiomeType biomeType, double weight) {
        biomeKeys.add(biomeRegistryKey);
        if(biomeType == EndBiomeType.LAND) {
            landNoiseSelection.add(biomeRegistryKey, weight);
        } else {
            voidNoiseSelection.add(biomeRegistryKey, weight);
        }
    }

    public static void removeBiome(RegistryKey<Biome> biomeRegistryKey) {
        biomeKeys.remove(biomeRegistryKey);
        landNoiseSelection.remove(biomeRegistryKey);
        voidNoiseSelection.remove(biomeRegistryKey);
    }

    /**
     * Gets the biome entry associated with a given biome key.
     *
     * @param biomeRegistryKey The key of the biome.
     * @return The biome entry associated with the key, or {@code null} if not found.
     */
    @Nullable
    public static RegistryEntry<Biome> getBiomeEntry(RegistryKey<Biome> biomeRegistryKey) {
        return keyToEntry.get(biomeRegistryKey);
    }

    /**
     * Gets a set of all biome entries.
     *
     * @return A set of biome entries.
     */
    public static Set<RegistryEntry<Biome>> getBiomeEntries() {
        return keyToEntry.values();
    }

    /**
     * Gets a set of all biome keys.
     *
     * @return A set of biome keys.
     */
    public static Set<RegistryKey<Biome>> getBiomeKeys() {
        return keyToEntry.keySet();
    }

    public static void mapEntriesToKeys(RegistryEntryLookup<Biome> registryEntryLookup) {
        keyToEntry.clear();
        for(RegistryKey<Biome> biomeRegistryKey : biomeKeys) {
            keyToEntry.put(biomeRegistryKey, registryEntryLookup.getOrThrow(biomeRegistryKey));
        }
    }

    public static void init() {
        if(hasInitialized) {
            return;
        }

        TheEndBiomeData.addLandBiome(BiomeKeys.END_HIGHLANDS);
        TheEndBiomeData.addLandBiome(BiomeKeys.END_MIDLANDS);
        TheEndBiomeData.addLandBiome(BiomeKeys.END_BARRENS);
        TheEndBiomeData.addVoidBiome(BiomeKeys.SMALL_END_ISLANDS);
        hasInitialized = true;
    }

    public enum EndBiomeType {

        /** Represents land-based biomes in the End dimension. */ LAND,
        /** Represents void-based biomes in the End dimension. */VOID
    }
}
