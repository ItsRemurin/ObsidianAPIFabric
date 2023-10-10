package com.obsidian;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.List;
import java.util.function.Function;

public class ObsidianMultiNoiseBiomeParameterList {
    public static final MultiNoiseBiomeSourceParameterList.Preset END = new MultiNoiseBiomeSourceParameterList.Preset(new Identifier("obsidian", "end"), new MultiNoiseBiomeSourceParameterList.Preset.BiomeSourceFunction() {
        public <T> MultiNoiseUtil.Entries<T> apply(Function<RegistryKey<Biome>, T> function) {
            return new MultiNoiseUtil.Entries(
                    List.of(
                            Pair.of(MultiNoiseUtil.createNoiseHypercube(0.0F, 0.3F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                    function.apply(BiomeKeys.END_HIGHLANDS))));
        }
    });
}
