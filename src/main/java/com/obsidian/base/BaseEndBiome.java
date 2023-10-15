package com.obsidian.base;

import com.obsidian.endbiomes.TheEndBiomeData;

public abstract class BaseEndBiome extends BaseBiome {
    public TheEndBiomeData.EndBiomeType getBiomeType() {
        return TheEndBiomeData.EndBiomeType.LAND;
    }
}
