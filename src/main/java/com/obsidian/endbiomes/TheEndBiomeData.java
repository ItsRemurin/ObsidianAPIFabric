package com.obsidian.endbiomes;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.obsidian.ObsidianMod;
import com.obsidian.util.WeightedNoiseSelection;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class TheEndBiomeData {
    private static final Set<RegistryKey<Biome>> biomeKeys = new HashSet<>();
    private static final BiMap<RegistryKey<Biome>, RegistryEntry<Biome>> keyToEntry = HashBiMap.create();
    public static final WeightedNoiseSelection<RegistryKey<Biome>> weightedNoiseSelection = new WeightedNoiseSelection<RegistryKey<Biome>>();

    /**
     * Adds a land biome to The End with a default weight of 1.
     *
     * @param biomeRegistryKey The key of the biome to add.
     */
    public static void AddLandBiome(RegistryKey<Biome> biomeRegistryKey) {
        AddLandBiome(biomeRegistryKey, 1);
    }

    /**
     * Adds a land biome to The End with a specified weight.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param weight           The weight of the biome.
     */
    public static void AddLandBiome(RegistryKey<Biome> biomeRegistryKey, double weight) {
        AddBiome(biomeRegistryKey, EndBiomeType.LAND, weight);
    }

    /**
     * Adds a biome to The End with a default weight of 1.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param biomeType        The type of the biome.
     */
    public static void AddBiome(RegistryKey<Biome> biomeRegistryKey, EndBiomeType biomeType) {
        AddBiome(biomeRegistryKey, biomeType, 1);
    }

    /**
     * Adds a biome to The End with a specified weight.
     *
     * @param biomeRegistryKey The key of the biome to add.
     * @param biomeType        The type of the biome.
     * @param weight           The weight of the biome.
     */
    public static void AddBiome(RegistryKey<Biome> biomeRegistryKey, EndBiomeType biomeType, double weight) {
        biomeKeys.add(biomeRegistryKey);
        weightedNoiseSelection.add(biomeRegistryKey, weight);
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
        ObsidianMod.LOGGER.info("Begun mapping End Biome entries keys");

        for(RegistryKey<Biome> biomeRegistryKey : biomeKeys) {
            keyToEntry.put(biomeRegistryKey, registryEntryLookup.getOrThrow(biomeRegistryKey));
        }

        ObsidianMod.LOGGER.info("Mapping complete with " + keyToEntry.size() + " entries.");
    }

    public enum EndBiomeType {
        /** Represents land-based biomes in the End dimension. */
        LAND
    }
}
