package com.obsidian;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeFogData {
    private static final BiMap<RegistryKey<Biome>, FogModificationData> biomeFogDataMap = HashBiMap.create();

    public static FogModificationData getBiomeFogData(RegistryKey<Biome> biomeRegistryKey) {
        return biomeFogDataMap.get(biomeRegistryKey);
    }
    private static boolean setBiomeFogData(RegistryKey<Biome> biomeRegistryKey, FogModificationData data) {
        if(biomeFogDataMap.containsKey(biomeRegistryKey)) {
            return false;
        }

        biomeFogDataMap.put(biomeRegistryKey, data);
        return true;
    }
    public static boolean setBiomeFogData(RegistryKey<Biome> biomeRegistryKey, float fogStartMult, float fogEndMult) {
        return setBiomeFogData(biomeRegistryKey, new FogModificationData(fogStartMult, fogEndMult));
    }
    public static boolean setBiomeFogData(RegistryKey<Biome> biomeRegistryKey, float fogDensityMult) {
        return setBiomeFogData(biomeRegistryKey, fogDensityMult, fogDensityMult);
    }

    public record FogModificationData(float fogStartMult, float fogEndMult) { }
}
