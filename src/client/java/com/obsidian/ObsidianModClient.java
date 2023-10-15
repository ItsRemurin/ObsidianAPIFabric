package com.obsidian;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.biome.BiomeKeys;

public class ObsidianModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BiomeFogData.setBiomeFogData(BiomeKeys.PLAINS, new BiomeFogData.FogModificationData(6, 2));
	}
}