package com.obsidian.mixin;

import com.obsidian.ImplMultiNoiseSampler;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MultiNoiseUtil.MultiNoiseSampler.class)
public class MultiNoiseSamplerMixin implements ImplMultiNoiseSampler {
    @Unique
    private Long seed = null;

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }
}
