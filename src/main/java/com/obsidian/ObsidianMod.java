package com.obsidian;

import com.obsidian.endbiomes.TheEndBiomeData;
import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObsidianMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("obsidian");
	@Override
	public void onInitialize() {
		TheEndBiomeData.init();
	}
}