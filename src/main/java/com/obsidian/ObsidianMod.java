package com.obsidian;

import com.obsidian.endbiomes.TheEndBiomeData;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.mixin.biome.TheEndBiomeSourceMixin;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ObsidianMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("obsidian");
	@Override
	public void onInitialize() {
		TheEndBiomeData.AddLandBiome(BiomeKeys.END_HIGHLANDS);
	}
}