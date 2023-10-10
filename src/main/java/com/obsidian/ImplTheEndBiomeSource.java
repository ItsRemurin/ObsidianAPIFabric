package com.obsidian;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.world.biome.Biome;

public interface ImplTheEndBiomeSource {
    public RegistryEntryLookup<Biome> getBiomeLookup();

    public void setBiomeLookup(RegistryEntryLookup<Biome> biomeLookup);
}
