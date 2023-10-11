package com.obsidian;

import com.obsidian.endbiomes.TheEndBiomeData;
import net.fabricmc.api.ModInitializer;

import net.minecraft.world.biome.BiomeKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObsidianMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("obsidian");
	@Override
	public void onInitialize() {
		TheEndBiomeData.addLandBiome(BiomeKeys.END_HIGHLANDS);
		TheEndBiomeData.addLandBiome(BiomeKeys.END_MIDLANDS);
		TheEndBiomeData.addLandBiome(BiomeKeys.END_BARRENS);

		//DimensionOptions.END.getValue().

		//MaterialRuleData.AddEndBiomeMaterialRule(BiomeKeys.END_MIDLANDS, MaterialRules.condition(MaterialRules.biome(BiomeKeys.THE_END), MaterialRules.block(Blocks.GRASS_BLOCK.getDefaultState())));
	}
}