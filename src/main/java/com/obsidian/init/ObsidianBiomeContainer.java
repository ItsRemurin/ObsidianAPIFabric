package com.obsidian.init;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.obsidian.base.BaseBiome;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Set;

public class ObsidianBiomeContainer {
    private static final BiMap<String, Set<BaseBiome>> biomeSet = HashBiMap.create();
    private static final BiMap<BaseBiome, RegistryKey<Biome>> biomeToKeyMap = HashBiMap.create();

    public static Set<BaseBiome> getBiomesForMod(String modid) {
        return biomeSet.get(modid);
    }

    public static RegistryKey<Biome> getAssociatedRegistryKey(BaseBiome baseBiome) {
        return biomeToKeyMap.get(baseBiome);
    }
}
