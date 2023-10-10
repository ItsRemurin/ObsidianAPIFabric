package com.obsidian;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;

public class ObsidianMultiNoiseBiomeSourceParameterLists {
    public static final RegistryKey<MultiNoiseBiomeSourceParameterList> END = of("end");

    private static RegistryKey<MultiNoiseBiomeSourceParameterList> of(String id) {
        return RegistryKey.of(RegistryKeys.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, new Identifier("obsidian", id));
    }
}
