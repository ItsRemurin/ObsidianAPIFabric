package com.obsidian.util;

import com.obsidian.ImplMultiNoiseSampler;
import com.obsidian.ObsidianMod;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.ArrayList;
import java.util.List;

public class WeightedNoiseSelection<T> {
    private final List<WeightedEntry<T>> entries;
    private PerlinNoiseSampler perlinNoiseSampler;
    public double totalWeight;

    public WeightedNoiseSelection() {
        entries = new ArrayList<>();
    }

    public void add(T item, double weight) {
        entries.add(new WeightedEntry<>(item, weight));
        totalWeight += weight;
    }

    public void resetPerlinNoiseSampler() {
        perlinNoiseSampler = null;
    }

    public T pickFromNoise(MultiNoiseUtil.MultiNoiseSampler sampler, double x, double y, double z) {
        if(perlinNoiseSampler == null) {
            perlinNoiseSampler = new PerlinNoiseSampler(new ChunkRandom(new CheckedRandom(((ImplMultiNoiseSampler)(Object)sampler).getSeed())));
            ObsidianMod.LOGGER.info("Created perlinNoiseSampler for WeightedNoiseSelection");
        }

        double target = MathHelper.clamp(Math.abs(perlinNoiseSampler.sample(x, y, z)), 0, 1) * totalWeight;

        double currentWeight = 0;

        for(WeightedEntry<T> entry : entries) {
            currentWeight += entry.weight();
            if(currentWeight >= target) {
                return entry.value();
            }
        }

        return null;
    }

    public record WeightedEntry<T>(T value, double weight) { }
}
