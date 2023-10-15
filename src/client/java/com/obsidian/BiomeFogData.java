package com.obsidian;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeFogData {
    public static final BiMap<RegistryKey<Biome>, FogModificationData> biomeFogDataMap = HashBiMap.create();

    public static FogModificationData getBiomeFogData(RegistryKey<Biome> biomeRegistryKey) {
        return biomeFogDataMap.get(biomeRegistryKey);
    }

    public static void setBiomeFogData(RegistryKey<Biome> biomeRegistryKey, FogModificationData data) {
        biomeFogDataMap.put(biomeRegistryKey, data);
    }

    public static void setBiomeFogData(RegistryKey<Biome> biomeRegistryKey, float fogStartMult, float fogEndMult) {
        biomeFogDataMap.put(biomeRegistryKey,  new FogModificationData(fogStartMult, fogEndMult));
    }

    public record FogModificationData(float fogStartMult, float fogEndMult) { }
}
